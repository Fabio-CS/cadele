package br.com.winget.cadele;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import br.com.winget.cadele.control.RestClient;
import br.com.winget.cadele.control.RestClient.RequestMethod;
import br.com.winget.cadele.fragments.AdicionarFragment;
import br.com.winget.cadele.fragments.CadastrarFragment;
import br.com.winget.cadele.fragments.LocalizarFragment;
import br.com.winget.cadele.fragments.LoginFragment;
import br.com.winget.cadele.fragments.MapaFragment;
import br.com.winget.cadele.fragments.MenuFragment;
import br.com.winget.cadele.interfaces.InterLogin;
import br.com.winget.cadele.model.Localizacao;
import br.com.winget.cadele.model.Usuario;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.ViewGroup;

public class MainActivity extends FragmentActivity implements InterLogin, GooglePlayServicesClient.ConnectionCallbacks,
GooglePlayServicesClient.OnConnectionFailedListener, LocationListener {
	
	private ArrayList<Usuario> amigos = new ArrayList<Usuario>();
	private ArrayList<Usuario> addAmigos = new ArrayList<Usuario>();
	private Usuario usuario = new Usuario();
	private Localizacao localizacaoAmigo = new Localizacao();
	private Localizacao localizacaoUser = new Localizacao();
	private static final String END_WEBSERVICE = "http://192.168.1.7:8080/WebserviceCadele/webresources/";
	
	private LocationRequest mLocationRequest;
	private LocationClient mLocationClient;
	
	//private LocationClient locationLister = new LocationClient(this, this, this);
	// Milliseconds per second
    private static final int MILLISECONDS_PER_SECOND = 1000;
    // Update frequency in seconds
    public static final int UPDATE_INTERVAL_IN_SECONDS = 60;
    // Update frequency in milliseconds
    private static final long UPDATE_INTERVAL = MILLISECONDS_PER_SECOND * UPDATE_INTERVAL_IN_SECONDS;

    
    
	
	@Override
	public ArrayList<Usuario> getAmigos(){
		return amigos;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		 mLocationRequest = LocationRequest.create();
	     mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
	     mLocationRequest.setInterval(UPDATE_INTERVAL);
	     mLocationClient = new LocationClient(this, this, this);
	     mLocationClient.connect();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public String autenticacao(String email, String senha) {
		Gson json = new Gson();
		String mensagem = "Erro ao realizar login, verifique o e-mail e a senha";
		RestClient client = new RestClient(END_WEBSERVICE+"user/login/");
		client.AddParam("email", email);
		client.AddParam("senha", senha);
		client.AddHeader("Content-Type", "application/x-www-form-urlencoded");
		try {
		    client.Execute(RequestMethod.POST);
		} catch (Exception e) {
		    e.printStackTrace();
		}
		String response = client.getResponse();
		usuario = json.fromJson(response, Usuario.class);
		if(usuario != null){
			mensagem = "1";
		}
		return mensagem;
	}

	@Override
	public void trocarTela(String telaAtual, String telaNova) {
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		Fragment telaHide = null;
		ViewGroup layout = (ViewGroup) findViewById(R.id.fragment_container);
		if (layout != null){
			if(telaAtual.equals("login"))
				telaHide = getSupportFragmentManager().findFragmentById(R.id.fg_login);
			else if(telaAtual.equals("cadastrar"))
				telaHide = getSupportFragmentManager().findFragmentById(R.id.fg_cadastrar);
			else if(telaAtual.equals("adicionar"))
				telaHide = getSupportFragmentManager().findFragmentById(R.id.fg_adicionar);
			else if(telaAtual.equals("menu"))
				telaHide = getSupportFragmentManager().findFragmentById(R.id.fg_menu);
			else if(telaAtual.equals("localizar"))
				telaHide = getSupportFragmentManager().findFragmentById(R.id.fg_lista);
			else if(telaAtual.equals("mapa"))
				telaHide = getSupportFragmentManager().findFragmentById(R.id.fg_mapa);
			if (telaHide != null)
				transaction.hide(telaHide);
		}
		
		if (telaNova.equals("cadastrar")) {
			CadastrarFragment novoFragment = new CadastrarFragment();
			transaction.replace(R.id.fragment_container, novoFragment).addToBackStack("cadastrar");
			transaction.commit();
		}else if (telaNova.equals("login")) {
			LoginFragment novoFragment = new LoginFragment();
			transaction.replace(R.id.fragment_container, novoFragment).addToBackStack("login");
			transaction.commit();
		}else if (telaNova.equals("adicionar")) {
			AdicionarFragment novoFragment = new AdicionarFragment();
			transaction.replace(R.id.fragment_container, novoFragment).addToBackStack("adicionar");
			transaction.commit();
		}else if (telaNova.equals("menu")) {
			MenuFragment novoFragment = new MenuFragment();
			transaction.replace(R.id.fragment_container, novoFragment).addToBackStack("menu");
			transaction.commit();
		}else if (telaNova.equals("localizar")) {
			LocalizarFragment novoFragment = new LocalizarFragment();
			transaction.replace(R.id.fragment_container, novoFragment).addToBackStack("localizar");
			transaction.commit();
		}else if (telaNova.equals("mapa")) {
			MapaFragment novoFragment = new MapaFragment();
			transaction.replace(R.id.fragment_container, novoFragment).addToBackStack("mapa");
			transaction.commit();
		}
		
	}

	@Override
	public String cadastrar(String nome, String email, String senha) {
		Gson json = new Gson();
		String mensagem = "Erro ao cadastrar usuário";
		RestClient client = new RestClient(END_WEBSERVICE+"user/");
		client.AddParam("nome", nome);
		client.AddParam("email", email);
		client.AddParam("senha", senha);
		client.AddHeader("Content-Type", "application/x-www-form-urlencoded");
		try {
		    client.Execute(RequestMethod.POST);
		} catch (Exception e) {
		    e.printStackTrace();
		}
		String response = client.getResponse();
		usuario = json.fromJson(response, Usuario.class);
		if(usuario != null){
			mensagem = "Usuário Cadastrado com sucesso!";
		}
		return mensagem;
	}

	@Override
	public String procurarAmigo(String email) {
		Gson json = new Gson();
		String mensagem = "Usuários Encontrados";
		RestClient client = new RestClient(END_WEBSERVICE+"user/friends/search/"+email);
		try {
		    client.Execute(RequestMethod.GET);
		} catch (Exception e) {
		    e.printStackTrace();
		}
		String response = client.getResponse();
		Type collectionType = new TypeToken<ArrayList<Usuario>>(){}.getType();
		ArrayList<Usuario> lista = json.fromJson(response, collectionType);
		addAmigos.addAll(lista);
		if(response == null){
			mensagem = null;
		}
		return mensagem;
	}

	@Override
	public String adicionarAmigo(int idUser, int idAmigo) {
		RestClient client = new RestClient(END_WEBSERVICE+"user/friends/"+idUser+"/"+idAmigo);
		try {
		    client.Execute(RequestMethod.GET);
		} catch (Exception e) {
		    e.printStackTrace();
		}
		String response = client.getResponse();
		return response;
	}

	@Override
	public String localizarAmigo(int idAmigo) {
		Gson json = new Gson();
		String mensagem = "Localizado";
		RestClient client = new RestClient(END_WEBSERVICE+"location/"+idAmigo);
		try {
		    client.Execute(RequestMethod.GET);
		} catch (Exception e) {
		    e.printStackTrace();
		}
		String response = client.getResponse();
		localizacaoAmigo = json.fromJson(response, Localizacao.class);
		if(localizacaoAmigo.getId() == 0)
			mensagem = "Erro";
		return mensagem;
	}

	@Override
	public Usuario getUsuarioLogado() {
		return usuario;
	}

	@Override
	public String listarAmigos(int idUser) {
		Gson json = new Gson();
		String mensagem = "";
		RestClient client = new RestClient(END_WEBSERVICE+"user/friends/list/"+idUser);
		try {
		    client.Execute(RequestMethod.GET);
		} catch (Exception e) {
		    e.printStackTrace();
		}
		String response = client.getResponse();
		amigos.removeAll(Collections.singleton(null));
		Type collectionType = new TypeToken<ArrayList<Usuario>>(){}.getType();
		ArrayList<Usuario> lista = json.fromJson(response, collectionType);
		amigos.addAll(lista);
		if(lista != null){
			mensagem = "Listagem com sucesso!";
		}
		return mensagem;
	}
	

	@Override
	public Localizacao getLocalizacaoAmigo() {
		return localizacaoAmigo;
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		System.out.print("Errou!");
	}

	@Override
	public void onConnected(Bundle arg0) {
		if (mLocationClient != null && mLocationClient.isConnected()) {
			mLocationClient.requestLocationUpdates(mLocationRequest, this);
	    }
		
	}

	@Override
	public void onDisconnected() {
		mLocationClient.disconnect();
	}

	@Override
	public void onLocationChanged(Location location) {
		this.atualizarLocalizacao();
	}
	
	@Override
	public void atualizarLocalizacao() {
		Location localNow = mLocationClient.getLastLocation();
		Localizacao localizacao = new Localizacao();
		localizacao.setLatitude(localNow.getLatitude());
		localizacao.setLongitude(localNow.getLongitude());
		localizacao.setAltitude(localNow.getAltitude());
		localizacao.setIdUser(usuario.getId());
		if(!(localizacaoUser.equals(localizacao))){
			final Gson json = new Gson();
			final RestClient client = new RestClient(END_WEBSERVICE+"location/update");
			client.AddParam("id_usuario", usuario.getId()+"");
			client.AddParam("latitude", localNow.getLatitude()+"");
			client.AddParam("longitude", localNow.getLongitude()+"");
			client.AddParam("altitude", localNow.getAltitude()+"");
			client.AddHeader("Content-Type", "application/x-www-form-urlencoded");
			new Thread(new Runnable(){
	            public void run() {
					try {
					    client.Execute(RequestMethod.POST);
					} catch (Exception e) {
					    e.printStackTrace();
					}
					String response = client.getResponse();
					localizacaoUser = json.fromJson(response, Localizacao.class);
	            }
			}).start();
		}
	}

	@Override
	public ArrayList<Usuario> getAddAmigos() {
		return addAmigos;
	}
}
