package sample1.domain.support

/**
  * エンティティの責務を表すトレイト
  * @tparam ID 識別子
  */
trait Entity[ID <: Identifier[_]] {

  /** エンティティの識別子 */
  val identifier: ID

  /**
    * ハッシュコードを返す
    * @return ハッシュコード
    */
  override final def hashCode: Int = identifier.hashCode

  /**
    * 指定されたオブジェクトと等価であるかを判定する
    * @param that オブジェクト
    * @return 等価である場合はtrue
    */
  override final def equals(that: Any): Boolean = that match {
    case that: Entity[_] => identifier.value == that.identifier.value
    case _ => false
  }

}

