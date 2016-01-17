package sample1.domain.service.message

import org.joda.time.DateTime
import sample1.domain.lifecycle.message.{MessageRepositoryOnJDBC, MessageRepository}
import sample1.domain.lifecycle.user.{UserRepositoryOnJDBC, UserRepository}
import sample1.domain.model.message.{EmptyMessageId, MessageId, Message}
import sample1.domain.model.user.UserId
import sample1.infrastructure.scalikejdbc._

import scala.concurrent.Future

object MessageService {

  val userRepository: UserRepository = UserRepositoryOnJDBC

  val messageRepository: MessageRepository = MessageRepositoryOnJDBC

  def create(message: String, userName: String): Future[Message] =
    messageRepository.store(Message(EmptyMessageId, message, userName, DateTime.now, DateTime.now)).run()

  def update(message: Message): Future[Message] =
    messageRepository.store(message).run()

  def read(id: MessageId): Future[Option[Message]] =
    messageRepository.resolveById(id).run()

  def readAll: Future[List[Message]] =
    messageRepository.resolveAll.run()

  def delete(id: MessageId): Future[Unit] =
    messageRepository.deleteById(id).run()

  def createByUserId(message: String, userId: UserId): Future[Message] = {
    val task =
      for {
        userOpt <- userRepository.resolveById(userId)
        user = userOpt.getOrElse(throw new IllegalArgumentException("User Not Found"))
        message <- MessageRepositoryOnJDBC.store(Message(EmptyMessageId, message, user.name, DateTime.now, DateTime.now))
      } yield message

    task.run()
  }
}

