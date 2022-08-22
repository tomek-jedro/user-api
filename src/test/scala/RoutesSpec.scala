import user.Marshallers._
import akka.http.scaladsl.model.{ContentTypes, StatusCodes}
import akka.http.scaladsl.server._
import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.http.scaladsl.unmarshalling.Unmarshal
import models.User
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import slick.ast.Ordering

class RoutesSpec(userRoute: Route)
    extends AnyFlatSpec
    with ScalatestRouteTest
    with Matchers {

  val userEntity: String =
    """
      | {
      |   "firstName": "T", 
      |   "lastName": "J", 
      |   "gender": "Male", 
      |   "age": 23
      | }""".stripMargin

  val pageRequestEntity: String =
    """
      | {
      |   "page": 1,
      |   "pageSize": 1,
      |   "sortBy": [{"columnName": "id", "direction":"ASC"}]
      | }
      |""".stripMargin

  it should "fetch first user" in {
    Get("/api/user/get/1") ~> userRoute ~> check {
      val user = responseAs[User]
      user.firstName should not be empty
      user.lastName should not be empty
      user.age should be > 1
      status shouldEqual StatusCodes.OK
      contentType shouldBe ContentTypes.`application/json`
    }
  }

  it should "save user" in {
    Post("/api/user/save").withEntity(
      ContentTypes.`application/json`,
      userEntity
    ) ~> userRoute ~> check {

      val futureId = Unmarshal(responseAs[String]).to[Long]

      ScalaFutures.whenReady(futureId) { id =>
        assert(id > 1)
      }
      contentType shouldBe ContentTypes.`application/json`
      status shouldEqual StatusCodes.OK
    }

  }

  it should "fetch all users" in {
    Get("/api/user/get/all").withEntity(
      ContentTypes.`application/json`,
      pageRequestEntity
    ) ~> userRoute ~> check {

      val fUsers = Unmarshal(responseAs[List[User]]).to[List[User]]

      ScalaFutures.whenReady(fUsers) { users =>
        println(users)
        users.length shouldEqual 1
      }

      contentType shouldBe ContentTypes.`application/json`
      status shouldEqual StatusCodes.OK
    }

  }

  it should "filter users using given parameters" in {
    val pageRequestEntity =
      """
        | {
        |   "page": 1,
        |   "pageSize": 10,
        |   "sortBy": []
        | }
        |""".stripMargin
    Post("/api/user/select?lastName=Smith").withEntity(
      ContentTypes.`application/json`,
      pageRequestEntity
    ) ~> userRoute ~> check {
      val fUsers = Unmarshal(responseAs[List[User]]).to[List[User]]

      ScalaFutures.whenReady(fUsers) { users =>
        users.length shouldEqual 1
        users.head.firstName shouldEqual "Will"
      }

      contentType shouldBe ContentTypes.`application/json`
      status shouldEqual StatusCodes.OK
    }
  }

  it should "delete user" in {
    Get("/api/user/delete/1") ~> userRoute ~> check {
      val deleted = Unmarshal(responseAs[String]).to[Long]
      ScalaFutures.whenReady(deleted) { d =>
        d shouldEqual 1
      }
      status shouldEqual StatusCodes.OK
    }
    Get("/api/user/get/1") ~> userRoute ~> check {
      val user = responseAs[String]
      user shouldBe empty
      status shouldEqual StatusCodes.OK
      contentType shouldBe ContentTypes.`application/json`
    }
  }
}
