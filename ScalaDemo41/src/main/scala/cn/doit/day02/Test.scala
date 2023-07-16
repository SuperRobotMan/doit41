package cn.doit.day02

import scala.util.control.Breaks.{break, breakable}

//import scala.util.control.Breaks._

object Test {
  def main(args: Array[String]): Unit = {

    println(_01_方法.getMax(1, 2, 3))

    val person: Person = new Person
    person.abc(1,2)
//    val a = 10
//    val arr = Array(1,2,3,4,5)   new Person(1,zss,18,male)
//    println(arr)//[I@6a41eaa2

//    a = 20
//    arr = Array(1,2,3,4,5)


  /*  var b = 10
    var arr1 = Array(1,2,3,4,5)
    println(arr1)//[I@7cd62f43

    b = 20
    arr1 = Array(1,2,3,4,5)

    val inclusive: Range.Inclusive = 1.to(10)
    for(elem <- 1 to 10 by 2 if elem != 5){

    }*/


    //一切皆对象 ==》 break
//    val breaks: Breaks = new Breaks()
    //不用new
    /*
    1. new Breaks()  获取到对象，用对象去调用方法，breakable(需要中断的代码)   break()   中断的方法(break)
    2. 用类名调用方法 Breaks.breakable()   Breaks.break()
    3. 先导包，然后直接用方法来中断
     */

    //方法是静态的   用类名去调用方法
    breakable(
      for (elem <- 1 to 10 ){
        println(elem)
        if (elem == 5) {
          break()
        }
      }
    )

    //只打印到1-5
    //抛异常 =>循环的中断






    println("各位该醒醒了！！！")






  }

}
