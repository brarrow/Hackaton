package androidapp.hackaton.hackaton;

import android.util.Log;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ServerCommunication {
    private final OkHttpClient client = new OkHttpClient();


    public void uploadUserPhoto(File image) {
        final RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", "logo-square.png",
                        RequestBody.create(MediaType.parse("image/png"), image))
                .build();

        Request request = new Request.Builder()
                .url("http://djangoimghandlerenv.y39mhtmw34.us-west-2.elasticbeanstalk.com/mock")
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("okhttp-res", e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i("okhttp-res", response.body().string());
            }
        });
    }

}
