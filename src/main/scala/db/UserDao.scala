package db

import models.{PageRequest, SelectQueryParameters, UpdateUserRequest, User}
import slick.ast.Ordering.Direction

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import db.utils.Utils.dynamicSortBy
import CustomPostgresProfile.api._
import UserTable._

class UserDao(db: Database) extends Dao[User] {

  type ColumnOrdering = (String, Direction)

  override def delete(id: Long): Future[Int] = {
    db.run(userTable.filter(_.id === id).delete)
  }

  override def update(
      updateUserRequest: UpdateUserRequest
  ): Future[Int] = {
    val user = updateUserRequest.newUser.copy(id = Some(updateUserRequest.id))
    val q = userTable
      .insertOrUpdate(user)

    db.run(q)
  }

  override def save(t: User): Future[Option[Long]] =
    db.run(userTable returning userTable.map(_.id) += t)

  private def getOffset(pageRequest: PageRequest) = {
    pageRequest.page * pageRequest.pageSize - pageRequest.pageSize
  }

  override def select(
      mbyPageRequest: Option[PageRequest],
      queryParameters: SelectQueryParameters
  ): Future[List[User]] = {
    val pageRequest = mbyPageRequest.getOrElse(PageRequest(sortBy = Seq()))

    val offset = getOffset(pageRequest)

    val filteredUsers =
      if (queryParameters.ids.nonEmpty)
        userTable.filter(_.id inSetBind queryParameters.ids)
      else userTable

    val query = filteredUsers
      .filterOpt(queryParameters.firstName)((t, f) => t.firstName === f)
      .filterOpt(queryParameters.lastName)((t, l) => t.lastName === l)
      .filterOpt(queryParameters.gender)((t, s) => t.gender === s)
      .drop(offset)
      .take(pageRequest.pageSize)

    val sortedQuery = dynamicSortBy(
      pageRequest.sortBy.map(x => (x.columnName, x.direction)),
      query
    )

    db.run(sortedQuery.result.map(_.toList))
  }

  override def get(id: Long): Future[Option[User]] = {
    db.run(userTable.filter(_.id === id).take(1).result.headOption)
  }

  override def getAll(
      mbyPageRequest: Option[PageRequest]
  ): Future[List[User]] = {
    val pageRequest = mbyPageRequest.getOrElse(PageRequest(sortBy = Seq()))
    val offset = getOffset(pageRequest)
    val q = userTable.drop(offset).take(pageRequest.pageSize)
    db.run(q.result).map(_.toList)
  }

}
