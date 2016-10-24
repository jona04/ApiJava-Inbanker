package br.com.inbanker.dao;

import java.util.List;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.*;
import br.com.inbanker.config.Conexao;
import br.com.inbanker.entidades.Transacao;
import br.com.inbanker.entidades.Usuario;

public class UsuarioDAO{
   
	public void incluirUsuario(Usuario usu) {
		Datastore datastore = Conexao.abrirConexao();
		datastore.save(usu);
		Conexao.fecharConexao(datastore);
	}
	
	public List<Usuario> listUser() {
        Datastore datastore = Conexao.abrirConexao();
  
        Query<Usuario> query = datastore.find(Usuario.class);
        List<Usuario> listProjeto = query.asList();
  
        Conexao.fecharConexao(datastore);
        return listProjeto;
      }
	
	public Usuario findUserEmail(String email) {
        Datastore datastore = Conexao.abrirConexao();
        Query<Usuario> query = datastore
        .createQuery(Usuario.class)
        .field("email").equal(email);
        List<Usuario> listUsuario = query.asList();
        Conexao.fecharConexao(datastore);

        if (listUsuario != null && !listUsuario.isEmpty())
           return listUsuario.get(0);
        else
          return null;
        }
	
	public Usuario findUserCpf(String cpf) {
        Datastore datastore = Conexao.abrirConexao();
        Query<Usuario> query = datastore
        .createQuery(Usuario.class)
        .field("cpf").equal(cpf);
        List<Usuario> listUsuario = query.asList();
        Conexao.fecharConexao(datastore);

        if (listUsuario != null && !listUsuario.isEmpty())
           return listUsuario.get(0);
        else
          return null;
        }
	public Usuario findUserFace(String id_face) {
        Datastore datastore = Conexao.abrirConexao();
        Query<Usuario> query = datastore
        .createQuery(Usuario.class)
        .field("id_face").equal(id_face);
        List<Usuario> listUsuario = query.asList();
        Conexao.fecharConexao(datastore);

        if (listUsuario != null && !listUsuario.isEmpty())
           return listUsuario.get(0);
        else
          return null;
        }
	
	public void editarUsuario(Usuario usu,String cpf) {
		Datastore datastore = Conexao.abrirConexao();
		Query<Usuario> query = datastore
		        .createQuery(Usuario.class)
		        .field("cpf").equal(cpf);
		UpdateOperations ops = datastore
			    .createUpdateOperations(Usuario.class)
			    .set("id_face", usu.getId_face())
			    .set("nome_face", usu.getNome_face())
				.set("url_face", usu.getUrl_face());
		datastore.update(query, ops);
		Conexao.fecharConexao(datastore);
	}
	
	public void editarTransacao(Transacao trans,String cpf_user1) {
		Datastore datastore = Conexao.abrirConexao();
		Query<Usuario> query_user1 = datastore.createQuery(Usuario.class)
				.field("cpf").equal(cpf_user1);
		//Query<Usuario> query_user2 = datastore.createQuery(Usuario.class).field("cpf").equal(cpf_user2);
		
		UpdateOperations ops = datastore.createUpdateOperations(Usuario.class)
				.add("transacoes", trans,false);
		datastore.update(query_user1, ops);
		
		
		Conexao.fecharConexao(datastore);
	}
}