package user

import akka.http.scaladsl.server.Route
import cats.implicits._
import com.typesafe.scalalogging.Logger
import db.UserDao
import models.SelectQueryParameters
import sttp.tapir.server.akkahttp.AkkaHttpServerInterpreter

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util._

class UserService(userDao: UserDao) {

  val logger: Logger = Logger("Routes")

  val getUser = Endpoints.getUserEndpoint.serverLogic { id =>
    Right(userDao.get(id)).withLeft[Unit].sequence
  }

  val getAllUsers = Endpoints.getAllUsersEndpoint.serverLogic { in =>
    Right(userDao.getAll(in)).withLeft[Unit].sequence
  }

  val selectUsers = Endpoints.selectUsersEndpoint
    .serverLogic(in => {
      println("XXXXX")
      println(in)
      val (pageRequest, mbyIds, mbyFirstName, mbyLastName, mbyGender) = in

      val queryParameters = SelectQueryParameters(
        mbyIds,
        mbyFirstName,
        mbyLastName,
        mbyGender
      )

      Right(
        userDao
          .select(pageRequest, queryParameters)
      ).withLeft[String].sequence
    })

  def updateUser =
    Endpoints.updateUserEndpoint.serverLogic { in =>
      Right(userDao.update(in)).withLeft[Unit].sequence
    }

  def deleteUser =
    Endpoints.deleteUserEndpoint.serverLogic { in =>
      Right(userDao.delete(in)).withLeft[Unit].sequence
    }

  def addUser =
    Endpoints.addUserEndpoint.serverLogic { in =>
      println(in)
      Right(userDao.save(in)).withLeft[Unit].sequence
    }

  val toRoute: Route = AkkaHttpServerInterpreter().toRoute(
    List(
      getUser,
      getAllUsers,
      updateUser,
      selectUsers,
      addUser,
      deleteUser
    )
  )

}
