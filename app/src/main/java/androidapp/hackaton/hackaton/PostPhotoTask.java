package androidapp.hackaton.hackaton;

import android.content.Intent;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;


public class PostPhotoTask extends AsyncTask<File, Integer, Status> {
    private final ServerCommunication communication;
    private final MainActivity mainActivity;
    private final NoContent noContent;
    private volatile File processingFile;

    public PostPhotoTask(ServerCommunication communication, MainActivity mainActivity, NoContent noContent) {
        this.communication = communication;
        this.noContent = noContent;
        this.mainActivity = mainActivity;
    }

    @Override
    protected androidapp.hackaton.hackaton.Status doInBackground(File... files) {
        try {
            processingFile = files[0];
            final String resultJson = communication.uploadUserPhoto(processingFile);
            return new androidapp.hackaton.hackaton.Status(0, resultJson);
        } catch (NoContentException e) {
            e.printStackTrace();
            return new androidapp.hackaton.hackaton.Status(1, "");
        } catch (Exception e){
            return new androidapp.hackaton.hackaton.Status(1, "");
        }
    }

    @Override
    protected void onPostExecute(androidapp.hackaton.hackaton.Status s) {
        if(s.getStatus() == 0){
            try {
                Intent intent = new Intent(mainActivity, ResponseActivity.class);
                final JSONObject jsonObject = new JSONObject(s.getString());
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
        else if(s.getStatus() == 1 || s.getStatus() == 2){
            Intent intent = new Intent(mainActivity, NoContent.class);
            mainActivity.startActivity(intent);
        }
    }
}
