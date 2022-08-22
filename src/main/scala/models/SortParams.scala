package models

import slick.ast.Ordering

case class SortParams(columnName: String, direction: Ordering)
