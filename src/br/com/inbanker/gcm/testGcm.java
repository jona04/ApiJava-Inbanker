package br.com.inbanker.gcm;

import java.io.IOException;

import org.json.JSONObject;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;


public class testGcm {

	private String ANDROID_NOTIFICATION_URL = "https://fcm.googleapis.com/fcm/send";
	private String ANDROID_NOTIFICATION_KEY = "AIzaSyCanZDbePUY-kPT6Z2oZe1GCu2RMBeRHGk";
	private String CONTENT_TYPE = "application/json";
	
	
	public String sendAndroidNotification(String deviceToken,String message,String title) throws IOException {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        JSONObject obj = new JSONObject();
        JSONObject msgObject = new JSONObject();
        msgObject.put("body", message);
        msgObject.put("title", title);
       // msgObject.put("icon", ANDROID_NOTIFICATION_ICON);
       // msgObject.put("color", ANDROID_NOTIFICATION_COLOR);

        obj.put("to", deviceToken);
        obj.put("notification",msgObject);

        RequestBody body = RequestBody.create(mediaType, obj.toString());
        Request request = new Request.Builder().url(ANDROID_NOTIFICATION_URL).post(body)
                .addHeader("content-type", CONTENT_TYPE)
                .addHeader("authorization", "key="+ANDROID_NOTIFICATION_KEY).build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }
	
}
