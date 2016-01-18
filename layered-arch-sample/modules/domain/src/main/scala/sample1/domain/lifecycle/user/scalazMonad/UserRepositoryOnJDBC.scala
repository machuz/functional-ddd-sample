package sample1.domain.lifecycle.user.scalazMonad

import sample1.core.util.fujitask.{ReadTransaction, ReadWriteTransaction, Task}
import sample1.domain.lifecycle.user.UserDxoOnJDBC
import sample1.domain.model.user.{User, UserId}
import sample1.infrastructure.scalikejdbc._
import sample1.infrastructure.scalikejdbc.mysql.models.TUsers

import scalaz.Alpha.M
import scalaz.Monad

trait UserRepositoryOnJDBC[M[+_]] extends UserRepository[M] {

  implicit val M = Monad[M]
//
//   /**
//     * エンティティをすべて取得する
//     *
//     * @return すべてのエンティティ
//     */
//   override def resolveAll: M[List[User]] =
//     ask.map { implicit session =>
//       TUsers.findAll().map(UserDxoOnJDBC.toEntity)
//     }
//
//   /**
//     * 識別子に該当するエンティティを取得する
//     *
//     * @param id 識別子
//     * @return 識別子に該当するエンティティ
//     */
//   override def resolveById(id: UserId): Task[ReadTransaction, Option[User]] =
//     ask.map { implicit session =>
//       TUsers.find(id.value).map(UserDxoOnJDBC.toEntity)
//     }
//
//   /**
//     * エンティティを保存する
//     *
//     * @param entity 保存するエンティティ
//     * @return 保存したエンティティ
//     */
//   override def store(entity: User): Task[ReadWriteTransaction, User] =
//     ask.map { implicit session =>
//       UserDxoOnJDBC.toEntity(TUsers.upsert(entity.name, entity.createdAt, entity.updatedAt))
//     }
//
//   /**
//     * 指定した識別子のエンティティを削除する
//     *
//     * @param id 識別子
//     */
//   override def deleteById(id: UserId): Task[ReadWriteTransaction, Unit] =
//     ask.map { implicit session =>
//       TUsers.destroyById(id.value)
//     }
  /**
   * エンティティをすべて取得する
   *
   * @return すべてのエンティティ
   */
  override def resolveAll: M[List[User]] = ???

  /**
   * エンティティを保存する
   * @param entity 保存するエンティティ
   * @return 保存したエンティティ
   */
  override def store(entity: User): M[User] = ???

  /**
   * 指定した識別子のエンティティを削除する
   * @param id 識別子
   */
  override def deleteById(id: UserId): M[Unit] = ???

  /**
   * 識別子に該当するエンティティを取得する
   * @param id 識別子
   * @return 識別子に該当するエンティティ
   */
  override def resolveById(id: UserId): M[Option[User]] = ???

  override type This = this.type
}
