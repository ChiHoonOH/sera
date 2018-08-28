package com.pnu.spark.jogroup

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.Dataset
import org.apache.spark.sql._
import org.apache.spark.sql.functions._

object sfasdf {
  def main(args: Array[String]): Unit = {
        val spark = SparkSession.builder()
    .appName("HDP Test Job")
    .master("yarn")
    .config("spark.hadoop.fs.defaultFS", "hdfs://master:9000")
    .config("spark.yarn.jars", "hdfs://master:9000/spark/jars/*.zip")
    .config("spark.hadoop.yarn.resourcemanager.address", "master:8032")
    .config("spark.hadoop.yarn.application.classpath", 
       "$HADOOP_CONF_DIR,$HADOOP_COMMON_HOME/*,$HADOOP_COMMON_HOME/lib/*,$HADOOP_HDFS_HOME/*,$HADOOP_HDFS_HOME/lib/*,$HADOOP_MAPRED_HOME/*,$HADOOP_MAPRED_HOME/lib/*,$HADOOP_YARN_HOME/*,$HADOOP_YARN_HOME/lib/*")
     .config("spark.sql.pivotMaxValues",60000)  
     .config("spark.driver.memory","5g")
     .config("spark.executor.memory", "4g")
    .enableHiveSupport()
.getOrCreate()
    
    case class Person(name:String, age: Int,job:String)
    val row1=Person("hayoon",7,"student")
    val row2=Person("sunwoo",13,"student")
    val row3=Person("hajoo",5,"indergartener")
    val row4=Person("jinwoo",13,"student")
    val data=List(row1,row2,row3,row4)

    
  }

}