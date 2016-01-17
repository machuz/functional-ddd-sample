package sample1.infrastructure.scalikejdbc.mysql.models

import scalikejdbc.config.DBs
import scalikejdbc.specs2.mutable.AutoRollback
import org.specs2.mutable._
import scalikejdbc._
import org.joda.time.DateTime

class TUsersSpec extends Specification {

  DBs.setupAll()

  "TUsers" should {

    val tu = TUsers.syntax("tu")

    "find by primary keys" in new AutoRollbackFixture {
      val maybeFound = TUsers.find(testData.id)
      maybeFound.isDefined should beTrue
    }
    "find by where clauses" in new AutoRollbackFixture {
      val maybeFound = TUsers.findBy(sqls.eq(tu.id, testData.id))
      maybeFound.isDefined should beTrue
    }
    "find all records" in new AutoRollbackFixture {
      val allResults = TUsers.findAll()
      allResults.size should be_>(0)
    }
    "count all records" in new AutoRollbackFixture {
      val count = TUsers.countAll()
      count should be_>(0L)
    }
    "find all by where clauses" in new AutoRollbackFixture {
      val results = TUsers.findAllBy(sqls.eq(tu.id, testData.id))
      results.size should be_>(0)
    }
    "count by where clauses" in new AutoRollbackFixture {
      val count = TUsers.countBy(sqls.eq(tu.id, testData.id))
      count should be_>(0L)
    }
    "create new record" in new AutoRollbackFixture {
      val created = TUsers.create(name = "name_2", createdAt = DateTime.now, updatedAt = DateTime.now)
      created should not beNull
    }
    "save a record" in new AutoRollbackFixture {
      val entity = TUsers.find(testData.id).head
      val modified = entity.copy(name = "modified_name")
      val updated = TUsers.save(modified)
      updated should not equalTo(entity)
    }
    "destroy a record" in new AutoRollbackFixture {
      val entity = TUsers.findAll().head
      TUsers.destroy(entity)
      val shouldBeNone = TUsers.find(testData.id)
      shouldBeNone.isDefined should beFalse
    }
    "perform batch insert" in new AutoRollbackFixture {
      val entities = TUsers.findAll()
      entities.foreach(e => TUsers.destroy(e))
      val batchInserted = TUsers.batchInsert(entities)
      batchInserted.size should be_>(0)
    }
  }

  trait AutoRollbackFixture extends AutoRollback {
    val testData = createTestData

    def createTestData(implicit session: scalikejdbc.DBSession): TUsers = {
      TUsers.create(name = "name_1", createdAt = DateTime.now, updatedAt = DateTime.now)
    }
  }

}
