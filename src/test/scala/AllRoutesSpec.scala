import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.dimafeng.testcontainers.{JdbcDatabaseContainer, PostgreSQLContainer}
import db.UserDao
import org.scalatest.BeforeAndAfterAll
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import slick.jdbc.JdbcBackend.Database
import user.UserService

class AllRoutesSpec
    extends AnyFlatSpec
    with Matchers
    with ScalatestRouteTest
    with BeforeAndAfterAll {

  private val initScriptParam =
    JdbcDatabaseContainer.CommonParams(initScriptPath = Option("init.sql"))

  private val postgresContainer =
    PostgreSQLContainer.Def(commonJdbcParams = initScriptParam).start()

  override def nestedSuites = {
    val slickCon = Database.forURL(
      postgresContainer.jdbcUrl,
      user = postgresContainer.username,
      password = postgresContainer.password,
      driver = "org.postgresql.Driver"
    )

    val userService = new UserService(new UserDao(slickCon))

    Vector(
      new RoutesSpec(userService.toRoute)
    )
  }

  override def afterAll() {
    postgresContainer.stop()
  }
}
