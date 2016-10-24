package br.com.inbanker.entidades;

import java.io.Serializable;
import java.util.Random;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Embedded 
public class Transacao{

	
	private String usu1;
	private String usu2;
	
	/*public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId value) {
		this.id = value;
	}*/
	  
	public String getUsu1() {
		return usu1;
	}
	public void setUsu1(String usu1) {
		this.usu1 = usu1;
	}
	public String getUsu2() {
		return usu2;
	}
	public void setUsu2(String usu2) {
		this.usu2 = usu2;
	}
	
	
}
