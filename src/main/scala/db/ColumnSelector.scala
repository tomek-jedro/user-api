package db

import slick.lifted.Rep

trait ColumnSelector { def sortFields: Map[String, Rep[_]] }
