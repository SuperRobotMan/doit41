package cn.doit.day01

import java.util.Scanner
import scala.util.Random

/**
 *猜数字小游戏
 * 1.生成一个目标数字
 * 2.我输入一个数字开始猜，如果猜的数字比原来的数字大，就打印猜大了
 * 如果猜的数字比原来的数字小，就打印猜小了
 * 如果猜的数字和原来的数字相等，就打印，猜对了
 */

object _05_猜数字小游戏 {
  def main(args: Array[String]): Unit = {
    val random: Random = new Random()
    val gussNum: Int = random.nextInt(100)
    println(gussNum)
    val sc: Scanner = new Scanner(System.in)
    var cnt = 1
    println("请输入需要猜得数字")
    var num: Int = sc.nextInt()
    while (cnt <5){
      if(num == gussNum){
        println("猜对了，滚蛋吧")
        return
      }else if(num > gussNum){
        println("大了，继续吧")
        num= sc.nextInt()
      }else{
        println("小了，继续吧")
        num= sc.nextInt()
      }
      if (cnt == 4){
        println("机会用完了")
      }
      cnt += 1

    }

  }

}
