package sample1.domain.lifecycle.user

import sample1.domain.model.user.{UserId, User}
import sample1.infrastructure.scalikejdbc.mysql.models.TUsers

trait UserDxoOnJDBC extends UserDxo[User, TUsers] {

  override def toEntity(d: TUsers): User = User(UserId(d.id), d.name, d.createdAt, d.updatedAt)

  override def toDataModel(e: User): TUsers = TUsers(e.id.value, e.name, e.createdAt, e.updatedAt)

}
