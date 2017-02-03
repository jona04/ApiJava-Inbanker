package br.com.inbanker.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;
import org.mongodb.morphia.annotations.Reference;

@Entity(value = "usuarios") 
public class Usuario implements Serializable {
   
	private String nome;
	private String email;
	private String cpf;
	private String nascimento;
	private String senha;
	private String id_face;
	private String url_face;
	private String device_id;
	private String token_gcm;
	
    private List<Transacao> transacoes_enviadas;
    
    private List<Transacao> transacoes_recebidas;
    
 	@Embedded
    private List<Transacao> transacoes;
   
 	public List<Transacao> getTransacoes_enviadas() {
		return transacoes_enviadas;
	}
	public void setTransacoes_enviadas(List<Transacao> transacoes_enviadas) {
		this.transacoes_enviadas = transacoes_enviadas;
	}
	
 	public List<Transacao> getTransacoes_recebidas() {
		return transacoes_recebidas;
	}
	public void setTransacoes_recebidas(List<Transacao> transacoes_recebidas) {
		this.transacoes_recebidas = transacoes_recebidas;
	}

	public List<Transacao> getTransacao() {
		return transacoes;
	}
	public void setTransacao(List<Transacao> transacoes) {
		this.transacoes = transacoes;
	}
	
	public String getId_face() {
		return id_face;
	}
	public void setId_face(String id_face) {
		this.id_face = id_face;
	
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getNascimento() {
		return nascimento;
	}
	public void setNascimento(String nascimento) {
		this.nascimento = nascimento;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getUrl_face() {
		return url_face;
	}
	public void setUrl_face(String url_face) {
		this.url_face = url_face;
	}
	public String getDevice_id() {
		return device_id;
	}
	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}
	public String getToken_gcm() {
		return token_gcm;
	}
	public void setToken_gcm(String token_gcm) {
		this.token_gcm = token_gcm;
	}
	

}