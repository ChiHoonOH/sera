import java.util.Base64;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Scanner;

import java.net.URL;
import java.net.URLConnection;
import java.net.HttpURLConnection;

public class HttpClient1 {

	public static void main(String[] args)
	{
		//전달할 인자 값 정의 (문자열로 정의)
		String a = "https://entertain.naver.com/read?oid=108&aid=0002719961";
		System.out.println(a);
		
		String body = "url=" + Base64.getEncoder().encodeToString(a.getBytes());
		
		try
		{
			//url 설정
			URL url = new URL("http://localhost:5000/article");
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			
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

			String buf;
			
			while( ( buf = br.readLine() ) != null ) 
			{
				System.out.println( buf );
			}

			br.close();
			/*
			InputStream is = conn.getInputStream();
			Scanner scan = new Scanner(is);
			
			while(scan.hasNext())
			{
				String str = scan.nextLine();
				System.out.println(str);				
			}
			*/
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	
	}
}
