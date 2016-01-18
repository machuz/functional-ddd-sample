package sample1.domain.lifecycle.user.scalazMonad

import sample1.domain.model.user.{User, UserId}
import sample1.domain.support.MonadRepository

trait UserRepository[M[+_]] extends MonadRepository[M, UserId, User] {

  /**
   * エンティティをすべて取得する
   *
   * @return すべてのエンティティ
   */
  def resolveAll: M[List[User]]

}
