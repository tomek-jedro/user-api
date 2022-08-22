package user

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

import java.util.Properties

class SwaggerUI(yml: String) {

  val DocsYml = "docs.yml"

  private val redirectToIndex: Route =
    redirect(
      s"/docs/index.html?url=/docs/$DocsYml",
      StatusCodes.PermanentRedirect
    )

  private val swaggerUiVersion = {
    val p = new Properties()
    p.load(
      getClass.getResourceAsStream(
        "/META-INF/maven/org.webjars/swagger-ui/pom.properties"
      )
    )
    p.getProperty("version")
  }

  val routes: Route =
    path("docs") {
      redirectToIndex
    } ~
      pathPrefix("docs") {
        path("") {
          redirectToIndex
        } ~
          path(DocsYml) {
            complete(yml)
          } ~
          getFromResourceDirectory(
            s"META-INF/resources/webjars/swagger-ui/$swaggerUiVersion/"
          )
      }
}
