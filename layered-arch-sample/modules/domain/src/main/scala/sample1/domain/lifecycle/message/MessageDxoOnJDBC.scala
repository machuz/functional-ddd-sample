package sample1.domain.lifecycle.message

import sample1.domain.model.message.{MessageId, Message}
import sample1.infrastructure.scalikejdbc.mysql.models.TMessages
import org.joda.time.DateTime

object MessageDxoOnJDBC extends MessageDxo[Message, TMessages] {

  override def toEntity(d: TMessages): Message = Message(MessageId(d.id), d.message, d.userName, d.createdAt, d.updatedAt)

  override def toDataModel(e: Message): TMessages = TMessages(e.id.value, e.message, e.userName, e.createdAt, e.updatedAt)

}
