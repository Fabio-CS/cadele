package br.com.winget.cadele.interfaces;

import java.util.ArrayList;

//import br.com.winget.cadele.model.Localizacao;
import br.com.winget.cadele.model.Usuario;

public interface InterLogin {
	public String autenticacao(String email, String senha);
	public void trocarTela(String telaAtual, String telaNova);
	public String cadastrar(String nome, String email, String senha);
	public String procurarAmigo(String email);
	public String adicionarAmigo(int idUser, int idAmigo);
	public String listarAmigos(int idUser);
	public String localizarAmigo(int idAmigo);
//	public void atualizarLocalizacao();
	//public Localizacao getLocalizacaoAmigo();
	public ArrayList<Usuario> getAmigos();
	public Usuario getUsuarioLogado();
}
