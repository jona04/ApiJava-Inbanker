package br.com.inbanker.entidades;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class NotificacaoContrato {

	private String id_trans;
	private String cpf_user;
	private String date;
	private String dias;
	private String titulo;
	private String mensagem;
	public String getId_trans() {
		return id_trans;
	}
	public void setId_trans(String id_trans) {
		this.id_trans = id_trans;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getCpf_user() {
		return cpf_user;
	}
	public void setCpf_user(String cpf_user) {
		this.cpf_user = cpf_user;
	}
	public String getDias() {
		return dias;
	}
	public void setDias(String dias) {
		this.dias = dias;
	}
	
	
	
	
}
