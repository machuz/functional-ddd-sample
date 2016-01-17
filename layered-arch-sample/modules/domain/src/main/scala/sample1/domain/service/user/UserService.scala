package sample1.domain.service.user

import org.joda.time.DateTime
import sample1.domain.lifecycle.user.{UserRepositoryOnJDBC, UserRepository}
import sample1.domain.model.user.{EmptyUserId, UserId, User}
import sample1.infrastructure.scalikejdbc._

import scala.concurrent.Future

object UserService {

  val userRepository: UserRepository = UserRepositoryOnJDBC

  def create(name: String): Future[User] =
    userRepository.store(User(EmptyUserId, name, DateTime.now, DateTime.now)).run()

  def update(user: User): Future[User] =
    userRepository.store(user).run()

  def read(id: UserId): Future[Option[User]] =
    userRepository.resolveById(id).run()

  def readAll: Future[List[User]] =
    userRepository.resolveAll.run()

  def delete(id: UserId): Future[Unit] =
    userRepository.deleteById(id).run()
  
}