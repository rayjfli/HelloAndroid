package rayjf.helloandroid;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;

class ReadHttp extends AsyncTask<String, Void, String> {
	 private Exception exception;
	 String strinfo;

	@Override
	protected String doInBackground(String... uriAPI) {
		 
		HttpGet httpRequest = new HttpGet(uriAPI[0]); 
	    try 
	    { 
	      /*發出HTTP request*/
	      HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest); 
	      /*若狀態碼為200 ok*/
	      if(httpResponse.getStatusLine().getStatusCode() == 200)  
	      { 
	        /*取出回應字串*/
	        strinfo = EntityUtils.toString(httpResponse.getEntity());
	        //int a = 5/0;
	        /*刪除多餘字元*/
	        //strResult = eregi_replace("(\r\n|\r|\n|\n\r)","",strResult);
	        //return strinfo;
	    	
	      } 
	      else 
	      { 
	       // mTextView1.setText("Error Response: "+httpResponse.getStatusLine().toString()); 
	      } 
	    } 
	    catch (ClientProtocolException e) 
	    {  
	      //mTextView1.setText(e.getMessage().toString()); 
	      e.printStackTrace(); 
	    } 
	    catch (IOException e) 
	    {  
	      //mTextView1.setText(e.getMessage().toString()); 
	      e.printStackTrace(); 
	    } 
	    catch (Exception e) 
	    {  
	     // mTextView1.setText(e.getMessage().toString()); 
	    	strinfo = e.getMessage().toString();
	      e.printStackTrace();  
	    }  
		
		// TODO Auto-generated method stub
		return strinfo;
	}}
