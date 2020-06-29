package miscelaneous;

import okhttp3.*;
import org.json.JSONObject;


import java.io.IOException;
import java.net.SocketTimeoutException;

public class Api {


    public static String getAuthorizationToken() throws IOException, InterruptedException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url(Props.getProperty("baseUrl") + "/api/v1/OAuth/Token?clientId=ht9ygs4wrskf7rwa61dsujjw9w")
                .method("POST", body)
                .build();


        Response response = null;
        boolean fail = false;
        do {
            try {
                response = client.newCall(request).execute();
            } catch (SocketTimeoutException e) {
                response = client.newCall(request).execute();
                fail = true;
            }
        } while (fail);

        String data = response.body().string();

        JSONObject obj = new JSONObject(data);
        String token = obj.get("access_token").toString();

        return token;
    }

    public static void featureControllerDisable(String feature) throws IOException, InterruptedException {
        String token = getAuthorizationToken();
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "\"" + feature + "\"");
        Request request = new Request.Builder()
                .url(Props.getProperty("baseUrl") + "/api/v1/Features/disable")
                .method("POST", body)
                .addHeader("Authorization", "Bearer " + token)
                .addHeader("Content-Type", "application/json")
                .build();

        try {
            client.newCall(request).execute();
        } catch (SocketTimeoutException e) {
            Thread.sleep(20000);
            client.newCall(request).execute();
        }
    }

    public static String encryptLogin(String login) throws IOException, InterruptedException {
        String token = getAuthorizationToken();
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url(Props.getProperty("baseUrl") + "/api/v1/Admin/EncryptLogin/?login=" + login)
                .method("GET", null)
                .addHeader("Authorization", "Bearer " + token)
                .build();
        Response response = client.newCall(request).execute();
        String[] s = response.body().string().split("\"");
        return s[1];
    }

    public static void inspectionTaskCreateNf(String login, String address) throws IOException, InterruptedException {
        String integratedUserToken = getIntegratedUserToken(login);
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "objectType=Nf&ao=CAO&district=Мещанский&address=" + address + "&taskAimCtId=107496&taskBasisCtId=108293&taskAimComments=Voluptas sequi magni atque nostrum rerum molestias ea.");
        Request request = new Request.Builder()
                .url(Props.getProperty("baseUrl") + "/api/v1/InspectionTasks/Create")
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("Authorization", "Bearer " + integratedUserToken)
                .build();
        client.newCall(request).execute();

    }

    private static String getIntegratedUserToken(String login) throws IOException, InterruptedException {
        String encryptedLogin = encryptLogin(login);
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url(Props.getProperty("baseUrl") + "/api/v1/OAuth/Token?clientId=" + encryptedLogin)
                .method("POST", body)
                .build();

        Response response = client.newCall(request).execute();
        String data = response.body().string();
        JSONObject obj = new JSONObject(data);
        String token = obj.get("access_token").toString();

        return token;
    }


}
