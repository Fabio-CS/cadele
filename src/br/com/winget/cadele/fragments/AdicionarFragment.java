 package br.com.winget.cadele.fragments;

import java.util.ArrayList;

import br.com.winget.cadele.R;
import br.com.winget.cadele.interfaces.InterLogin;
import br.com.winget.cadele.model.Usuario;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AdicionarFragment extends Fragment {
	private InterLogin mCallback;
	private Button buttonProcurar;
	private String email;
	private EditText parser;
	private TextView tvStatus;
	private String status;
	private ListProcurarFragment finders;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
			View view = inflater.inflate(R.layout.adicionar, container, false);
		return view;
	}
	
	@Override
	public void onStart() {
		super.onStart();
		buttonProcurar = (Button) getView().findViewById(R.id.bt_procurar);
		buttonProcurar.setOnClickListener(new ButtonHandler());
		tvStatus = (TextView) getView().findViewById(R.id.tv_adicionado);
		finders = (ListProcurarFragment) getFragmentManager().findFragmentById(R.id.fg_adLista);
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
		@Override
		public void onClick(View v) {
			ArrayList<Usuario> amigos = mCallback.getAddAmigos();
			amigos.clear();
			if (v.getId() == R.id.bt_procurar){
				new Thread(new Runnable(){
	                public void run() {
						parser = (EditText) getView().findViewById(R.id.et_emailSearch);
						email = parser.getText().toString();
						status = mCallback.procurarAmigo(email);
						Activity act = (Activity) mCallback;
						act.runOnUiThread(new Runnable() {
	                        @Override
	                        public void run() {
								if(status == null){
									tvStatus.setText("Usuário não encontrado!");
								}else{
									tvStatus.setText(mCallback.getAddAmigos().size()+" usuário(s) encontrado(s)");
									finders.addData(mCallback.getAddAmigos());
								}
	                        }
						});
					}
				}).start();
			}
		}
	}
}
