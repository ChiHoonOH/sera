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
     
  val conf=new SparkConf().setAppName("wow")
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
  
  //val inputpath="hdfs://master:9000/data/"
  val inputpath="/home/hadoop/Data/ana_data/test"
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
               //emit(get?) second component of tuple               
               .flatMap(x => x)//이거 결과가 문장 단위                    
               /* .toDF()    */
  //_1번에는 알집 파일 경로 및 이름이 있다.
/*   val raw_context= sc.binaryFiles(inputpath, 2)
               .flatMapValues(x => extractFiles(x).toOption)
               //아마 문서 내용 인코딩
               .map(_._1)//_ is placeholder, _2 is member of case class Tuple.
               .flatMap(x=>x)//emit(get?) second component of tuple               
               //이거 결과가 문장 단위              
*/ 
 var j=0
  val indices_DS=raw.zipWithIndex().toDS()
  //println(indices_DS.where('_2 === lit(j)).select('_1).as[String].flatMap(_.split("\n")).flatMap(_.split(" ")).where('value.contains("NNP")).show())
  
  
  
  
  
   var states = collection.mutable.Map[Int, Dataset[String]]()
   
 while(j<indices_DS.count().intValue()){
 states+=(j->indices_DS.where('_2 === lit(j)).select('_1).as[String].flatMap(_.split("\n")).flatMap(_.split(" ")).where('value.contains("NNP")))     
  }
  println(states.get(0))
  // println(raw.collect()//mkString("\n") -> System.out.println()
 //  println(raw.map(x=>x).collect().mkString("\n"))
   /*.flatMap(x=>if((x._1).contains("NNP") &&(x._2)==j)(x._1)))    */
 //  println(raw.zipWithIndex().map(x=>if((x._1).contains("NNP") &&(x._2)==j)(x._1)).collect().mkString(", "))
 //              println(raw.zipWithIndex().map(x=>if((x._2)==0){(x._1)} else "None").flatMap(_.split(" ")).collect().mkString(","))
 //  println(raw.zipWithIndex().map(x=>if((x._2)==0){(x._1)} else "None").flatMap(_.split(" ")).map(x=>if(x.contains("NNP"))x else "None").collect().mkString(","))     
  //   println(raw.zipWithIndex().flatMap(x=>if((x._1).contains("NNP") 
   



  
  
  
  
  val raw_DF=raw.toDF()
  val raw_DS=raw.toDS()
  
  val raw_DS_S=raw_DS.flatMap(_.split("\n")).flatMap(_.split(" "))
  
  //##1
 

  
  //##2
  
  val col=when(raw_DS_S("value").contains("NNP"),raw_DS_S("value")).as("result")
  val raw_DS_S2=raw_DS_S.select(raw_DS_S("value"),col)
  val raw_DS_D=raw_DS_S2.na.drop()  
  val raw_DS_F=raw_DS_D.select("result")
  //val raw_DS_unique=raw_DS_F.distinct()
  
  val k=raw_DS_F.withColumn("value", lit(1))
  //val result = df.withColumn("cutted", expr("substring(value, 1, length(value)-1)"))
   /*val k1=k.withColumn("substring",  substring_index('result,",",1))*/
   val k2=k.select(trim(substring_index('result,",",1)).as("result"))
  
 //  println(k2.dtypes) 
  
 // println(k2.show(10))
    
  
 // println(k.show(10))
/*  println(raw_DS_F.count())
  println(raw_DS_F.schema)  
  println(raw_DS_F.show(100))*/
 
  //substring 써서 만드는 건 나중에
  /*val raw_mr=raw_DS_F.rdd.map(row->substring_index(row,",",1).reduceByKey(_+_)  */
 
  //.withColumn("tm", expr("substring(value, 1, length(value)-1)"))
  //println(mr_e.show(100))
  //println(raw_mr.collect().mkString("\n"))
  //println(raw_mr.count())
  //println(raw_mr.first())
  //println(raw_mr)
  
  //println(raw_DF.head())
 
  //println(raw.toDF().show(20))
      
                
  //val result_1=raw.collect()//배열만 만들어준다. 그래서 출력하면 주소값만 보여줌
 // println(result_1.mkString(","))
  
  //val result=raw.map(x=>x.split(",")).map((_,1)).reduceByKey(_+_).toDS()
  //println(result.show(40))
  /*println(raw.collect().mkString(","))*/
  /*RDD의 결과를 표현하기 위해 1차적으로 리스트로 반환해준다. mkString이 주소
  값에서부터 값을 받아와 주는 역할을 한다고 생각한다.*/
  /*
  val raw_NN=raw.map(x=>x.split(",")).map(
      x=>if(x.contains("NN")) x else null
     )
  
  
  println(raw_NN.collect().mkString(","))
  
  val rawDF_NN=raw_NN.toDF("오호라")
  println(rawDF_NN.show(20))
  println("NN출력 20")
 
  val rawDF=raw.map(x=>x.split(",")).toDF("strings")
     println(rawDF.show(2)) 
     println(rawDF.printSchema())
     println(rawDF.count)
     
     
     rawDF.where('strings like "%NN%").show(10)  
      println(rawDF.limit(10).show(10))
    val rawDS= raw.toDS()
*/
 ////DTM만들기 프로젝트ㅐ
 
   //단어 모음
   
var raw_DS_unique=raw_DS_F.distinct()

val raw_DSrdd=raw.flatMap(_.split("\n")).flatMap(_.split(" ")).map((_,1)).reduceByKey(_+_).toDS()
/*println(raw_DSrdd.show(100))*/
val col2=when(raw_DSrdd("_1").contains("NNP") && length(raw_DSrdd("_1"))>1,raw_DSrdd("_1")).as("NNP")
val DS=raw_DSrdd.select(col2,'_2)
val DS_na=DS.na.drop()

raw.flatMap(_.split("\n")).flatMap(_.split(" ")).map(x=>if(x.contains("NNP"))x)
//1차 NNP만 거르기, 결과는 raw_DS와 같은 형태 //2차 레이블 붙이기//
val raw_DSindex=raw.zipWithIndex().toDS.withColumnRenamed("_1", "result")
  
/*println(raw_DSindex.where('_2===lit(0)).select(col("result")).as[String].rdd.map(_.split(" ")).show())*/
//println(raw_DS_unique.join(raw_DSindex.where('_2===lit(0))).show())

/*println(raw.flatMap(_.split(" ")).map(x=>if(x.contains("NNP"))x).collect().mkString("\n"))
    */
/*  var i=0
  var unique=DS
 while(i<raw_DS_unique.count().intValue()){
var unique=raw_DS_unique.join(raw_DSindex.where('_2===lit(i)).flatMap(x=>if(x.getString(i).contains("NNP") x),Seq("result"),"left")
  i=i+1
  }  
  println(unique.show(500))*/
  
/*val x=List("바보","축구","온달","호구","찌질이","고수","얼간이")
val y=List("바보","온달","호구","바보")
val z=List("축구","온달")
val x_DS=x.toDS()
val y_DS=sc.parallelize(y).map((_,1)).toDS().union(z.map((_,1)).toDS()).withColumnRenamed("_1","value")
val z_DS=y.toDS().union(z.toDS())
println(z_DS.show())*/

/*for(1 to 
println(x_DS.join(y_DS,Seq("value"),"left").show())*/

  
/*
val raw_DSrdd2=raw.flatMap(_.split(" ")).map((_,1)).reduceByKey(_+_).toDS()


val row_num=sc.parallelize(1 to raw_DS_unique.count().intValue()).toDS()
*/
/*println(raw_DS.count())
println(raw_DS.show(400))*/

//println(raw_DS_unique.count())
//write
//raw_DS_unique.write.format("com.databricks.spark.csv").save("hdfs://master:9000/output_spark/unique.csv")
//raw_DS.write.save("hdfs://master:9000/output_spark/DS")
//raw_DS.write.format("com.databricks.spark.csv")save("hdfs://master:9000/output_spark/DS.csv")


/*val raw_DSrdd=raw.flatMap(numbers=>{
   if (numbers.split("\n").split(" ").contains("NNP") && numbers.split("\n").split(" ").length()>1)numbers
   else {null}
}).map((_,1)).reduceByKey(_+_).toDS()*/


/*val raw_DSrdd2=raw.flatMap(x=>x.split("\n")).map(x=>(if(x.split(" ").contains("NNP"))x,1))*/

/*val raw_DSrdd2=when(raw_DSrdd("_1").contains("NNP"),raw_DSrdd("_1")).as("NNP")
 val raw_DSrdd3=raw_DSrdd.select(raw_DSrdd2,'_2)
 val raw_DSrdd4=raw_DSrdd3.na.drop()  
println(raw_DSrdd4.show(200))*/


//DS row 단위로 분해





//raw_DS.write.save("hdfs://master:9000/output_spark/raw_DS")
//raw_DS_unique.write.save("hdfs://master:9000/output_spark/raw_DS_unique")
/*raw_DS_unique.createOrReplaceGlobalTempView("back")
println(spark.sql("select * from global_temp.back").show())*/
     
 


//println(raw_DSn.show())     



/*for(a<-Seq(1 to number)){  
val ds1= raw_DSn.withColumnRenamed("value", "result").where('no===lit(b)).withColumn("value", lit(1))
//println(ds1.show())
ds_result=raw_DS_unique.join(ds1,Seq("result"),"left_outer")
b=b+1
}*/




/*println(ds1.printSchema())
println(ds1.dtypes)
println(ds1.schema)
println(raw_DS_unique.printSchema())
println(raw_DS_unique.dtypes)
println(raw_DS_unique.schema)*/

/* val col1=when(raw_DS_unique("result").contains(ds1.col("value")),1).as("ds1")
  val raw_DS1=raw_DS_unique.select(raw_DS_unique("result"),col1)
 println(raw_DS1.show(100))*/
   //대상 데이터넷 raw_DS
// val col=     
 
/* val sentence="Spark SQL, DataFrames and Datasets Guide"
 println(List(sentence).toDS.show())*/

  //val col=when(raw_DS_unique("result").contains(flatMap(raw_DS_unique)),raw_DS_S("value")).as("result")
 
 
  

}



}