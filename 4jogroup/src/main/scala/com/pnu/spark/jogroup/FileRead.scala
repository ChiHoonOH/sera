package com.pnu.spark.jogroup

import org.apache.spark.SparkContext
import org.apache.spark.SparkConf

object FileRead {
      val conf=new SparkConf().setMaster("local[*]").setAppName("wow")
  val sc=new SparkContext(conf)
  val good=sc.wholeTextFiles("hdfs://master:9000/data/")  
  def main(args: Array[String]): Unit = {

      println(good.collect.mkString(","))
  }

}