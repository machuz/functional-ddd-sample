package sample1.infrastructure.scalikejdbc

import _root_.scalikejdbc._
import sample1.core.util.fujitask.{ReadTransaction, ReadWriteTransaction}

abstract class ScalikeJDBCTransaction(val session: DBSession)

class ScalikeJDBCReadTransaction(session: DBSession) extends ScalikeJDBCTransaction(session) with ReadTransaction

class ScalikeJDBCReadWriteTransaction(session: DBSession) extends ScalikeJDBCTransaction(session) with ReadWriteTransaction