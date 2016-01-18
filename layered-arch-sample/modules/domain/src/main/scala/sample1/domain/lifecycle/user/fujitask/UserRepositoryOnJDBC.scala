package sample1.domain.lifecycle.user.fujitask

import sample1.core.util.fujitask.{ReadTransaction, Task, ReadWriteTransaction}
import sample1.domain.lifecycle.user.UserDxoOnJDBC
import sample1.domain.model.user.{User, UserId}
import sample1.infrastructure.scalikejdbc._
import sample1.infrastructure.scalikejdbc.mysql.models.TUsers

object UserRepositoryOnJDBC extends UserRepository {

  /**
    * エンティティをすべて取得する
    *
    * @return すべてのエンティティ
    */
  override def resolveAll: Task[ReadTransaction, List[User]] =
    ask.map { implicit session =>
      TUsers.findAll().map(UserDxoOnJDBC.toEntity)
    }

  /**
    * 識別子に該当するエンティティを取得する
    *
    * @param id 識別子
    * @return 識別子に該当するエンティティ
    */
  override def resolveById(id: UserId): Task[ReadTransaction, Option[User]] =
    ask.map { implicit session =>
      TUsers.find(id.value).map(UserDxoOnJDBC.toEntity)
    }

  /**
    * エンティティを保存する
    *
    * @param entity 保存するエンティティ
    * @return 保存したエンティティ
    */
  override def store(entity: User): Task[ReadWriteTransaction, User] =
    ask.map { implicit session =>
      UserDxoOnJDBC.toEntity(TUsers.upsert(entity.name, entity.createdAt, entity.updatedAt))
    }

  /**
    * 指定した識別子のエンティティを削除する
    *
    * @param id 識別子
    */
  override def deleteById(id: UserId): Task[ReadWriteTransaction, Unit] =
    ask.map { implicit session =>
      TUsers.destroyById(id.value)
    }

}
