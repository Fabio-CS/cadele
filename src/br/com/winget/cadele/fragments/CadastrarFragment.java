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
import android.widget.EditText;
import android.widget.TextView;

public class CadastrarFragment extends Fragment {
	
	private InterLogin mCallback;
	private Button buttonCadastrar;
	private String email;
	private String senha;
	private String nome;
	private EditText parser;
	private TextView tvStatus;
	private String status;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.cadastrar, container, false);
		return view;
	}
	
	@Override
	public void onStart() {
		super.onStart();
		buttonCadastrar = (Button) getView().findViewById(R.id.bt_cadastrar);
		buttonCadastrar.setOnClickListener(new ButtonHandler());
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
			if (v.getId() == R.id.bt_cadastrar){
				parser = (EditText) getView().findViewById(R.id.et_nome);
				nome = parser.getText().toString();
				parser = (EditText) getView().findViewById(R.id.et_email);
				email = parser.getText().toString();
				parser = (EditText) getView().findViewById(R.id.et_senha);
				senha = parser.getText().toString();
				status = mCallback.cadastrar(nome, email, senha);
				tvStatus = (TextView) getView().findViewById(R.id.tv_adicionado);
				tvStatus.setText(status);
				//mCallback.trocarTela("menu");
			}
		}
	}
}
