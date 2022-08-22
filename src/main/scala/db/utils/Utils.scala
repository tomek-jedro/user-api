package db.utils

import db.ColumnSelector
import slick.ast.Ordering
import slick.lifted.{ColumnOrdered, Ordered, Query, Rep}

object Utils {

  def dynamicSortBy[A <: ColumnSelector, B, C[_]](
      sortBy: Seq[(String, Ordering)],
      query: Query[A, B, C]
  ): Query[A, B, C] = {
    sortBy.foldRight(query) {
      case ((sortColumn, sortOrder), queryToSort) => {
        val sortOrderRep: Rep[_] => Ordered =
          ColumnOrdered(_, Ordering(sortOrder.direction))
        val sortColumnRep: A => Rep[_] = _.sortFields(sortColumn)
        queryToSort.sortBy(sortColumnRep)(sortOrderRep)
      }
    }
  }

}
