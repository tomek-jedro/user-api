package user

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import models.Gender.Gender
import models.{Gender, PageRequest, SortParams, UpdateUserRequest, User}
import spray.json.{JsValue, RootJsonFormat, _}
import sttp.tapir.json.spray.TapirJsonSpray
import slick.ast.Ordering

object Marshallers
    extends SprayJsonSupport
    with DefaultJsonProtocol
    with TapirJsonSpray {

  implicit object GenderFormat extends RootJsonFormat[Gender] {

    override def write(obj: Gender): JsString = JsString(obj.toString)

    override def read(json: JsValue): Gender =
      json match {
        case JsString(s) if s.toUpperCase == "MALE"   => Gender.Male
        case JsString(s) if s.toUpperCase == "FEMALE" => Gender.Female
        case _                                        => throw new Exception("Malformed gender")
      }

  }

  implicit val userFormat: RootJsonFormat[User] = jsonFormat5(User.apply)

  implicit object OrderingFormat extends RootJsonFormat[Ordering] {

    override def write(obj: Ordering) = JsString(obj.toString)

    override def read(json: JsValue): Ordering =
      Ordering {
        json match {
          case JsString(s) if s.toUpperCase == "ASC"  => Ordering.Asc
          case JsString(s) if s.toUpperCase == "DESC" => Ordering.Desc
          case _                                      => throw new Exception("Malformed ordering")
        }
      }
  }

  implicit val sortParamsFormat: RootJsonFormat[SortParams] = jsonFormat2(
    SortParams.apply
  )

  implicit val pageRequestFormat: RootJsonFormat[PageRequest] = jsonFormat3(
    PageRequest.apply
  )

  implicit val userUpdateRequestFormat: RootJsonFormat[UpdateUserRequest] =
    jsonFormat2(
      UpdateUserRequest.apply
    )

}
