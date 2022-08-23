package user

import models.{Gender, PageRequest, UpdateUserRequest, User}
import sttp.tapir._
import sttp.tapir.generic.auto._
import sttp.tapir.Codec.PlainCodec
import Marshallers._
import models.Gender.Gender

object Endpoints {

  val getUserEndpoint: PublicEndpoint[Long, Unit, Option[User], Any] =
    endpoint.get
      .in("api" / "user" / "get" / path[Long]("userId"))
      .out(jsonBody[Option[User]])

  val getAllUsersEndpoint
      : PublicEndpoint[Option[PageRequest], Unit, List[User], Any] =
    endpoint.get
      .in(jsonBody[Option[PageRequest]])
      .in("api" / "user" / "get" / "all")
      .out(jsonBody[List[User]])

  implicit val genderCodec: PlainCodec[Gender] =
    implicitly[PlainCodec[String]]
      .map(g => Gender.withName(g.toUpperCase))(_.toString.toUpperCase)

  val selectUsersEndpoint: PublicEndpoint[
    (
        Option[PageRequest],
        List[Long],
        Option[String],
        Option[String],
        Option[Gender]
    ),
    String,
    List[User],
    Any
  ] =
    endpoint.post
      .in("api" / "user" / "select")
      .errorOut(plainBody[String])
      .in(jsonBody[Option[PageRequest]])
      .in(query[List[Long]]("id"))
      .in(query[Option[String]]("firstName"))
      .in(query[Option[String]]("lastName"))
      .in(query[Option[Gender]]("gender"))
      .out(jsonBody[List[User]])

  val updateUserEndpoint: PublicEndpoint[UpdateUserRequest, Unit, Int, Any] =
    endpoint.get
      .in("api" / "user" / "update")
      .in(jsonBody[UpdateUserRequest])
      .out(jsonBody[Int])

  val deleteUserEndpoint: PublicEndpoint[Long, Unit, Int, Any] =
    endpoint.get
      .in("api" / "user" / "delete" / path[Long]("id"))
      .out(jsonBody[Int])

  val addUserEndpoint: PublicEndpoint[User, Unit, Option[Long], Any] =
    endpoint.post
      .in("api" / "user" / "save")
      .in(jsonBody[User])
      .out(jsonBody[Option[Long]])

}
