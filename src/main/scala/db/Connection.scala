package db

object Connection {
  val driver = slick.jdbc.PostgresProfile

  import driver.api._

  val db: Database = Database.forConfig("api.postgres")

}
