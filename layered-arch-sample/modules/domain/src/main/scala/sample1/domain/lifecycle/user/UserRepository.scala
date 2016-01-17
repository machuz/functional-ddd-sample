package sample1.domain.lifecycle.user

import sample1.core.util.fujitask.{Task, ReadTransaction}
import sample1.domain.model.user.{User, UserId}
import sample1.domain.support.Repository

import scalaz.Monad

trait UserRepository[M[+_]] extends Repository[M, UserId, User] {

  implicit val M: Monad[M]

  /**
    * エンティティをすべて取得する
    *
    * @return すべてのエンティティ
    */
  def resolveAll: Task[ReadTransaction, M[List[User]]]

}

