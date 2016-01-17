package sample1.core.util.pimp

import scalikejdbc._

object RichMySQLSyntax {

  implicit class RichInsertSQLBuilder(val self: InsertSQLBuilder) extends AnyVal {
    def onDuplicateKeyUpdate(columnsAndValues: (SQLSyntax, Any)*): InsertSQLBuilder = {
      val cvs = columnsAndValues map {
        case (c, v) => sqls"$c = $v"
      }
      self.append(sqls"on duplicate key update ${sqls.csv(cvs: _*)}")
    }
  }

  implicit class RichSQLSyntaxType(val self: sqls.type) extends AnyVal {
    def values(column: SQLSyntax): SQLSyntax = sqls"values($column)"
  }

}