package sample1.domain.support

/**
  * Entityの識別子を表すトレイト
  * @tparam A 識別子の値型
  */
trait Identifier[+A] {

  def value: A

  val isDefined: Boolean = true

  val isEmpty: Boolean = !isDefined

  override def equals(obj: Any) = obj match {
    case that: Identifier[_] =>
      value == that.value
    case _ => false
  }

  override def hashCode = 31 * value.##

}

/**
  * 空のEntity識別子を表すオブジェクト
  */
object EmptyIdentifier extends EmptyIdentifier

/**
  * 空のEntity識別子を表すトレイト
  */
trait EmptyIdentifier extends Identifier[Nothing] {

  def value: Nothing = throw new NoSuchElementException

  override val isDefined = false

  override def equals(obj: Any) = obj match {
    case that: EmptyIdentifier => this eq that
    case _ => false
  }

  override def hashCode = 0
}
