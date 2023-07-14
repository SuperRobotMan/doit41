package cn.doit.day01

import scala.io.Source

object WordCountDemo {
  def main(args: Array[String]): Unit = {
    //读取数据文件
    Source.fromFile("data/word.txt").getLines()
      //转换成list集合
      .toList
      //对于读过来的一行数据进行切割成一个个的单词
      .flatMap(_.split(","))
      //对单词进行分组
      .groupBy(word=>word)
      //转换成单词和个数的形式
      .map(tp=>(tp._1,tp._2.size))
      //打印输出
      .foreach(println)
  }

}
