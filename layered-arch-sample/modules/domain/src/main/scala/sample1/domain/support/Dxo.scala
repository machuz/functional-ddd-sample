package sample1.domain.support

trait Dxo[A, B] {

  def dataModelToEntity(dataModel: B): A

  def entityToDataModel(entity: A): B

}
