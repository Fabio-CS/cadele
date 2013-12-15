package br.com.winget.cadele.fragments;

import java.util.ArrayList;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import br.com.winget.cadele.R;
import br.com.winget.cadele.interfaces.InterLogin;
import br.com.winget.cadele.model.Localizacao;
import br.com.winget.cadele.model.Usuario;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MapaFragment extends Fragment {
	private InterLogin mCallback;
	private Localizacao localizacaoAmigo;
	private Usuario amigo;
	private ArrayList<Usuario> amigos;
	private GoogleMap map;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.mapa, container, false);
		return view;
	}
	
	@Override
	public void onStart() {
		super.onStart();
		map = ((SupportMapFragment) getFragmentManager().findFragmentById(R.id.mapa)).getMap();
		map.clear();
		localizacaoAmigo = mCallback.getLocalizacaoAmigo();
		amigos = mCallback.getAmigos();
		for(Usuario friend : amigos){
			if(friend.getId() == localizacaoAmigo.getIdUser()){
				amigo = friend;
			}
		}		
		LatLng local = new LatLng(localizacaoAmigo.getLatitude(),localizacaoAmigo.getLongitude());
		map.addMarker(new MarkerOptions().position(local).title("Localização de "+amigo.getNome()).snippet(localizacaoAmigo.getData()));
		map.moveCamera(CameraUpdateFactory.newLatLng(local));
	    map.animateCamera(CameraUpdateFactory.zoomTo(17));
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
	
	
}
