package br.com.winget.cadele;

import java.net.URI;
import java.net.URISyntaxException;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void getUserById() throws URISyntaxException{
		URI url = new URI("http://10.0.2.2:8080/WebserviceCadele/webresources/user/?id=1");
	}

}
