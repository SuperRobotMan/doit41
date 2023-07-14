package cn.doit.day01

object _02_字符串得输出 {
  def main(args: Array[String]): Unit = {
    val name = "zss"
    val age = 18
    val height = 1.85
    //打印这么一句话：我的名字叫zss，我今年18岁了，我得身高是1.85米
    println("我的名字叫"+name+"，我今年"+age+"岁了，我得身高是"+height+"米")

    //我的名字叫zss，我今年18岁了，我得身高是1.85米
    //模板字符串
    println(s"我的名字叫${name}，我今年${age+1}岁了，我得身高是${height}米")

    /**
     * printf中格式化输出的模板
           %d 十进制数字
           %s 字符串
           %c 字符
           %e 指数浮点数
           %f 浮点数
           %i 整数（十进制）
           %o 八进制
           %u 无符号十进制
           %x 十六进制
     */
    printf("我的名字叫%s，我今年%d岁了，我得身高是%.2f米\r\n",name,age,height)

    /**
     * 多行得模板字符串
     * spark sql
     */
    /*
    sql = select
    name,age
    from  table_name
    where name = zss
    and age = 18
     */
    val sql =
      s"""
        |select
        |name,age
        |from table_name
        |where name = ${name}
        |and age = ${age}
        |""".stripMargin

    val sql1 =
      """
        |
        |""".stripMargin

        println(sql)



  }

}
