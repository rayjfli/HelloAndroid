package rayjf.helloandroid;

//import org.apache.http.HttpResponse; 
//import org.apache.http.NameValuePair; 
//import org.apache.http.client.ClientProtocolException; 
//import org.apache.http.client.entity.UrlEncodedFormEntity; 
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.client.methods.HttpPost; 
//import org.apache.http.impl.client.DefaultHttpClient; 
//import org.apache.http.message.BasicNameValuePair; 
//import org.apache.http.protocol.HTTP; 
//import org.apache.http.util.EntityUtils; 
//import java.io.IOException;

import java.util.concurrent.ExecutionException;

import android.view.View; 
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Button;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.security.interfaces.RSAPrivateKey;  
import java.security.interfaces.RSAPublicKey;  

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import android.util.Base64;

public class MainActivity extends Activity {

	private TextView mTextView1, mTextView2;
	private Button mGetButton, keyButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
	         
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mTextView1 = (TextView) findViewById(R.id.textView2);
		mTextView2 = (TextView) findViewById(R.id.textView3);
		mGetButton = (Button) findViewById(R.id.button1);
		keyButton = (Button) findViewById(R.id.button02);
		String str = "你好!";
		mTextView1.setText(str);
		


	   mGetButton.setOnClickListener(new Button.OnClickListener() 
	    { 
	      @Override 
	      public void onClick(View v) 
	      { 
	        // TODO Auto-generated method stub 
	        /*宣告網址字串*/
	    	  String result = new String();
	    	  mTextView1.setText("yoyo1");
	    	  try {
				result = new ReadHttp().execute("http://partii.cc/webservices/music?artist=The+Beatles").get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	  mTextView1.setText(result);

	      }
	    });
	   
	   keyButton.setOnClickListener(new Button.OnClickListener()
	   {
		   @Override
		   public void onClick(View v)
		   {
			   RSAPublicKey pbk;
			   PrivateKey pvk;
			   Cipher c;
			   byte[] plainText = "This is Ray".getBytes();
			   try {
				KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
				keyPairGen.initialize(1024);
				KeyPair keyPair = keyPairGen.generateKeyPair();
				pbk = (RSAPublicKey) keyPair.getPublic();
				pvk = (RSAPrivateKey) keyPair.getPrivate();
				c = Cipher.getInstance("RSA/ECB/PKCS1Padding");
				//Encryption
				c.init(Cipher.ENCRYPT_MODE, pbk);
				byte[] enBytes = c.doFinal(plainText);
				//mTextView1.setText(enBytes.toString());
				String t =Base64.encodeToString(pbk.getModulus().toByteArray(), 0);
				String u = Base64.encodeToString(pbk.getPublicExponent().toByteArray(), 0);
				String ai = pbk.getModulus().toString();
				String bi = pbk.getPublicExponent().toString();
				
				//String pret = Base64.encodeToString(pbk.getModulus().toByteArray(), 0);
				

				//String t = pbk.toString();
				
				String xml = "<RSAKeyValue> <Modulus>" + t + "</Modulus> <Exponent>" + u + "</Exponent> </RSAKeyValue>";
				String enXml = URLEncoder.encode(xml, "UTF-8");

				String result = new String();
				
		    	  try {
						result = new ReadHttp().execute("http://partii.cc/webservices/publicKey.php?xmlkey=" + enXml).get();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		    	  byte[] remote = Base64.decode(result, 0);
		    	  
		    	  int a = remote.length;
		    	  
		    	  c.init(Cipher.DECRYPT_MODE, pvk);
		    	  byte[] deBytes = c.doFinal(remote);
				
		    	
				mTextView1.setText(new String(deBytes));
				
		    	//  mTextView1.setText(result);
				//Decryption
				
				
				//mTextView2.setText(new String(deBytes));
				
			   } catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchPaddingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalBlockSizeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BadPaddingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			   

		   }
	   }
	   );
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


	
}


