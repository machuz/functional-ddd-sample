package sample1.domain.model.message

import org.joda.time.DateTime
import sample1.domain.support.Entity

case class Message(
  id: MessageId,
  message: String,
  userName: String,
  createdAt: DateTime,
  updatedAt: DateTime) extends Entity[MessageId] {

  override val identifier = id

  def toTsv = s"${id.value}\t$message\t$userName"

}

