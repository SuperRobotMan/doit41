import scala.collection.{immutable, mutable}
import scala.util.Random

object Test {
  def main(args: Array[String]): Unit = {
    val inclusive: immutable.Seq[Int] = 0 to 19
    val res = assignReplicasToBrokersRackUnaware(100, 10, inclusive, -1, -1)

    res.toList.sortBy(_._1).foreach(println)

  }

  private def assignReplicasToBrokersRackUnaware(
                                                  nPartitions: Int, //分区的个数   10
                                                  replicationFactor: Int,  //副本的个数  5
                                                  brokerList: Seq[Int],//broker的集合    8   0~7
                                                  fixedStartIndex: Int,//默认值是-1  固定开始的索引位置
                                                    startPartitionId:Int): mutable.Map[Int, Seq[Int]] //默认值是-1 分区开始的位置
  = {
    val ret = mutable.Map[Int, Seq[Int]]()
    val brokerArray = brokerList.toArray
    val startIndex = if (fixedStartIndex >= 0) {
      fixedStartIndex
    }else {
//      val rand = new Random()
//      rand.nextInt(brokerArray.length)
      8
    }
    var currentPartitionId = math.max(0, startPartitionId)
    var nextReplicaShift = if (fixedStartIndex >= 0) {
      fixedStartIndex
    }else {
//      val rand = new Random()
//      rand.nextInt(brokerArray.length)
      6
    }
    for (_ <- 0 until nPartitions) {
      if (currentPartitionId > 0 && (currentPartitionId % brokerArray.length == 0)){
        nextReplicaShift += 1
      }

      val firstReplicaIndex = (currentPartitionId + startIndex) % brokerArray.length
      val replicaBuffer = mutable.ArrayBuffer(brokerArray(firstReplicaIndex))
      for (j <- 0 until replicationFactor - 1) {
        replicaBuffer += brokerArray(replicaIndex(firstReplicaIndex, nextReplicaShift, j, brokerArray.length))
      }
      ret.put(currentPartitionId, replicaBuffer)
      currentPartitionId += 1
    }
    ret
  }

  private def replicaIndex(firstReplicaIndex: Int, secondReplicaShift: Int, replicaIndex: Int, nBrokers: Int): Int = {
    val shift = 1 + (secondReplicaShift + replicaIndex) % (nBrokers - 1)
    (firstReplicaIndex + shift) % nBrokers
  }

}
