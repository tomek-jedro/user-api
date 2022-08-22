package db

import models.{PageRequest, SelectQueryParameters, UpdateUserRequest}

import scala.concurrent.Future

trait Dao[T] {

  def delete(id: Long): Future[Int]

  def update(updateUserRequest: UpdateUserRequest): Future[Int]

  def save(t: T): Future[Option[Long]]

  def select(
      pageRequest: Option[PageRequest],
      queryParameters: SelectQueryParameters
  ): Future[List[T]]

  def get(id: Long): Future[Option[T]]

  def getAll(pageRequest: Option[PageRequest]): Future[List[T]]
}
