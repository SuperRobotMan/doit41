package cn.doit.day01

object _03_数据类型 {
  def main(args: Array[String]): Unit = {
    val unit: Unit = sayHello()
    println(unit)//()

//    val abc :Null = null
    //Nothing
  }

  def sayHello():Unit={
    println("hello")
  }

  def sayHi()={
    throw new Exception
  }



}
