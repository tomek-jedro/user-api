import akka.actor.typed.{ActorSystem, DispatcherSelector}
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import com.typesafe.config.{Config, ConfigFactory}
import com.typesafe.scalalogging.Logger

import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, ExecutionContextExecutor, Future}
import db.{Connection, UserDao}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import models.User
import sttp.apispec.openapi.OpenAPI
import sttp.model.StatusCode
import sttp.tapir.docs.openapi._
import sttp.tapir._
import sttp.tapir.swagger.bundle.SwaggerInterpreter
import sttp.tapir.server.akkahttp.AkkaHttpServerInterpreter
import sttp.tapir.server.interceptor.DecodeFailureContext
import sttp.tapir.server.interceptor.decodefailure.DecodeFailureHandler
import sttp.tapir.server.model.ValuedEndpointOutput
import user.{Endpoints, UserService}

import scala.concurrent.ExecutionContext.Implicits.global

object Main extends App {

  implicit val system: ActorSystem[Nothing] =
    ActorSystem(Behaviors.empty, "system")

  val logger = Logger(Main.getClass)

  val config: Config = ConfigFactory.load()

  val swaggerEndpoints = SwaggerInterpreter()
    .fromEndpoints[Future](
      List(
        Endpoints.getUserEndpoint,
        Endpoints.getAllUsersEndpoint,
        Endpoints.selectUsersEndpoint,
        Endpoints.deleteUserEndpoint,
        Endpoints.updateUserEndpoint,
        Endpoints.addUserEndpoint
      ),
      "My App",
      "1.0"
    )

  val dao = new UserDao(Connection.db)

  val userService = new UserService(dao)

  val swaggerRoutes = AkkaHttpServerInterpreter().toRoute(
    swaggerEndpoints
  )

  val allRoutes = userService.toRoute ~ swaggerRoutes

  logger.info(
    s"Starting server at ${config.getString("api.host")} port ${config.getInt("api.port")}"
  )

  val bindingFuture = Http()
    .newServerAt(config.getString("api.host"), config.getInt("api.port"))
    .bind(allRoutes)

  scala.sys.addShutdownHook {
    logger.info("Closing db conn")
    Connection.db.close()
    logger.info("Terminating system")
    system.terminate()
    Await.result(system.whenTerminated, 30.seconds)
    logger.info("System terminated")
  }
}
