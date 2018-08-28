package org.zerock.board.service.impl;

import java.util.ArrayList;
import java.util.Base64;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Scanner;

import java.net.URL;
import java.net.URLConnection;
import java.net.HttpURLConnection;

import org.rosuda.JRI.Rengine;
import org.springframework.beans.factory.annotation.Autowired;
import org.rosuda.JRI.REXP; 

public class HttpClient1 {

	private static Rengine engine = new Rengine(null, false, null);
	public double getRcode(String b)
	{
		double result = 0.0f;
		String[] xp = this.getMorphs(b);
		System.out.println("string value is ready");
		//BufferedReader br = new BufferedReader(new FileReader("C:/R/Predict.R"));
		//Rengine engine = new Rengine(null, false, null);
		
		System.out.println("Rengine is ready");
		engine.assign("newdata1", xp[0]);	
		engine.assign("newdata2", xp[1]);	

		REXP x;
		
		x = engine.eval("newdata1");     
        x = engine.eval("newdata2");

		x = engine.eval("load(file=\"C:/R/model.RData\")");
		//x = engine.eval(".libPaths('C:/Users/Sonyoungbae/Documents/R/win-library/3.5')");
		x = engine.eval(".libPaths('C:/Program Files/R/R-3.5.1/library')");
		x = engine.eval("library(proxy)");
		x = engine.eval("library(randomForest)");

		x = engine.eval("newdata1");	      
		x = engine.eval("newdata2");

		x = engine.eval("ex<-c(newdata1,newdata2)");
		x = engine.eval("ar1<-strsplit(ex[1],\" \")");
		x = engine.eval("ar2<-strsplit(ex[2],\" \")");
		x = engine.eval("pu1<-ar1[[1]][which((grepl(\"Punctuation\",ar1[[1]])==FALSE)&(grepl(\"Number\",ar1[[1]])==FALSE)&(grepl(\",,\",ar1[[1]])==FALSE)&(grepl(\"Hashtag\",ar1[[1]])==FALSE)&(grepl(\"Email\",ar1[[1]])==FALSE)&(grepl(\"ScreenName\",ar1[[1]])==FALSE)&(grepl(\"URL\",ar1[[1]])==FALSE))]");
		x = engine.eval("pu2<-ar2[[1]][which((grepl(\"Punctuation\",ar2[[1]])==FALSE)&(grepl(\"Number\",ar2[[1]])==FALSE)&(grepl(\",,\",ar2[[1]])==FALSE)&(grepl(\"Hashtag\",ar2[[1]])==FALSE)&(grepl(\"Email\",ar2[[1]])==FALSE)&(grepl(\"ScreenName\",ar2[[1]])==FALSE)&(grepl(\"URL\",ar2[[1]])==FALSE))]");
		x = engine.eval("title<-matrix(unlist(strsplit(pu1, \",\")), ncol=2, byrow=TRUE)");
		
		x = engine.eval("content<-matrix(unlist(strsplit(pu2, \",\")), ncol=2, byrow=TRUE)");
		x = engine.eval("nv1<-ar1[[1]][which((grepl(\"Noun\",ar1[[1]])==TRUE)|(grepl(\"Verb\",ar1[[1]])==TRUE)|(grepl(\"Determiner\",ar1[[1]])==TRUE)|(grepl(\"Adverb\",ar1[[1]])==TRUE)|(grepl(\"Josa\",ar1[[1]])==TRUE)|(grepl(\"PreEomi\",ar1[[1]])==TRUE)|(grepl(\"Eomi\",ar1[[1]])==TRUE)|(grepl(\"Suffix\",ar1[[1]])==TRUE)|(grepl(\"Alpha\",ar1[[1]])==TRUE))]");
		x = engine.eval("nv2<-ar2[[1]][which((grepl(\"Noun\",ar2[[1]])==TRUE)|(grepl(\"Verb\",ar2[[1]])==TRUE)|(grepl(\"Determiner\",ar2[[1]])==TRUE)|(grepl(\"Adverb\",ar2[[1]])==TRUE)|(grepl(\"Josa\",ar2[[1]])==TRUE)|(grepl(\"PreEomi\",ar2[[1]])==TRUE)|(grepl(\"Eomi\",ar2[[1]])==TRUE)|(grepl(\"Suffix\",ar2[[1]])==TRUE)|(grepl(\"Alpha\",ar2[[1]])==TRUE))]");
		x = engine.eval("bititle<-matrix(unlist(strsplit(nv1, \",\")), ncol=2, byrow=TRUE)");
		x = engine.eval("bicontent<-matrix(unlist(strsplit(nv2, \",\")), ncol=2, byrow=TRUE)");

		x = engine.eval("var1<-var3<-var4<-var5<-var6<-0");
		x = engine.eval("var21<-var22<-0");

		x = engine.eval("var6<-sum(grepl(\"Exclamation\", ar1[[1]])==TRUE)");
		x = engine.eval("var5<-title[nrow(title)-1,2]");
		x = engine.eval("var4<-var4+sum(grepl(\"노출\", ar1[[1]])==TRUE)");
		x = engine.eval("var4<-var4+sum(grepl(\"누드\", ar1[[1]])==TRUE)");
		x = engine.eval("var4<-var4+sum(grepl(\"섹시\", ar1[[1]])==TRUE)");
		x = engine.eval("var4<-var4+sum(grepl(\"글래머\", ar1[[1]])==TRUE)");
		x = engine.eval("var4<-var4+sum(grepl(\"속옷\", ar1[[1]])==TRUE)");
		x = engine.eval("var3<-sum(grepl(\"…\", ar1[[1]])==TRUE)");
		x = engine.eval("var21<-sum(nchar(title[,1]))");
		x = engine.eval("var22<-sum(nchar(content[,1]))");

		x = engine.eval("tf1<-table(bititle[,1])");
		x = engine.eval("tf11<-tf1[nchar(names(tf1))>1]");
		x = engine.eval("tf2<-bigram(bititle[,1],\"t\")");
		x = engine.eval("tf2<-freqlist(bigram(bititle[,1],\"t\"))");
		x = engine.eval("tf22<-tf2[which(tf2>1)]");
		x = engine.eval("cf1<-table(bicontent[,1])");	      
		x = engine.eval("cf11<-cf1[nchar(names(cf1))>1]");
		x = engine.eval("cf2<-freqlist(bigram(bicontent[,1],\"c\"))");
		x = engine.eval("cf22<-cf2[which(cf2>1)]");
		x = engine.eval("ff1<-data.frame(names(c(tf11,tf22)),as.numeric(c(tf11,tf22)))");
		x = engine.eval("ff2<-data.frame(names(c(cf11,cf22)),as.numeric(c(cf11,cf22)))");
		x = engine.eval("colnames(ff1)<-colnames(ff2)<-c(\"var\",\"count\")");
		x = engine.eval("toco<-c(as.character(ff1[,1]),as.character(ff2[,1]))");
		x = engine.eval("uu<-unique(toco)");
		x = engine.eval("uuni<-cbind(uu,7)");
		x = engine.eval("colnames(uuni)<-c(\"name\",7)");
		x = engine.eval("uuni<-merge(uuni,ff1,by.x=\"name\",by.y=\"var\",all.x=TRUE)");
		x = engine.eval("uuni<-merge(uuni,ff2,by.x=\"name\",by.y=\"var\",all.x=TRUE)");

		x = engine.eval("dtmat<-uuni[,-c(1:2)]");
		x = engine.eval("rownames(dtmat)<-uuni[,1]");
		x = engine.eval("colnames(dtmat)<-c(\"bititle\",\"bicontent\")");
		x = engine.eval("dtmat<-apply(dtmat,2,as.numeric)");
		x = engine.eval("dtmat[is.na(dtmat)]<-0");
		x = engine.eval("tdtmat<-t(dtmat)");
		x = engine.eval("colnames(tdtmat)<-uuni[,1]");
		x = engine.eval("dist(tdtmat, method=\"cosine\")");
		x = engine.eval("cosmat<-as.matrix(dist(tdtmat, method=\"cosine\"))[1,2]");
		x = engine.eval("var1<-1-cosmat");
		x = engine.eval("var5<-factor(var5, levels=c(\"Noun\",\"Verb\",\"Adjective\",\"Determiner\",\"Adverb\",\"Conjunction\",\"Exclamation\",\"Josa\",\"PreEomi\",\"Eomi\",\"Suffix\",\"Foreign\",\"Alpha\",\"Unknown\",\"KoreanParticle\",\"Modifier\"))");
		
		x = engine.eval("newsdata1<-data.frame(var1,var21,var22,var3,var4,var5,var6)");
		x = engine.eval("newsdata2<-newsdata1[colnames(realx)]");
	    x = engine.eval("test<-predict(model,newsdata2)");
		x = engine.eval("resultdata<-predict(model,newsdata2,type=\"prob\")[2]");
		System.out.println(x);		
		result = x.asDouble();

		engine.end();

		//br.close();
		
		return result;
	}


