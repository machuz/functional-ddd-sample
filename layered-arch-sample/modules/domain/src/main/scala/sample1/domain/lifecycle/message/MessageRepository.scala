package sample1.domain.lifecycle.message

import sample1.core.util.fujitask.{ReadTransaction, Task}
import sample1.domain.model.message.{Message, MessageId}
import sample1.domain.support.Repository

trait MessageRepository extends Repository[MessageId, Message] {

  /**
    * エンティティをすべて取得する
    *
    * @return すべてのエンティティ
    */
  def resolveAll: Task[ReadTransaction, List[Message]]

}

