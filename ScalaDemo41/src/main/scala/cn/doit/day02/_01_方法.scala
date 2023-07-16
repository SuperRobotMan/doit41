package cn.doit.day02

/**
 * 方法就是一段代码逻辑的封装
 * 可以无限调用这个方法，来对这段代码逻辑进行复用
 * 注意点：
 * 返回值类型
 * 方法名
 * 参数
 *
 * def：方法声明的标志
 * main：方法名称   见名知意
 * (args: Array[String])   方法的参数列表
 * : Unit 返回值类型
 * {} 方法体
 */
object _01_方法 {
  def main(args: Array[String]): Unit = {
    val i: Int = add(1, 2)
    println(i)

    sayHello()

  }

  //写一个两个int类型的值相加的方法
  def add(a:Int,b:Int)={
    a+b
  }

  //三个数求最大值
  def getMax(a:Int,b:Int,c:Int):Int={
    //做一个假设 假设此时a最大
    var max = a
    if(b > max){max = b }
    if(c >max){max = c}
    max
  }

  def sayHello()={
    println("hello")
  }

  /**
   * 方法的定义
   * 返回值类型可以不写，他会根据你{}里面最后的一行代码来自动推断
   * 方法体里面，如果最后一行代码你添加了return ，那么，返回值类型必须得写
   * 方法的参数，可以没有  那么参数列表中的小括号可以省略
   */

  /**
   * 方法的调用
   * 1.看这个方法定义在哪里
   * class类里面，想要调用方法，必须new 对象，用对象名去调用方法
   * object中，在本类中调用，直接方法名，然后传参数就可以了,不是在本类里面调用，需要类名.方法名称
   * 2.如果说方法没有参数，那么调用的时候可以不写()
   * 如果说，定义方法的时候，()省略了，那么调用的时候，就不能加上
   */




}
