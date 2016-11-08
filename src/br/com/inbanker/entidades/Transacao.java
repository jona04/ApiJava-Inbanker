package br.com.inbanker.entidades;

import org.mongodb.morphia.annotations.Embedded;

@Embedded 
public class Transacao{

	//status da transacao
    public static final int AGUARDANDO_RESPOSTA = 0;
    public static final int PEDIDO_ACEITO = 1;
    public static final int PEDIDO_RECUSADO = 2;
    public static final int CONFIRMADO_RECEBIMENTO = 3;
    public static final int QUITACAO_SOLICITADA = 4;
    public static final int RESP_QUITACAO_SOLICITADA_RECUSADA = 5;
    public static final int RESP_QUITACAO_SOLICITADA_CONFIRMADA = 6;
    
    private String id_trans;
    private String cpf_usu1;
    private String cpf_usu2;
    private String valor;
    private String vencimento;
    private String data_pedido;
    private String nome_usu2;
    private String url_img_usu2;
    private String nome_usu1;
    private String url_img_usu1;
    private String status_transacao;
    private String data_recusada;
    private String data_pagamento;
    
	public String getNome_usu1() {
		return nome_usu1;
	}
	public void setNome_usu1(String nome_usu1) {
		this.nome_usu1 = nome_usu1;
	}
	public String getUrl_img_usu1() {
		return url_img_usu1;
	}
	public void setUrl_img_usu1(String url_img_usu1) {
		this.url_img_usu1 = url_img_usu1;
	}
	
	public String getCpf_usu1() {
		return cpf_usu1;
	}
	public void setCpf_usu1(String cpf_usu1) {
		this.cpf_usu1 = cpf_usu1;
	}
	public String getCpf_usu2() {
		return cpf_usu2;
	}
	public void setCpf_usu2(String cpf_usu2) {
		this.cpf_usu2 = cpf_usu2;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public String getVencimento() {
		return vencimento;
	}
	public void setVencimento(String vencimento) {
		this.vencimento = vencimento;
	}
	public String getData_pedido() {
		return data_pedido;
	}
	public void setData_pedido(String data_pedido) {
		this.data_pedido = data_pedido;
	}
	public String getNome_usu2() {
		return nome_usu2;
	}
	public void setNome_usu2(String nome_usu2) {
		this.nome_usu2 = nome_usu2;
	}
	public String getUrl_img_usu2() {
		return url_img_usu2;
	}
	public void setUrl_img_usu2(String url_img_usu2) {
		this.url_img_usu2 = url_img_usu2;
	}
	public String getId_trans() {
		return id_trans;
	}
	public void setId_trans(String id_trans) {
		this.id_trans = id_trans;
	}
	public String getStatus_transacao() {
		return status_transacao;
	}
	public void setStatus_transacao(String status_transacao) {
		this.status_transacao = status_transacao;
	}
	public String getData_recusada() {
		return data_recusada;
	}
	public void setData_recusada(String data_recusada) {
		this.data_recusada = data_recusada;
	}
	public String getData_pagamento() {
		return data_pagamento;
	}
	public void setData_pagamento(String data_pagamento) {
		this.data_pagamento = data_pagamento;
	}
    
    
	
}
