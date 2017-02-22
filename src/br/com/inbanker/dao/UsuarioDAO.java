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
	
	public Usuario findUsuarioCpf(String cpf) {
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
	
	public String verificaUsuarioCadastro(String cpf,String email) {
        Datastore datastore = Conexao.abrirConexao();
        Query<Usuario> query = datastore
        .createQuery(Usuario.class)
        .field("cpf").equal(cpf);
        List<Usuario> listUsuario = query.asList();
        
        //Conexao.fecharConexao(datastore);

        if (listUsuario != null && !listUsuario.isEmpty())
           return "cpf";
        else{
        	
        	Query<Usuario> query2 = datastore
            .createQuery(Usuario.class)
            .field("email").equal(email);
            List<Usuario> listUsuario2 = query2.asList();
            
            if (listUsuario2 != null && !listUsuario2.isEmpty()){
            	return "email";
            }else{
            	return "vazio";
            }
        }    
	}
	
	public String verificaEmailCadastro(String email) {
        Datastore datastore = Conexao.abrirConexao();
        Query<Usuario> query = datastore
        .createQuery(Usuario.class)
        .field("email").equal(email);
        List<Usuario> listUsuario = query.asList();
        
        //Conexao.fecharConexao(datastore);

        if (listUsuario != null && !listUsuario.isEmpty())
           return "email";
        else
        	return "vazio";
	}
	
	public String verificaCPFCadastro(String cpf) {
        Datastore datastore = Conexao.abrirConexao();
        Query<Usuario> query = datastore
        .createQuery(Usuario.class)
        .field("cpf").equal(cpf);
        List<Usuario> listUsuario = query.asList();
        
        //Conexao.fecharConexao(datastore);

        if (listUsuario != null && !listUsuario.isEmpty())
           return "cpf";
        else
        	return "vazio";
	}
	
	public String verificaIdFace(String id_face) {
        Datastore datastore = Conexao.abrirConexao();
        Query<Usuario> query = datastore
        .createQuery(Usuario.class)
        .field("id_face").equal(id_face);
        List<Usuario> listUsuario = query.asList();
        
        //Conexao.fecharConexao(datastore);

        if (listUsuario != null && !listUsuario.isEmpty())
           return "id_face";
        else
        	return "vazio";
	}
	
	public Usuario findUserCpfTransEnv(String cpf) {
        Datastore datastore = Conexao.abrirConexao();
        
        Usuario query = datastore
        .find(Usuario.class)
        .field("cpf").equal(cpf)
        .retrievedFields(true, "transacoes_enviadas").get();
        
        
        Conexao.fecharConexao(datastore);

        if (query != null)
           return query;
        else
          return null;
        }
	public Usuario findUserCpfTransRec(String cpf) {
        Datastore datastore = Conexao.abrirConexao();
        
        Usuario query = datastore
        .find(Usuario.class)
        .field("cpf").equal(cpf)
        .retrievedFields(true, "transacoes_recebidas").get();
        
        
        Conexao.fecharConexao(datastore);

        if (query != null)
           return query;
        else
          return null;
        }
	public Usuario findUserCpfTransHistorico(String cpf) {
        Datastore datastore = Conexao.abrirConexao();
        
        Usuario query = datastore
        .find(Usuario.class)
        .field("cpf").equal(cpf)
        .retrievedFields(true, "transacoes_recebidas","transacoes_enviadas").get();
        
        
        Conexao.fecharConexao(datastore);

        if (query != null)
           return query;
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
	
	public void editarSenhaByCPF(Usuario usu,String cpf) {
		Datastore datastore = Conexao.abrirConexao();
		Query<Usuario> query = datastore
		        .createQuery(Usuario.class)
		        .field("cpf").equal(cpf);
		UpdateOperations ops = datastore
			    .createUpdateOperations(Usuario.class)
			    .set("senha", usu.getSenha());
		datastore.update(query, ops);
		Conexao.fecharConexao(datastore);
	}
	
	public void editarUsuarioByCPF(Usuario usu,String cpf) {
		Datastore datastore = Conexao.abrirConexao();
		Query<Usuario> query = datastore
		        .createQuery(Usuario.class)
		        .field("cpf").equal(cpf);
		UpdateOperations ops = datastore
			    .createUpdateOperations(Usuario.class)
			    .set("id_face", usu.getId_face())
			    .set("nome", usu.getNome())
				.set("url_face", usu.getUrl_face())
				.set("email", usu.getEmail());
		datastore.update(query, ops);
		Conexao.fecharConexao(datastore);
	}
	
	public void editarUsuarioByFace(Usuario usu,String id_face) {
		Datastore datastore = Conexao.abrirConexao();
		Query<Usuario> query = datastore
		        .createQuery(Usuario.class)
		        .field("id_face").equal(id_face);
		UpdateOperations ops = datastore
			    .createUpdateOperations(Usuario.class)
			    .set("cpf", usu.getCpf())
			    .set("email", usu.getEmail())
			    .set("nome", usu.getNome())
			    .set("senha", usu.getSenha());
		datastore.update(query, ops);
		Conexao.fecharConexao(datastore);
	}
	
	public void deletaUsuFace(Usuario usu,String id_face) {
		Datastore datastore = Conexao.abrirConexao();
		Query<Usuario> query = datastore
		        .createQuery(Usuario.class)
		        .field("id_face").equal(id_face);
		datastore.delete(query);
		Conexao.fecharConexao(datastore);
	}
	
	public void updateTokenGcm(Usuario usu,String cpf) {
		Datastore datastore = Conexao.abrirConexao();
		Query<Usuario> query = datastore
		        .createQuery(Usuario.class)
		        .field("cpf").equal(cpf);
		UpdateOperations ops = datastore
			    .createUpdateOperations(Usuario.class)
			    .set("device_id", usu.getDevice_id())
			    .set("token_gcm", usu.getToken_gcm());
		datastore.update(query, ops);
		Conexao.fecharConexao(datastore);
	}
	
	public void addTransacao(Transacao trans,String cpf_user1,String cpf_user2) {
		Datastore datastore = Conexao.abrirConexao();
		Query<Usuario> query_user1 = datastore.createQuery(Usuario.class)
				.field("cpf").equal(cpf_user1);
		Query<Usuario> query_user2 = datastore.createQuery(Usuario.class)
				.field("cpf").equal(cpf_user2);
		
		UpdateOperations ops = datastore.createUpdateOperations(Usuario.class).disableValidation()
				.add("transacoes_enviadas", trans,false);
		datastore.update(query_user1, ops);
		
		UpdateOperations ops2 = datastore.createUpdateOperations(Usuario.class).disableValidation()
				.add("transacoes_recebidas", trans,false);
		datastore.update(query_user2, ops2);
		
		
		Conexao.fecharConexao(datastore);
	}
	
	public void editarTransacao(Transacao trans,String cpf_user1,String cpf_user2) {
		Datastore datastore = Conexao.abrirConexao();
		Query<Usuario> query_user1 = datastore.createQuery(Usuario.class)
				.filter("cpf",cpf_user1)
				.filter("transacoes_enviadas.id_trans",trans.getId_trans());
		Query<Usuario> query_user2 = datastore.createQuery(Usuario.class)
				.filter("cpf",cpf_user2)
				.filter("transacoes_recebidas.id_trans",trans.getId_trans());
		
		UpdateOperations ops = datastore.createUpdateOperations(Usuario.class)
				.set("transacoes_enviadas.$.status_transacao", trans.getStatus_transacao());
		datastore.update(query_user1, ops);
		
		UpdateOperations ops2 = datastore.createUpdateOperations(Usuario.class)
				.set("transacoes_recebidas.$.status_transacao", trans.getStatus_transacao());
		datastore.update(query_user2, ops2);
		
		
		Conexao.fecharConexao(datastore);
	}
	
	public void editarTransacaoResposta(Transacao trans,String cpf_user1,String cpf_user2) {
		Datastore datastore = Conexao.abrirConexao();
		Query<Usuario> query_user1 = datastore.createQuery(Usuario.class)
				.filter("cpf",cpf_user1)
				.filter("transacoes_enviadas.id_trans",trans.getId_trans());
		Query<Usuario> query_user2 = datastore.createQuery(Usuario.class)
				.filter("cpf",cpf_user2)
				.filter("transacoes_recebidas.id_trans",trans.getId_trans());
		
		UpdateOperations ops = datastore.createUpdateOperations(Usuario.class)
				.set("transacoes_enviadas.$.status_transacao", trans.getStatus_transacao())
				.set("transacoes_enviadas.$.data_recusada", trans.getData_recusada())
				.set("transacoes_enviadas.$.data_pagamento", trans.getData_pagamento());
		datastore.update(query_user1, ops);
		
		UpdateOperations ops2 = datastore.createUpdateOperations(Usuario.class)
				.set("transacoes_recebidas.$.status_transacao", trans.getStatus_transacao())
				.set("transacoes_recebidas.$.data_recusada", trans.getData_recusada())
				.set("transacoes_recebidas.$.data_pagamento", trans.getData_pagamento());
		datastore.update(query_user2, ops2);
		
		
		Conexao.fecharConexao(datastore);
	}
}