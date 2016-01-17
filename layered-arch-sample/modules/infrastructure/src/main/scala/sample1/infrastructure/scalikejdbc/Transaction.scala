package sample1.infrastructure.scalikejdbc

import _root_.scalikejdbc._
import sample1.libs.fujitask.ReadWriteTransaction
import sample1.shared.ReadWriteTransaction
import sample1.shared.util.fujitask.{ReadWriteTransaction, ReadTransaction}

abstract class ScalikeJDBCTransaction(val session: DBSession)

class ScalikeJDBCReadTransaction(session: DBSession) extends ScalikeJDBCTransaction(session) with ReadTransaction

class ScalikeJDBCReadWriteTransaction(session: DBSession) extends ScalikeJDBCTransaction(session) with ReadWriteTransaction