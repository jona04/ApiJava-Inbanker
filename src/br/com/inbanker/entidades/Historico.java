package br.com.inbanker.entidades;

import org.mongodb.morphia.annotations.Embedded;

@Embedded 
public class Historico {
	
	private String status_transacao;
	private String data;
	
	
	public String getStatus_transacao() {
		return status_transacao;
	}
	public void setStatus_transacao(String status_transacao) {
		this.status_transacao = status_transacao;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	
	
	
}
