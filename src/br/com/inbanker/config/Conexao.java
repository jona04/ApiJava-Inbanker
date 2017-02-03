package br.com.inbanker.config;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;


public class Conexao {
   
    private static String IP_CONEXAO_MONGODB = "45.55.217.160:32302";
	//private static String IP_CONEXAO_MONGODB = "127.0.0.1:27017";
    private static String NOME_BANCO_MONGODB = "inbanker";

	public static Datastore abrirConexao() {
        MongoClient mongo = new MongoClient(IP_CONEXAO_MONGODB);
        Morphia morphia = new Morphia();
        Datastore datastore = morphia.createDatastore(mongo, NOME_BANCO_MONGODB);
        return datastore;
        
    }
    
    public static void fecharConexao(Datastore datastore) {
        datastore.getMongo().close();
    }
   
  }