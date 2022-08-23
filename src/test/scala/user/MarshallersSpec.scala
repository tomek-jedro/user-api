package user

import org.scalatest.flatspec.AsyncFlatSpecLike
import org.scalatest.matchers.should.Matchers
import Marshallers._
import akka.actor.ActorSystem
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.testkit.TestKit
import models.User
import org.scalatest.BeforeAndAfterAll
import org.scalatest.concurrent.ScalaFutures

class MarshallersSpec
    extends TestKit(ActorSystem("MarshallersSpec"))
    with Matchers
    with AsyncFlatSpecLike
    with BeforeAndAfterAll {

  override def afterAll(): Unit = {
    TestKit.shutdownActorSystem(system)
  }

  val userEntity: String =
    """
      | {
      |   "firstName": "Tom",
      |   "lastName": "J",
      |   "gender": "Male",
      |   "age": 23
      | }""".stripMargin

  it should "unmarshal user entity" in {

    val futureUser = Unmarshal(userEntity).to[User]

    ScalaFutures.whenReady(futureUser) { user =>
      user.age shouldEqual 23
      user.firstName shouldEqual "Tom"
    }
  }
}
