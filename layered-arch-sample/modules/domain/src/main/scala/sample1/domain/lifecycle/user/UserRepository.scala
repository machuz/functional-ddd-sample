package sample1.domain.lifecycle.user

import sample1.core.util.fujitask.{Task, ReadTransaction}
import sample1.domain.model.user.{User, UserId}
import sample1.domain.support.Repository

trait UserRepository extends Repository[UserId, User] {

  /**
    * エンティティをすべて取得する
    *
    * @return すべてのエンティティ
    */
  def resolveAll: Task[ReadTransaction, List[User]]

}

