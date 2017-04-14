package br.com.inbanker.gcm;

import java.io.IOException;

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
