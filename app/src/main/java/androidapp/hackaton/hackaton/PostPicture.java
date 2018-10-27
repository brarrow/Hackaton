package androidapp.hackaton.hackaton;

import android.os.AsyncTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class PostPicture extends AsyncTask<Void, Void, Void> {
    private String resultString = null;
    private static String url = "http://djangoimghandlerenv.y39mhtmw34.us-west-2.elasticbeanstalk.com/"; //serverURL
    private static String picture;
    private static byte[] data;
    private static InputStream inputStream;


    @Override
    protected Void doInBackground(Void... voids) {
        try{
            URL url = new URL(this.url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            OutputStream outputStream = connection.getOutputStream();
            data = picture.getBytes("UTF-8");
            outputStream.write(data);
            data = null;
            connection.connect();
            int responseCode = connection.getResponseCode();

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            if(responseCode == 200){
                inputStream = connection.getInputStream();
                byte [] buffer = new byte[8192];
                int bytesRead;
                while((bytesRead = inputStream.read(buffer))!=-1){
                    byteArrayOutputStream.write(buffer, 0, bytesRead);
                }
                data = byteArrayOutputStream.toByteArray();
                resultString = new String(data, "UTF-8");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}