package sample1.infrastructure.scalikejdbc.mysql.models

import sample1.core.util.pimp.RichMySQLSyntax._
import scalikejdbc._
import org.joda.time.DateTime

case class TUsers(
  id: Int,
  name: String,
  createdAt: DateTime,
  updatedAt: DateTime) {

  def save()(implicit session: DBSession = TUsers.autoSession): TUsers = TUsers.save(this)(session)

  def destroy()(implicit session: DBSession = TUsers.autoSession): Unit = TUsers.destroy(this)(session)

}


object TUsers extends SQLSyntaxSupport[TUsers] {

  override val tableName = "t_users"

  override val columns = Seq("id", "name", "created_at", "updated_at")

  def apply(tu: SyntaxProvider[TUsers])(rs: WrappedResultSet): TUsers = apply(tu.resultName)(rs)
  def apply(tu: ResultName[TUsers])(rs: WrappedResultSet): TUsers = new TUsers(
    id = rs.get(tu.id),
    name = rs.get(tu.name),
    createdAt = rs.get(tu.createdAt),
    updatedAt = rs.get(tu.updatedAt)
  )

  val tu = TUsers.syntax("tu")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[TUsers] = {
    withSQL {
      select.from(TUsers as tu).where.eq(tu.id, id)
    }.map(TUsers(tu.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[TUsers] = {
    withSQL(select.from(TUsers as tu)).map(TUsers(tu.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(TUsers as tu)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[TUsers] = {
    withSQL {
      select.from(TUsers as tu).where.append(where)
    }.map(TUsers(tu.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[TUsers] = {
    withSQL {
      select.from(TUsers as tu).where.append(where)
    }.map(TUsers(tu.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(TUsers as tu).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    name: String,
    createdAt: DateTime,
    updatedAt: DateTime)(implicit session: DBSession = autoSession): TUsers = {
    val generatedKey = withSQL {
      insert.into(TUsers).columns(
        column.name,
        column.createdAt,
        column.updatedAt
      ).values(
        name,
        createdAt,
        updatedAt
      )
    }.updateAndReturnGeneratedKey.apply()

    TUsers(
      id = generatedKey.toInt,
      name = name,
      createdAt = createdAt,
      updatedAt = updatedAt)
  }

  def batchInsert(entities: Seq[TUsers])(implicit session: DBSession = autoSession): Seq[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'name -> entity.name,
        'createdAt -> entity.createdAt,
        'updatedAt -> entity.updatedAt))
        SQL("""insert into t_users(
        name,
        created_at,
        updated_at
      ) values (
        {name},
        {createdAt},
        {updatedAt}
      )""").batchByName(params: _*).apply()
    }

  def save(entity: TUsers)(implicit session: DBSession = autoSession): TUsers = {
    withSQL {
      update(TUsers).set(
        column.id -> entity.id,
        column.name -> entity.name,
        column.createdAt -> entity.createdAt,
        column.updatedAt -> entity.updatedAt
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: TUsers)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(TUsers).where.eq(column.id, entity.id) }.update.apply()
  }

  def destroyById(id: Int)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(TUsers).where.eq(column.id, id) }.update.apply()
  }

  def upsert(
    name: String,
    createdAt: DateTime,
    updatedAt: DateTime)(implicit session: DBSession = autoSession): TUsers = {
    withSQL {
      insert.into(TUsers).columns(
        column.name,
        column.createdAt,
        column.updatedAt
      ).values(
        name,
        createdAt,
        updatedAt
      ).onDuplicateKeyUpdate(
        column.name -> sqls.values(column.name),
        column.updatedAt -> sqls.values(column.updatedAt)
      )
    }.update.apply()

    val generatedKey = TUsers.findBy(sqls.eq(column.name, name)).getOrElse(throw new IllegalStateException(s"upsert Failed"))

    TUsers(
      id = generatedKey.id,
      name = name,
      createdAt = createdAt,
      updatedAt = updatedAt)
  }

}
