package br.com.inbanker.entidades;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity(value = "recibos") 
public class Recibo {
	
	@Id 
	private ObjectId id;
	
	private String id_trans;
    private String cpf_usu1;
    private String cpf_usu2;
    private String valor;
    private String vencimento;
    private String data_pedido;
    private String data_confirma_quitacao;
    private String nome_usu2;
    private String nome_usu1;
    
	public String getId_trans() {
		return id_trans;
	}
	public void setId_trans(String id_trans) {
		this.id_trans = id_trans;
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
	
	public String getData_confirma_quitacao() {
		return data_confirma_quitacao;
	}
	public void setData_confirma_quitacao(String data_confirma_quitacao) {
		this.data_confirma_quitacao = data_confirma_quitacao;
	}
	public String getNome_usu2() {
		return nome_usu2;
	}
	public void setNome_usu2(String nome_usu2) {
		this.nome_usu2 = nome_usu2;
	}
	public String getNome_usu1() {
		return nome_usu1;
	}
	public void setNome_usu1(String nome_usu1) {
		this.nome_usu1 = nome_usu1;
	}

	
    
    
}
