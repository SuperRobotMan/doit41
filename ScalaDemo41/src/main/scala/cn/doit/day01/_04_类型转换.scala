package cn.doit.day01

object _04_类型转换 {
  def main(args: Array[String]): Unit = {
    val i :Int= 10
    val l :Long= i
    // 强制类型转换
    val a = 10.0
    val b = 'a'

    val res: Double = i+l+a+b
    println(res)





  }

}
