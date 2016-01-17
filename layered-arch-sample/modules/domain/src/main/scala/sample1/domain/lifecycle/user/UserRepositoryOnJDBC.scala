package sample1.domain.lifecycle.user

import akka.actor.ActorSystem
import sample1.core.util.fujitask.Task
import sample1.core.util.fujitask.ReadTransaction
import sample1.core.util.fujitask.ReadWriteTransaction
import sample1.infrastructure.scalikejdbc._
import sample1.domain.model.user.{User, UserId}
import sample1.infrastructure.scalikejdbc.mysql.models.TUsers
import scalikejdbc._

import scala.concurrent.{Future, ExecutionContext}
import scalaz.Monad
import scalaz.Scalaz._

abstract class UserRepositoryOnJDBC[M[+_]] extends UserRepository[M] {

  /**
    * エンティティをすべて取得する
    *
    * @return すべてのエンティティ
    */
  override def resolveAll: Task[Ctx, M[List[User]]] =
    ask.map { implicit session =>
      TUsers.findAll().map(UserDxoOnJDBC.toEntity).pure[M]
    }

  /**
    * 識別子に該当するエンティティを取得する
    *
    * @param id 識別子
    * @return 識別子に該当するエンティティ
    */
  override def resolveById(id: UserId): Task[ReadTransaction, M[Option[User]]] =
    ask.map { implicit session =>
      TUsers.find(id.value).map(UserDxoOnJDBC.toEntity).pure[M]
    }

  /**
    * エンティティを保存する
    *
    * @param entity 保存するエンティティ
    * @return 保存したエンティティ
    */
  override def store(entity: User): Task[ReadWriteTransaction, M[User]] =
    ask.map { implicit session =>
      UserDxoOnJDBC.toEntity(TUsers.upsert(entity.name, entity.createdAt, entity.updatedAt)).pure[M]
    }

  /**
    * 指定した識別子のエンティティを削除する
    *
    * @param id 識別子
    */
  override def deleteById(id: UserId): Task[ReadWriteTransaction, M[Unit]] =
    ask.map { implicit session =>
      TUsers.destroyById(id.value).pure[M]
    }

}

class FutureUserRepositoryOnJDBC extends UserRepositoryOnJDBC[Future] {
  implicit val ec = ActorSystem().dispatcher
  val M = Monad[Future]
}

object FutureUserRepositoryOnJDBC extends FutureUserRepositoryOnJDBC

// test用恒等射Monad
class IDUserRepositoryOnJDBC extends UserRepositoryOnJDBC[Id] {
  val M = Monad[Id]
}

object IDUserRepositoryOnJDBC extends IDUserRepositoryOnJDBC