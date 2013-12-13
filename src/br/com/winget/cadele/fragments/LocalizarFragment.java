package br.com.winget.cadele.fragments;

import java.util.ArrayList;
import br.com.winget.cadele.interfaces.InterLogin;
import br.com.winget.cadele.model.Usuario;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class LocalizarFragment extends ListFragment {
	private ArrayList<Usuario> list = new ArrayList<Usuario>();
	private ArrayAdapter<Usuario> adapter;
	private InterLogin mCallback;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = super.onCreateView(inflater, container, savedInstanceState);
		return view;
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
		new Thread(new Runnable(){
            public void run() {
				mCallback.listarAmigos(mCallback.getUsuarioLogado().getId());
				list.addAll(mCallback.getAmigos());
            }
        }).start();
		adapter = new ArrayAdapter<Usuario>(getActivity(), android.R.layout.simple_list_item_multiple_choice, list);
		setListAdapter(adapter);
	}
	
	public void addData(ArrayList<Usuario> users){
		list.clear();
		addItens(users);
	}
	
	private void addItens(ArrayList<Usuario> users) {
		for (Usuario user : users) {
				list.add(user);
		}
		adapter.notifyDataSetChanged();
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		final Usuario amigo = (Usuario) l.getItemAtPosition(position);
		new Thread(new Runnable(){
            public void run() {
				mCallback.localizarAmigo(amigo.getId());
				mCallback.trocarTela("localizar","mapa");
            }
        }).start();
	}
}
