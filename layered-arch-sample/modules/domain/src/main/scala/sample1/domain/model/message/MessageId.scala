package sample1.domain.model.message

import sample1.domain.support.{EmptyIdentifier, Identifier}

trait MessageId extends Identifier[Int]

object MessageId {
  def apply(value: Int) = ExistMessageId(value)
}

case class ExistMessageId(value: Int) extends MessageId

object EmptyMessageId extends EmptyIdentifier with MessageId