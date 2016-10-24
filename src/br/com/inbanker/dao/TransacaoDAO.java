package br.com.inbanker.dao;

import org.mongodb.morphia.Datastore;

import br.com.inbanker.config.Conexao;
import br.com.inbanker.entidades.Transacao;

public class TransacaoDAO {
	
	public void incluirUsuario(Transacao trans) {
		Datastore datastore = Conexao.abrirConexao();
		datastore.save(trans);
		Conexao.fecharConexao(datastore);
	}
}
