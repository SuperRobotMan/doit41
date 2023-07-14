package cn.doit.day01

import scala.collection.immutable
import scala.util.Random

object _06_for循环 {
  def main(args: Array[String]): Unit = {
    val arr: Array[Int] = Array(1, 2, 3, 4, 5)

//    for (elem <- arr) {
//      println(elem)
//    }

    //序列  to   [0,4]
    //     until [0,5)

//    for (elem <- 0 until 4) {
//      println(elem)
//    }
//

    //循环步长  by
/*    for(elem <- 1 to 10 by 2){
      println(elem)
    }*/

    val reverse: Range = 1.to(10)//1-10
    val reverse1: Range = reverse.reverse //10-1

    //反转1 to 10 reverse
  /*  for(elem <- 1 to 10 reverse){
      println(elem)
    }

    //1-10中，我不想要5
    for(elem <- 1 to 10 ){
      if(elem != 5){
        println(elem)
      }
    }

    //循环守卫
    for(elem <- 1 to 10 if (elem != 5) ){
        println(elem)
    }
    */
    //双重for循环
    //打印99乘法表
    for(i <- 1 to 9 ){
      for (j <- 1 to i ){

      }
      println()
    }

    println("================")

    for(i <- 1 to 9 ;j <- 1 to i){
      print(s"${i} * ${j} = ${i*j} \t")
      if(i == j) println()
    }



    //引入变量
    for(i <- 1 to 3;j = 4 - i ) {
      println("i=" + i + " j=" + j)
    }



    //默认代码块中得最后一行，就是代码块得返回值类型
    val hello: String = {
      println("hello")
      "1"
    }

    val random: Random = new Random()
    val num = random.nextInt(100)

    val res = if(num >10){
      520
    }else if(num == 10){
      //Unit
    }


    //yield  用来收集for循环中得返回值
    val res1: immutable.Seq[Int] = for(elem <- 1 to 10) yield{
      elem
    }









  }

}
