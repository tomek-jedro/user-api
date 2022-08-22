package models

import models.Gender.Gender

case class SelectQueryParameters(
    ids: List[Long],
    firstName: Option[String],
    lastName: Option[String],
    gender: Option[Gender]
)
