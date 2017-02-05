package test.http;
import java.io.*;
import java.net.*;
import java.security.Security.*;
import javax.net.ssl.HttpsURLConnection;
 
public class TestHttp {
       public static void main(String[] args){
		String cuki=new String();
try { 
//System.setProperty("java.protocol.handler.pkgs", "com.sun.net.ssl.internal.www.protocol");
//java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
	System.setProperty("java.net.debug", "all");
//URL url = new URL("https://www.google.com/");
	URL url = new URL("https://www.flipkart.com/account/loginWithoutOtp");
 HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
 
connection.setDoInput(true); 
connection.setDoOutput(true);
String cookieHeader = connection.getHeaderField("set-cookie"); 
if(cookieHeader != null) { 
int index = cookieHeader.indexOf(";"); 
if(index >= 0) 
cuki = cookieHeader.substring(0, index) + new FlipCon1().getSession() ; 
connection.setRequestProperty("Cookie", cuki);

}

connection.setRequestMethod("POST"); 
//connection.setFollowRedirects(true); 

String query = "contact_id=" + URLEncoder.encode("gorur.raghu@gmail.com"); 
query += "&"; 
query += "password=" + URLEncoder.encode("maghuasdfasd");
query += "&"; 
query += "__FK=" + new FlipCon1().getCSRF(); 


//connection.setRequestProperty("Accept-Language","it"); 
//connection.setRequestProperty("Accept", "application/cfm, image/gif, image/x-xbitmap, image/jpeg, image/pjpeg, image/png, //*/*"); 
//connection.setRequestProperty("Accept-Encoding","gzip"); 

connection.setRequestProperty("Content-length",String.valueOf (query.length())); 
connection.setRequestProperty("Content-Type","application/x-www- form-urlencoded"); 
connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows 98; DigExt)"); 

// open up the output stream of the connection 
DataOutputStream output = new DataOutputStream( connection.getOutputStream() ); 

// write out the data 
int queryLength = query.length(); 
output.writeBytes( query ); 
//output.close();

System.out.println("Resp Code:"+connection.getResponseCode()); 
System.out.println("Resp Message:"+ connection.getResponseMessage()); 

// get ready to read the response from the cgi script 
DataInputStream input = new DataInputStream( connection.getInputStream() ); 

// read in each character until end-of-stream is detected 
for( int c = input.read(); c != -1; c = input.read() ) 
	System.out.print( (char)c ); 
input.close(); 
} 
catch(Exception e) 
{ 
System.out.println( "Something bad just happened." ); 
System.out.println( e ); 
e.printStackTrace(); 
} 
}
}
 

