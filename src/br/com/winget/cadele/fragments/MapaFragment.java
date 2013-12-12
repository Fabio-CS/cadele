package br.com.winget.cadele.fragments;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import br.com.winget.cadele.R;
import br.com.winget.cadele.interfaces.InterLogin;
import br.com.winget.cadele.model.Localizacao;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

@SuppressLint("NewApi")
public class MapaFragment extends MapFragment {
	private InterLogin mCallback;
	private Localizacao localizacaoAmigo;
	private GoogleMap map;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.mapa, container, false);
		return view;
	}
	
	@SuppressLint("NewApi")
	@Override
	public void onStart() {
		super.onStart();
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.fg_mapa)).getMap();
		map.clear();
		//localizacaoAmigo = mCallback.getLocalizacaoAmigo();
		LatLng local = new LatLng(localizacaoAmigo.getLatitude(),localizacaoAmigo.getLongitude());
		map.addMarker(new MarkerOptions().position(local).title(localizacaoAmigo.getData()));
		map.moveCamera(CameraUpdateFactory.newLatLng(local));
	    map.animateCamera(CameraUpdateFactory.zoomTo(15));
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
