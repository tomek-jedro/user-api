package user

import models.{Gender, PageRequest, User}
import sttp.tapir._
import sttp.tapir.generic.auto._
import sttp.tapir.Codec.PlainCodec
import Marshallers._
import models.Gender.Gender

object Endpoints {

  val getUserEndpoint: PublicEndpoint[Long, Unit, Option[User], Any] =
    endpoint.get
      .in("api" / "user" / path[Long]("userId"))
      .out(jsonBody[Option[User]])

  val getAllUsersEndpoint
      : PublicEndpoint[Option[PageRequest], Unit, List[User], Any] =
    endpoint.get
      .in(jsonBody[Option[PageRequest]])
      .in("api" / "users")
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

  val updateUserEndpoint
      : PublicEndpoint[(Long, User), Unit, Option[User], Any] =
    endpoint.post
      .in("api" / "user" / path[Long]("id"))
      .in(jsonBody[User])
      .out(jsonBody[Option[User]])

  val deleteUserEndpoint: PublicEndpoint[Long, Unit, Int, Any] =
    endpoint.delete
      .in("api" / "user" / path[Long]("id"))
      .out(plainBody[Int])
      .description("Returns number of affected rows.")

  val addUserEndpoint: PublicEndpoint[User, Unit, Option[User], Any] =
    endpoint.post
      .in("api" / "user")
      .in(jsonBody[User])
      .out(jsonBody[Option[User]])

}
