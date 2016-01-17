package sample1.domain.lifecycle.message

trait MessageDxo[A, B] {

  def toEntity(d: B): A

  def toDataModel(e: A): B

}
