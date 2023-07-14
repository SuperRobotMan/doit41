package cn.doit.day01

/**
 * java 中定义变量=》 int a = 10;   int b ;
 *         int a = 10;
        a = 29;
        System.out.println(a);

        int b ;//局部变量  不赋予初始值，不能够使用
        b = 10;
        System.out.println(b);

        //常量   用final修饰的变量就是常量   一旦创建，不能够修改他的值
        final int c = 10;
//        c = 20;
 */
object _01_变量和常量_重点 {
  def main(args: Array[String]): Unit = {
    //在scala里面 如果想定义一个变量  var   variable
    //定义变量和常量的时候，可以不写数据类型  他会根据你后面传进来的值，做自动的类型推断
    //在定义变量或者常量的时候，必须得传值
    //优先使用val，因为val 得效率更高，线程是安全得
    var a = "xiangge"


    a = "xianggege"
    println(a.getClass)//class java.lang.String

    val b = 10
    println(b.getClass)//int



//    a = 29
//    System.out.println(a)
//
//    var b = 0 //局部变量  不赋予初始值，不能够使用
//    b = 10
//    System.out.println(b)
//
//    //常量   用final修饰的变量就是常量   一旦创建，不能够修改他的值
//    //常量在scala中用val修饰  val value  val修饰的变量叫常量
//    val c:Int = 10
//    c = 20



  }

}
