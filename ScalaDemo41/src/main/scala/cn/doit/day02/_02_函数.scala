package cn.doit.day02

/**
 * 函数和方法一样，都是给一段代码逻辑进行封装，便于后续的使用
 * 函数可以当做参数传递，可以当做返回值返回  ==>
 *
 * 写一个函数=》 啥都不传，返回一个字符串 ==》 结果是所有的水仙花数的一个拼接    1 2 3  1,2,3
 *
 *
 * 写一个函数，穿进去一个数，然后判断一下是不是水仙花数，最后打印一下，是还是不是  ==》
 *
 * 水仙花数： 是一个三位数    123   1*1*1 + 2*2*2 + 3*3*3 = 123 这就是一个水仙花数
 */
object _02_函数 {
  def main(args: Array[String]): Unit = {

    //() 函数的参数
    //{} 函数体
    //=> 数据的流向 函数声明的标志
    //给两个int类型的值，返回两个里面的最大值
    val getMax = (a: Int, b: Int) => {
      if (a > b) a else b
    } //没有名字的函数，叫匿名函数

    //三个数求最大值 Function3[Int,String,Double,Int]      (Int, String, Double) => Int
    //             java 的 写法（正统写法）                ()=>{}   语法糖
    val get3Max: (Int, String, Double) => Int = (a: Int, b: String, c: Double) => {
      var max = a
      if (b.toInt > max) max = b.toInt
      if (c.toInt > max) max = c.toInt
      max
    }

    val sayXiang: () => Unit = () => {
      println("翔哥威武")
    }

    val res: Int = get3Max(10, "20", 8.0)
    println(res)


    //Function3 getMax
    // Person p = new Person

    val getOne = (a: Int) => {
      a + 10
    }

    val abc: (Int) => Int = getOne
    /*
     Person p = new Person()
     Person p1 = p
     */
    getOne.apply(1)
    abc.apply(1)


    val test = (a: Int, b: (Int, String) => String) => {
      val str: String = b(a, "hello")
      str
    }

    //    val fun = (a:Int,b:String)=>{a+" ==> "+b}

    //    val str: String = test(1, (a:Int,b:String)=>{a+" ==> "+b})
    //    println(str)


    var str = ""

    //找出所有的水仙花数
    val waterFlower = () => {
      for (elem <- 100 to 999) {
        val ge = elem % 10
        val shi: Int = elem / 10 % 10
        val bai: Int = elem / 10 / 10
        if ((Math.pow(ge, 3) + Math.pow(shi, 3) + Math.pow(bai, 3)).toInt == elem) {
          //如果相等，代表是一个水仙花数
          str += elem + " "
        }
      }
      str
    }

    println(waterFlower())
    //传一个数值给他，告诉你是不是水仙花数
    val isWaterFlower = (elem: Int) => {
      val ge = elem % 10
      val shi: Int = elem / 10 % 10
      val bai: Int = elem / 10 / 10
      if ((Math.pow(ge, 3) + Math.pow(shi, 3) + Math.pow(bai, 3)).toInt == elem) {
        println(elem + "是水仙花数")
      } else {
        println("不是水仙花数")
      }
    }

    isWaterFlower(153)



  }

}
