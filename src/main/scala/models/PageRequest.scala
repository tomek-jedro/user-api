package models

case class PageRequest(
    page: Int = 1,
    pageSize: Int = 10,
    sortBy: Seq[SortParams]
)
