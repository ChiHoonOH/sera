package com.pnu.spark.jogroup

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.Dataset
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import scala.collection.immutable.Map

object ex3 {
  
  def main(args: Array[String]): Unit = {    
    
    val conf=new SparkConf().setMaster("local[*]").setAppName("wow")
    .set("spark.driver.memory","5g")
    .set("spark.executor.memory","4g")
  val sc=new SparkContext(conf)    
         val sqlContext = new org.apache.spark.sql.SQLContext(sc)
  import sqlContext.implicits._    
     val spark = SparkSession.builder()
     .config("spark.driver.memory","5g")
     .config("spark.executor.memory", "4g")
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
     


 val df=spark.read.text("/home/hadoop/Data/ana_data/test")
 val raw_DS=df.as(Encoders.STRING)
 val raw_DS_S=raw_DS.flatMap(_.split("\n")).flatMap(_.split(" "))     
 val col=when(raw_DS_S("value").contains("NNP"),raw_DS_S("value")).as("result")
 val raw_DS_S2=raw_DS_S.select(raw_DS_S("value"),col)
 val raw_DS_D=raw_DS_S2.na.drop()  
 val raw_DS_F=raw_DS_D.select("result").distinct()
  
 val indice=df.as(Encoders.STRING).rdd.zipWithIndex()
 val indice_rev=indice.map(_.swap)
 val indice_DS=indice.toDS()
 
 val indice_draft=indice_rev.flatMapValues(_.split(" "))
 val indice_refine=indice_draft.filter(_._2.contains("NNP")).filter(_._2.length()>1)
 val indice_DST=indice_refine.toDS()
 println(indice_DST.groupBy("_1").pivot("_2").agg(count("_1")).show(2))
 //println(indice_DST.show(30))
 
/* val indice_DST=indice_refine.to*/
// println(indice_refine.collect().mkString("\n"))
//println(indice.map(_.swap).collect().mkString("\n"))
 indice_DS.createOrReplaceGlobalTempView("back")
// val pair: Map[Long,String]=spark.sql("select _2,_1 from global_temp.back")
 
 
 
indice.flatMap(_._1.split(" "))
indice.flatMap(x=>(x._1).split(" "))
 //println(indice_DS.show(20))
 //println(indice.collect().mkString("\n"))
 //println(indice.collect().mkString("\n"))
 //val indices=indice.map((_._1)).flatMap(_.split(" ")).map((_,1))
 
 
 
//println(indice.collect().mkString("\n"))
//val index=indice_DS.select("_1").map((_,1))
    
//println(indices.collect().mkString(","))
 
/* println(raw_DS_F.show()) 
 println(indices_DS.schema)
 println(indices_DS.show())
      */
//  println(indice_DS.groupBy("_2").pivot("_1").agg(count("_2")).show(2))//에러를 안뱉는 이유가 궁금
  


  
    
    
  }
  
  
}