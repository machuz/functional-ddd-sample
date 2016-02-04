package sample1.domain.lifecycle.message.fujitask

import sample1.core.util.fujitask.{Task, ReadTransaction}
import sample1.domain.model.message.{Message, MessageId}
import sample1.domain.support.FTaskRepository

trait MessageRepository extends FTaskRepository[MessageId, Message] {

  /**
    * エンティティをすべて取得する
    *
    * @return すべてのエンティティ
    */
  def resolveAll: Task[ReadTransaction, List[Message]]

}
