package com.pnu.spark.jogroup
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream
import org.apache.spark.input.PortableDataStream
import scala.util.Try  

import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.apache.spark.sql._
import org.apache.spark.sql.Column
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import scala.collection.Seq


/*import java.nio.charset._*/  

object Ex {
       
  val conf=new SparkConf().setMaster("local[*]").setAppName("wow")
  val sc=new SparkContext(conf)
  /*val spark=SparkSession.builder().appName("haha").config("spark.some.config.option", "some-value").master("local[*]").getOrCreate()*/
  
 
  //yarn-cluster
 val spark = SparkSession.builder()
    .appName("HDP Test Job")
    .master("yarn")
    .config("spark.hadoop.fs.defaultFS", "hdfs://master:9000")
    .config("spark.yarn.jars", "hdfs://master:9000/spark/jars/*.zip")
    .config("spark.hadoop.yarn.resourcemanager.address", "master:8032")
    .config("spark.hadoop.yarn.application.classpath", 
       "$HADOOP_CONF_DIR,$HADOOP_COMMON_HOME/*,$HADOOP_COMMON_HOME/lib/*,$HADOOP_HDFS_HOME/*,$HADOOP_HDFS_HOME/lib/*,$HADOOP_MAPRED_HOME/*,$HADOOP_MAPRED_HOME/lib/*,$HADOOP_YARN_HOME/*,$HADOOP_YARN_HOME/lib/*")
    .enableHiveSupport()
.getOrCreate()




  val sqlContext = new org.apache.spark.sql.SQLContext(sc)
  import sqlContext.implicits._
  
  val inputpath="hdfs://master:9000/data/"
  /*val rdd=sc.textFile("hdfs://master:9000/data/
*/  def extractFiles(ps: PortableDataStream, n: Int = 5000) = Try {
  val tar = new TarArchiveInputStream(new GzipCompressorInputStream(ps.open))
  Stream.continually(Option(tar.getNextTarEntry))//다음 기록 리
    // Read until next exntry is null
    .takeWhile(_.isDefined)
    // flatten
    .flatMap(x => x)
    // Drop directories
    .filter(!_.isDirectory)
    .map(e => {
      Stream.continually {
        // Read n bytes
        val buffer = Array.fill[Byte](n)(-1)
        val i = tar.read(buffer, 0, n)
        (i, buffer.take(i))}
      // Take as long as we've read something  
      .takeWhile(_._1 > 0)
      .map(_._2)
      .flatten
      .toArray})
    .toArray
}



def decode(charset: Charset = StandardCharsets.UTF_8)(bytes: Array[Byte]) = 
  new String(bytes, StandardCharsets.UTF_8)


def main(args: Array[String]): Unit = {
  
  val raw = sc.binaryFiles(inputpath, 2)
               .flatMapValues(x => extractFiles(x).toOption)
               .mapValues(_.map(decode()))//아마 문서 내용 인코딩
               .map(_._2)//_ is placeholder, _2 is member of case class Tuple.
                         
               .flatMap(x => x)//이거 결과가 문장 단위                    


  val raw_DS=raw.toDS()
  
  val raw_DS_S=raw_DS.flatMap(_.split("\n")).flatMap(_.split(" "))
  
  //##1
 
 
  
  //##2
  
  val col=when(raw_DS_S("value").contains("NNP"),raw_DS_S("value")).as("result")
  val raw_DS_S2=raw_DS_S.select(raw_DS_S("value"),col)
  val raw_DS_D=raw_DS_S2.na.drop()  
  val raw_DS_F=raw_DS_D.select("result")
   
var raw_DS_unique=raw_DS_F.distinct()
//end of distinct


val raw_DSrdd=raw.flatMap(_.split("\n")).flatMap(_.split(" ")).map((_,1)).reduceByKey(_+_).toDS()
val col2=when(raw_DSrdd("_1").contains("NNP") && length(raw_DSrdd("_1"))>1,raw_DSrdd("_1")).as("NNP")
val DS=raw_DSrdd.select(col2,'_2)
val DS_na=DS.na.drop()
  val raw_DSindex=raw.zipWithIndex().toDS.withColumnRenamed("_1", "result")

    println(raw_DSindex.show(100))     
  }
 
 /* var i=0
  var unique=DS
 while(i<raw_DS_unique.count().intValue()){
var unique=raw_DS_unique.join(raw_DSindex.where('_2===lit(i)),Seq("result"),"left")
  i=i+1
  }
 */ 

  




 
  





}