package br.com.inbanker.gcm;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import br.com.inbanker.entidades.Transacao;


public class GcmDao {

	private String ANDROID_NOTIFICATION_URL = "https://fcm.googleapis.com/fcm/send";
	//private String ANDROID_NOTIFICATION_KEY = "AIzaSyCanZDbePUY-kPT6Z2oZe1GCu2RMBeRHGk";
	private String ANDROID_NOTIFICATION_KEY = "AAAALkmLZbE:APA91bFXlaZBuUQu9Ire7wcBiGkdI46gG59iwdbhAi3kNlxMW0kMtuttTk037vyKQX8PlGoenvAtTFpVr9qU2ThegJqZhtYJFIxITUMaHV_HAgDIOy0Y3TUmH5z4ehkVuPpxdhhOZYyJ6KE4FnbVfKccvwIjMn13UA";
	private String CONTENT_TYPE = "application/json";
	
	
	public String sendAndroidNotification(String tipo,Transacao trans, String deviceToken,String message,String title) throws IOException {

		//OkHttpClient client = new OkHttpClient();
        //MediaType mediaType = MediaType.parse("application/json");
        
		HttpClient client = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(ANDROID_NOTIFICATION_URL);
		post.setHeader("Content-type", "application/json");
		post.setHeader("Authorization", "key="+ANDROID_NOTIFICATION_KEY);
		
		JSONObject obj = new JSONObject();
        JSONObject msgObject = new JSONObject();
        msgObject.put("msg", message);
        msgObject.put("title", title);
        msgObject.put("tipo", tipo);
        msgObject.put("icon","icon");
       // msgObject.put("icon", ANDROID_NOTIFICATION_ICON);
       // msgObject.put("color", ANDROID_NOTIFICATION_COLOR);
        
        ///JSONObject notObject = new JSONObject();
        //notObject.put("body", "new Symulti update !");
        //notObject.put("title", "new Symulti update !");
        
        HttpResponse response = null;
        
        try{
	        
        	//convertemos a transacao em json para mandar de volta na notificacao
        	ObjectMapper mapper = new ObjectMapper();
	        //Object to JSON in String
	        String jsonTrans = mapper.writeValueAsString(trans);
	        
	        msgObject.put("transacao", jsonTrans);
	        
	        obj.put("to", deviceToken);
	        obj.put("data",msgObject);
	        obj.put("priority","high");
	        
	        //obj.put("notification",notObject);
	        
	        //obj.put("click_action", ".NavigationDrawerActivity");
	        
	        post.setEntity(new StringEntity(obj.toString(), "UTF-8"));
			
		
			response = client.execute(post);
			
			System.out.println(response);
			System.out.println(obj);
			
	        /*RequestBody body = RequestBody.create(mediaType, obj.toString());
	        Request request = new Request.Builder().url(ANDROID_NOTIFICATION_URL).post(body)
	                .addHeader("content-type", CONTENT_TYPE)
	                .addHeader("authorization", "key="+ANDROID_NOTIFICATION_KEY).build();

	        response = client.newCall(request).execute();*/
	        
        } catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        
        return response.toString();
    }
	
	public String sendNotificationPost(){
		
		try {
			
			HttpClient client = HttpClientBuilder.create().build();
			HttpPost post = new HttpPost("https://fcm.googleapis.com/fcm/send");
			post.setHeader("Content-type", "application/json");
			post.setHeader("Authorization", "key=AIzaSyBSxxxxsXevRq0trDbA9mhnY_2jqMoeChA");
	
			JSONObject message = new JSONObject();
			message.put("to", "d-oeLCUVqRA:APA91bEt6H-d3LGl9oXBArnp-q2MdAxrtyG3nhCVit4GgS7Jg8bCRaTLWsC2BwzW48Q-R5IrjSqZvPQaTOVbuMlxiOhRPXQFQSEr3tKCOgeuIjYZShQQQA19TY2vifF-qXhGPsAUoDu6");
			message.put("priority", "high");
	
			JSONObject notification = new JSONObject();
			notification.put("title", "Java");
			notification.put("body", "Notificação do Java");
	
			message.put("notification", notification);
	
			post.setEntity(new StringEntity(message.toString(), "UTF-8"));
			HttpResponse response;
		
			response = client.execute(post);
			
			System.out.println(response);
			System.out.println(message);
			
			return response.toString();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	} 
	
	public String sendAndroidNotification2(String tipo,Transacao trans, String deviceToken,String message,String title) throws IOException {

		OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        JSONObject obj = new JSONObject();
        JSONObject msgObject = new JSONObject();
        msgObject.put("msg", message);
        msgObject.put("title", title);
        msgObject.put("tipo", tipo);
        msgObject.put("icon","icon");
       // msgObject.put("icon", ANDROID_NOTIFICATION_ICON);
       // msgObject.put("color", ANDROID_NOTIFICATION_COLOR);

        Response response = null;
        
        try{
	        
        	//convertemos a transacao em json para mandar de volta na notificacao
        	ObjectMapper mapper = new ObjectMapper();
	        //Object to JSON in String
	        String jsonTrans = mapper.writeValueAsString(trans);
	        
	        msgObject.put("transacao", jsonTrans);
	      
	        obj.put("to", deviceToken);
	        obj.put("data",msgObject);
	     

	        RequestBody body = RequestBody.create(mediaType, obj.toString());
	        Request request = new Request.Builder().url(ANDROID_NOTIFICATION_URL).post(body)
	                .addHeader("content-type", CONTENT_TYPE)
	                .addHeader("authorization", "key="+ANDROID_NOTIFICATION_KEY).build();

	        response = client.newCall(request).execute();
	        
        } catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        
        return response.body().string();
    }
	
}
