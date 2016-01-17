package sample1.core.util.fujitask

trait Transaction

trait ReadTransaction extends Transaction

trait ReadWriteTransaction extends ReadTransaction
