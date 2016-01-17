package sample1.domain.lifecycle.message

import sample1.core.util.fujitask.{ReadTransaction, ReadWriteTransaction, Task}
import sample1.domain.model.message.{MessageId, Message}
import sample1.infrastructure.scalikejdbc._
import sample1.infrastructure.scalikejdbc.mysql.models.TMessages

object MessageRepositoryOnJDBC extends MessageRepository {

  /**
    * エンティティをすべて取得する
    *
    * @return すべてのエンティティ
    */
  override def resolveAll: Task[ReadTransaction, List[Message]] =
    ask.map { implicit session =>
      TMessages.findAll().map(MessageDxoOnJDBC.toEntity)
    }

  /**
    * 識別子に該当するエンティティを取得する
    *
    * @param id 識別子
    * @return 識別子に該当するエンティティ
    */
  override def resolveById(id: MessageId): Task[ReadTransaction, Option[Message]] =
    ask.map { implicit session =>
      TMessages.find(id.value).map(MessageDxoOnJDBC.toEntity)
    }

  /**
    * エンティティを保存する
    *
    * @param entity 保存するエンティティ
    * @return 保存したエンティティ
    */
  override def store(entity: Message): Task[ReadWriteTransaction, Message] =
    ask.map { implicit session =>
      MessageDxoOnJDBC.toEntity(TMessages.upsert(entity.message, entity.userName, entity.createdAt, entity.updatedAt))
    }

  /**
    * 指定した識別子のエンティティを削除する
    *
    * @param id 識別子
    */
  override def deleteById(id: MessageId): Task[ReadWriteTransaction, Unit] =
    ask.map { implicit session =>
      TMessages.destroyById(id.value)
    }

}

