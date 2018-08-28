package com.pnu.spark.jogroup

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.Dataset
import org.apache.spark.sql._
import org.apache.spark.sql.functions._



object Ex2 {
  def run(inputPath: String, outputPath: String){
         val conf=new SparkConf().setAppName("wow")

  val sc=new SparkContext(conf)
    
         val sqlContext = new org.apache.spark.sql.SQLContext(sc)
  import sqlContext.implicits._
    
     val spark = SparkSession.builder()
    .appName("HDP Test Job")
    .master("yarn")
    .config("spark.hadoop.fs.defaultFS", "hdfs://master:9000")
    .config("spark.yarn.jars", "hdfs://master:9000/spark/jars/*.zip")
    .config("spark.hadoop.yarn.resourcemanager.address", "master:8032")
    .config("spark.hadoop.yarn.application.classpath", 
       "$HADOOP_CONF_DIR,$HADOOP_COMMON_HOME/*,$HADOOP_COMMON_HOME/lib/*,$HADOOP_HDFS_HOME/*,$HADOOP_HDFS_HOME/lib/*,$HADOOP_MAPRED_HOME/*,$HADOOP_MAPRED_HOME/lib/*,$HADOOP_YARN_HOME/*,$HADOOP_YARN_HOME/lib/*")
     .config("spark.sql.pivotMaxValues",60000)  
     .config("spark.driver.memory","2g")
     .config("spark.executor.memory", "1g")
    .enableHiveSupport()
.getOrCreate()
    val df=spark.read.text(inputPath)
    
    
    //val indices_DS=
    val raw_DS=df.as(Encoders.STRING)
   val raw_DS_S=raw_DS.flatMap(_.split("\n")).flatMap(_.split(" "))
    
   
      
  val col=when(raw_DS_S("value").contains("NNP"),raw_DS_S("value")).as("result")
  val raw_DS_S2=raw_DS_S.select(raw_DS_S("value"),col)
  val raw_DS_D=raw_DS_S2.na.drop()  
  val raw_DS_F=raw_DS_D.select("result").distinct()
  
     val indices_DS=df.as(Encoders.STRING).rdd.zipWithIndex().toDS()
     println(raw_DS_F.show()) 
      println(indices_DS.schema)
      println(indices_DS.show())
      
/*  indices_DS.groupBy("_2").pivot("_1").count().write.format("com.databricks.spark.csv").save(outputPath)*/
    
  }

  def main(args: Array[String]): Unit = {
      val numberOfArgs = 2

    if (args.length == numberOfArgs) {
      run(args(0), args(1))
    } else {
      println("Usage: $SPARK_HOME/bin/spark-submit --class <class_name> --master <master> --<option> <option_value> <jar_file_path> <input_path> <output_path>")
    }
  

 
    
    
    
    
    
    
    

  
  
  
  
      
   //   val number_1=indices_DS.where('_2 === lit(1)).select('_1).as[String].flatMap(_.split("\n")).flatMap(_.split(" ")).where('value.contains("NNP"))
    //   val number_2=number_1.withColumn("number", lit(1))
      
       
/*println(number_2.groupBy("number").pivot("value").count().show())*/

      /*println(indices_DS.groupBy(col).pivot(col))*/
    //println(indices_DS.show())
 /*var j=0   
      var states = collection.mutable.Map[Int, Dataset[String]]()
      
      

 while(j<indices_DS.count().intValue()){
 states+=(j->indices_DS.where('_2 === lit(j)).select('_1).as[String].flatMap(_.split("\n")).flatMap(_.split(" ")).where('value.contains("NNP")))     
  }
  println(states.get(0))*/
    

/*
기사1 기사2 기사3
1
2
3
4
*/

    
         
     
    
    
    
  }
}


