package sample1.domain.model.user

import org.joda.time.DateTime
import sample1.domain.support.Entity

case class User(
  id: UserId,
  name: String,
  createdAt: DateTime,
  updatedAt: DateTime) extends Entity[UserId] {

  override val identifier = id

  def toTsv = s"${id.value}\t$name"

}

