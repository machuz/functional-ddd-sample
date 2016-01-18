package sample1.domain.support

/**
 * リポジトリ責務を表すトレイト。
 *
 * @tparam ID 識別子の型
 * @tparam E エンティティの型
 */
trait MonadRepository[M[+_], ID <: Identifier[_], E <: Entity[ID]] {

  type This <: MonadRepository[M, ID, E]

  type Ctx = IOContext

  /**
   * エンティティを保存する
   * @param entity 保存するエンティティ
   * @return 保存したエンティティ
   */
  def store(entity: E): M[E]

  /**
   * 識別子に該当するエンティティを取得する
   * @param id 識別子
   * @return 識別子に該当するエンティティ
   */
  def resolveById(id: ID): M[Option[E]]

  /**
   * 指定した識別子のエンティティを削除する
   * @param id 識別子
   */
  def deleteById(id: ID): M[Unit]

}
