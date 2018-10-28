package androidapp.hackaton.hackaton;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;


public class PostPhotoTask extends AsyncTask<File, Integer, String> {
    private final ServerCommunication communication;
    private final MainActivity mainActivity;
    private volatile File processingFile;

    public PostPhotoTask(ServerCommunication communication, MainActivity mainActivity) {
        this.communication = communication;
        this.mainActivity = mainActivity;
    }

    @Override
    protected String doInBackground(File... files) {
        try {
            processingFile = files[0];
            return communication.uploadUserPhoto(processingFile);
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

            intent.putExtra("label", label);
            intent.putExtra("originFile", processingFile);

            JSONArray jsonArrayUrls = jsonObject.getJSONArray("urls");
            ArrayList<String> urls = new ArrayList<>();
            for (int i = 0; i < jsonArrayUrls.length(); i++) {
                urls.add(jsonArrayUrls.getString(i));
            }

            intent.putStringArrayListExtra("urls", urls);

            JSONArray jsonArrayPrices = jsonObject.getJSONArray("prices");
            ArrayList<String> prices = new ArrayList<>();
            for (int i = 0; i < jsonArrayPrices.length(); ++i) {
                prices.add(jsonArrayPrices.getString(i));
            }

            intent.putStringArrayListExtra("prices", prices);

            JSONArray jsonArrayTitles = jsonObject.getJSONArray("titles");
            ArrayList<String> titles = new ArrayList<>();
            for (int i = 0; i < jsonArrayTitles.length(); ++i) {
                titles.add(jsonArrayTitles.getString(i));
            }

            intent.putStringArrayListExtra("titles", titles);

            mainActivity.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
