package db

import models.Gender.Gender
import models.{Gender, User}

object UserTable {
  import CustomPostgresProfile.api._

  implicit val GenderMapper = MappedColumnType.base[Gender, String](
    e => e.toString,
    s => Gender.withName(s)
  )

  class UserTable(tag: Tag)
      extends Table[User](tag, Some("api"), "user")
      with ColumnSelector {
    def id =
      column[Option[Long]]("id", O.PrimaryKey, O.AutoInc, O.Unique)
    def firstName = column[String]("first_name")
    def lastName = column[String]("last_name")
    def gender = column[Gender]("gender")
    def age = column[Int]("age")
    override def sortFields =
      Map(
        "id" -> id,
        "firstName" -> firstName,
        "lastName" -> lastName,
        "gender" -> gender,
        "age" -> age
      )
    override def * =
      (
        id,
        firstName,
        lastName,
        gender,
        age
      ) <> (User.tupled, User.unapply)

  }

  val userTable = TableQuery[UserTable]

}
