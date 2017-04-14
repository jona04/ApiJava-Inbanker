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
	
	private String idade;
	private String sexo;
	
	private String adicionado_em;
	
	@Embedded
	private Endereco endereco;
	
    private List<Transacao> transacoes_enviadas;
    
    private List<Transacao> transacoes_recebidas;
    
    @Embedded
	private List<CartaoPagamento> cartao_pagamento;

    @Embedded
    private List<NotificacaoContrato> notificacao_contrato;
 	
    
    
	public List<NotificacaoContrato> getNotificacaoContrato() {
		return notificacao_contrato;
	}
	public void setNotificacaoContrato(List<NotificacaoContrato> notificacao_contrato) {
		this.notificacao_contrato = notificacao_contrato;
	}
	public List<CartaoPagamento> getCartaoPagamento() {
		return cartao_pagamento;
	}
	public void setCartaoPagamento(List<CartaoPagamento> cartaoPagamento) {
		this.cartao_pagamento = cartaoPagamento;
	}
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
	public String getIdade() {
		return idade;
	}
	public void setIdade(String idade) {
		this.idade = idade;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public Endereco getEndereco() {
		return endereco;
	}
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	public String getAdicionado_em() {
		return adicionado_em;
	}
	public void setAdicionado_em(String adicionado_em) {
		this.adicionado_em = adicionado_em;
	}
	

}