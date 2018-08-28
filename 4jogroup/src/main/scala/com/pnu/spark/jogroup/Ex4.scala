package com.pnu.spark.jogroup
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.Dataset
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import scala.collection.immutable.Map
object Ex4{
   def run(inputPath: String, outputPath: String){
   val conf=new SparkConf()
  
   .set("spark.shuffle.service.enabled", "false")

  .set("spark.dynamicAllocation.enabled", "false")

  .set("spark.io.compression.codec", "snappy")

  .set("spark.rdd.compress", "true")
  // .setJars()
   .setAppName("wow")
  val sc=new SparkContext(conf)    
         val sqlContext = new org.apache.spark.sql.SQLContext(sc)
  import sqlContext.implicits._    
     val spark = SparkSession.builder()
    .config("spark.driver.memory","1g")
     .config("spark.executor.memory", "4g")
     .appName("HDP Test Job")
    .master("yarn")
    .config("spark.hadoop.fs.defaultFS", "hdfs://master:9000")
    .config("spark.yarn.jars", "hdfs://master:9000/spark/jars/*.zip")
    .config("spark.hadoop.yarn.resourcemanager.address", "master:8032")
    .config("spark.hadoop.yarn.application.classpath", 
       "$HADOOP_CONF_DIR,$HADOOP_COMMON_HOME/*,$HADOOP_COMMON_HOME/lib/*,$HADOOP_HDFS_HOME/*,$HADOOP_HDFS_HOME/lib/*,$HADOOP_MAPRED_HOME/*,$HADOOP_MAPRED_HOME/lib/*,$HADOOP_YARN_HOME/*,$HADOOP_YARN_HOME/lib/*")
     .config("spark.sql.pivotMaxValues",60000)  
/*     .config("spark.driver.memory","5g")
     .config("spark.executor.memory", "4g")
    .enableHiveSupport()*/
.getOrCreate()
     


 val df=spark.read.text(inputPath)  
 val indice=df.as(Encoders.STRING).rdd.zipWithIndex()
 val indice_rev=indice.map(_.swap)
 val indice_DS=indice.toDS()
 
 val indice_draft=indice_rev.flatMapValues(_.split(" "))
 val indice_refine=indice_draft.filter(_._2.contains("NNP")).filter(_._2.length()>1)
 val indice_DST=indice_refine.toDS()
 val result= indice_DST.groupBy("_1").pivot("_2").agg(count("_1"))
 println(result.show())
result.write.format("com.databricks.spark.csv").save(outputPath) 
   }
   
     def main(args: Array[String]): Unit = {
    

    
      run("hdfs://master:9000/data2/", "hdfs://master:9000/output2/")
   
     }
}