package sample1.infrastructure.scalikejdbc.mysql.models

import scalikejdbc.config.DBs
import scalikejdbc.specs2.mutable.AutoRollback
import org.specs2.mutable._
import scalikejdbc._
import org.joda.time.DateTime

class TMessagesSpec extends Specification {

  DBs.setupAll()

  "TMessages" should {

    val tm = TMessages.syntax("tm")

    "find by primary keys" in new AutoRollbackFixture {
      val maybeFound = TMessages.find(testData.id)
      maybeFound.isDefined should beTrue
    }
    "find by where clauses" in new AutoRollbackFixture {
      val maybeFound = TMessages.findBy(sqls.eq(tm.id, testData.id))
      maybeFound.isDefined should beTrue
    }
    "find all records" in new AutoRollbackFixture {
      val allResults = TMessages.findAll()
      allResults.size should be_>(0)
    }
    "count all records" in new AutoRollbackFixture {
      val count = TMessages.countAll()
      count should be_>(0L)
    }
    "find all by where clauses" in new AutoRollbackFixture {
      val results = TMessages.findAllBy(sqls.eq(tm.id, testData.id))
      results.size should be_>(0)
    }
    "count by where clauses" in new AutoRollbackFixture {
      val count = TMessages.countBy(sqls.eq(tm.id, testData.id))
      count should be_>(0L)
    }
    "create new record" in new AutoRollbackFixture {
      val created = TMessages.create(message = "message_2", userName = "name_2", createdAt = DateTime.now, updatedAt = DateTime.now)
      created should not beNull
    }
    "save a record" in new AutoRollbackFixture {
      val entity = TMessages.find(testData.id).head
      val modified = entity.copy(userName = "modified_name")
      val updated = TMessages.save(modified)
      updated should not equalTo entity
    }
    "destroy a record" in new AutoRollbackFixture {
      val entity = TMessages.findAll().head
      TMessages.destroy(entity)
      val shouldBeNone = TMessages.find(testData.id)
      shouldBeNone.isDefined should beFalse
    }
    "perform batch insert" in new AutoRollbackFixture {
      val entities = TMessages.findAll()
      entities.foreach(e => TMessages.destroy(e))
      val batchInserted = TMessages.batchInsert(entities)
      batchInserted.size should be_>(0)
    }
  }

  trait AutoRollbackFixture extends AutoRollback {
    val testData = createTestData

    def createTestData(implicit session: scalikejdbc.DBSession): TMessages = {
      TMessages.create(message = "message_1", userName = "name_1", createdAt = DateTime.now, updatedAt = DateTime.now)
    }
  }

}
