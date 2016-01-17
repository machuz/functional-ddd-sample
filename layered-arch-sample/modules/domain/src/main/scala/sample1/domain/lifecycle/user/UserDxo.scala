package sample1.domain.lifecycle.user

trait UserDxo[A, B] {

  def toEntity(d: B): A

  def toDataModel(e: A): B

}
