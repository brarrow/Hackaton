package androidapp.hackaton.hackaton;

import android.content.Intent;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;


public class PostPhotoTask extends AsyncTask<File, Integer, String> {
    private final ServerCommunication communication;
    private final MainActivity mainActivity;

    public PostPhotoTask(ServerCommunication communication, MainActivity mainActivity) {
        this.communication = communication;
        this.mainActivity = mainActivity;
    }

    @Override
    protected String doInBackground(File... files) {
        try {
            return communication.uploadUserPhoto(files[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        try {
            Intent intent = new Intent(mainActivity, ResponseActivity.class);
            final JSONObject jsonObject = new JSONObject(s);
            String label = jsonObject.getString("label");
            JSONArray jsonArray = jsonObject.getJSONArray("urls");

            ArrayList<String> urls = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                urls.add(jsonArray.getString(i));
            }

            intent.putExtra("label", label);

            intent.putStringArrayListExtra("urls", urls);


            mainActivity.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
