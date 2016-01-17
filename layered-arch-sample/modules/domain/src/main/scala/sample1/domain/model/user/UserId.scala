package sample1.domain.model.user

import sample1.domain.support.{EmptyIdentifier, Identifier}

trait UserId extends Identifier[Int]

object UserId {
  def apply(value: Int) = ExistUserId(value)
}

case class ExistUserId(value: Int) extends UserId

object EmptyUserId extends EmptyIdentifier with UserId