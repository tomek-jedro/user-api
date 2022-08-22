package models

import models.Gender.Gender

case class  User(
    id: Option[Long],
    firstName: String,
    lastName: String,
    gender: Gender,
    age: Int
)
