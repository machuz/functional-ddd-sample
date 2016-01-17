package sample1.infrastructure.scalikejdbc.mysql.models

import sample1.core.util.pimp.RichMySQLSyntax._
import scalikejdbc._
import org.joda.time.DateTime

case class TMessages(
  id: Int,
  message: String,
  userName: String,
  createdAt: DateTime,
  updatedAt: DateTime) {

  def save()(implicit session: DBSession = TMessages.autoSession): TMessages = TMessages.save(this)(session)

  def destroy()(implicit session: DBSession = TMessages.autoSession): Unit = TMessages.destroy(this)(session)

}

object TMessages extends SQLSyntaxSupport[TMessages] {

  override val tableName = "t_messages"

  override val columns = Seq("id", "message", "user_name", "created_at", "updated_at")

  def apply(tm: SyntaxProvider[TMessages])(rs: WrappedResultSet): TMessages = apply(tm.resultName)(rs)
  def apply(tm: ResultName[TMessages])(rs: WrappedResultSet): TMessages = new TMessages(
    id = rs.get(tm.id),
    message = rs.get(tm.message),
    userName = rs.get(tm.userName),
    createdAt = rs.get(tm.createdAt),
    updatedAt = rs.get(tm.updatedAt)
  )

  val tm = TMessages.syntax("tm")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[TMessages] = {
    withSQL {
      select.from(TMessages as tm).where.eq(tm.id, id)
    }.map(TMessages(tm.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[TMessages] = {
    withSQL(select.from(TMessages as tm)).map(TMessages(tm.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(TMessages as tm)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[TMessages] = {
    withSQL {
      select.from(TMessages as tm).where.append(where)
    }.map(TMessages(tm.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[TMessages] = {
    withSQL {
      select.from(TMessages as tm).where.append(where)
    }.map(TMessages(tm.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(TMessages as tm).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    message: String,
    userName: String,
    createdAt: DateTime,
    updatedAt: DateTime)(implicit session: DBSession = autoSession): TMessages = {
    val generatedKey = withSQL {
      insert.into(TMessages).columns(
        column.message,
        column.userName,
        column.createdAt,
        column.updatedAt
      ).values(
        message,
        userName,
        createdAt,
        updatedAt
      )
    }.updateAndReturnGeneratedKey.apply()

    TMessages(
      id = generatedKey.toInt,
      message = message,
      userName = userName,
      createdAt = createdAt,
      updatedAt = updatedAt)
  }

  def batchInsert(entities: Seq[TMessages])(implicit session: DBSession = autoSession): Seq[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'message -> entity.message,
        'userName -> entity.userName,
        'createdAt -> entity.createdAt,
        'updatedAt -> entity.updatedAt))
        SQL("""insert into t_messages(
        message,
        user_name,
        created_at,
        updated_at
      ) values (
        {message},
        {userName},
        {createdAt},
        {updatedAt}
      )""").batchByName(params: _*).apply()
    }

  def save(entity: TMessages)(implicit session: DBSession = autoSession): TMessages = {
    withSQL {
      update(TMessages).set(
        column.id -> entity.id,
        column.message -> entity.message,
        column.userName -> entity.userName,
        column.createdAt -> entity.createdAt,
        column.updatedAt -> entity.updatedAt
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: TMessages)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(TMessages).where.eq(column.id, entity.id) }.update.apply()
  }

  def destroyById(id: Int)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(TMessages).where.eq(column.id, id) }.update.apply()
  }

  def upsert(
    message: String,
    userName: String,
    createdAt: DateTime,
    updatedAt: DateTime)(implicit session: DBSession = autoSession): TMessages = {
    withSQL {
      insert.into(TMessages).columns(
        column.message,
        column.userName,
        column.createdAt,
        column.updatedAt
      ).values(
        message,
        userName,
        createdAt,
        updatedAt
      ).onDuplicateKeyUpdate(
        column.message -> sqls.values(column.message),
        column.userName -> sqls.values(column.userName),
        column.updatedAt -> sqls.values(column.updatedAt)
      )
    }.update.apply()

    val generatedKey = TMessages.findBy(sqls.eq(column.userName, userName)).getOrElse(throw new IllegalStateException(s"upsert Failed"))

    TMessages(
      id = generatedKey.id,
      message = message,
      userName = userName,
      createdAt = createdAt,
      updatedAt = updatedAt)
  }

}