	public String[] getMorphs(String a)
	{
		//전달할 인자 값 정의 (문자열로 정의)

		System.out.println(a);

		String body = "url=" + Base64.getEncoder().encodeToString(a.getBytes());

		try
		{
			//url 설정
			URL _url = new URL("http://localhost:5000/morphs");
			HttpURLConnection conn = (HttpURLConnection)_url.openConnection();

			//리퀘스트 방식 정의 및 옵션 설정
			conn.setRequestMethod("POST");
			conn.setDoInput(true);
			conn.setDoOutput(true);

			//output 스트림
			OutputStream os = conn.getOutputStream();

			byte[] bytes = body.getBytes("UTF-8");

			os.write(bytes);
			os.flush();
			os.close();

			BufferedReader br = new BufferedReader( new InputStreamReader( conn.getInputStream(), "UTF-8" ), conn.getContentLength() );

			String result[] = new String[2];
			result[0] += br.readLine()+"\n";
			result[1] += br.readLine();

			br.close();
			System.out.println("FUNCTION MORPH IS END");
			return result;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}


	public NewsObj getFlask(String a) {
		// 전달할 인자 값 정의 (문자열로 정의)

		System.out.println(a);

		String body = "url=" + Base64.getEncoder().encodeToString(a.getBytes());

		try {
			// url 설정
			URL _url = new URL("http://localhost:5000/article");
			HttpURLConnection conn = (HttpURLConnection) _url.openConnection();

			// 리퀘스트 방식 정의 및 옵션 설정
			conn.setRequestMethod("POST");
			conn.setDoInput(true);
			conn.setDoOutput(true);

			// output 스트림
			OutputStream os = conn.getOutputStream();

			byte[] bytes = body.getBytes("UTF-8");

			os.write(bytes);
			os.flush();
			os.close();

			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"),
					conn.getContentLength());

			NewsObj nobj = new NewsObj(br);

			br.close();

			return nobj;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

}
