package br.com.inbanker.config;

import java.util.ArrayList;
import java.util.List;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;


public class Conexao {
   
    private static String IP_CONEXAO_MONGODB = "45.55.217.160:32302";
	//private static String IP_CONEXAO_MONGODB = "127.0.0.1:27017";
    private static String NOME_BANCO_MONGODB = "inbanker";

	public static Datastore abrirConexao() {
		
		ServerAddress addr = new ServerAddress("45.55.217.160",32303);
		List<MongoCredential> credentialsList = new ArrayList<MongoCredential>();
		MongoCredential credentia = MongoCredential.createCredential(
		    "appinbanker2017", "admin", "AppAdminInbanker2017#".toCharArray());
		credentialsList.add(credentia);
		
		
        MongoClient client = new MongoClient(addr,credentialsList);
        Morphia morphia = new Morphia();
        Datastore datastore = morphia.createDatastore(client, NOME_BANCO_MONGODB);
        return datastore;
        
    }
    
    public static void fecharConexao(Datastore datastore) {
        datastore.getMongo().close();
    }
   
  }