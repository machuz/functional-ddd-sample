package sample1.domain.support

import sample1.core.util.fujitask.{ReadTransaction, Task, ReadWriteTransaction, Transaction}

/**
  * リポジトリ責務を表すトレイト。
  *
  * @tparam ID 識別子の型
  * @tparam E エンティティの型
  */
trait Repository[ID <: Identifier[_], E <: Entity[ID]] {

  type This <: Repository[ID, E]

  type Ctx = Transaction

  /**
    * エンティティを保存する
    * @param entity 保存するエンティティ
    * @return 保存したエンティティ
    */
  def store(entity: E): Task[ReadWriteTransaction, E]

  /**
    * 識別子に該当するエンティティを取得する
    * @param id 識別子
    * @return 識別子に該当するエンティティ
    */
  def resolveById(id: ID): Task[ReadTransaction, Option[E]]

  /**
    * 指定した識別子のエンティティを削除する
    * @param id 識別子
    */
  def deleteById(id: ID): Task[ReadWriteTransaction, Unit]

}