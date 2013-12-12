package br.com.winget.cadele.fragments;

import br.com.winget.cadele.R;
import br.com.winget.cadele.interfaces.InterLogin;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MenuFragment extends Fragment{
	private InterLogin mCallback;
	private Button buttonAdicionar;
	private Button buttonLocalizar;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.menu, container, true);
		return view;
	}
	
	@Override
	public void onStart() {
		super.onStart();
		
		buttonAdicionar = (Button) getView().findViewById(R.id.bt_adicionar);
		buttonAdicionar.setOnClickListener(new ButtonHandler());
		
		buttonLocalizar = (Button) getView().findViewById(R.id.bt_localizar);
		buttonLocalizar.setOnClickListener(new ButtonHandler());
		
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mCallback = (InterLogin) activity;
		} 
		catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + " must implement InterLogin");
		}
	}
	
	public class ButtonHandler implements OnClickListener {
		public void onClick(View v) {
			if (v.getId() == R.id.bt_adicionar){
					mCallback.trocarTela("menu","adicionar");
				}else{
					mCallback.trocarTela("menu","localizar");
				}
		}
			
	}
	
}
