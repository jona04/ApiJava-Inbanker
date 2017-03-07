package br.com.inbanker.entidades;

import org.mongodb.morphia.annotations.Embedded;

@Embedded 
public class CartaoPagamento {
	private String token_cartao;
    private String numero_cartao;
    
	public String getToken_cartao() {
		return token_cartao;
	}
	public void setToken_cartao(String token_cartao) {
		this.token_cartao = token_cartao;
	}
	public String getNumero_cartao() {
		return numero_cartao;
	}
	public void setNumero_cartao(String numero_cartao) {
		this.numero_cartao = numero_cartao;
	}
    
    
}
