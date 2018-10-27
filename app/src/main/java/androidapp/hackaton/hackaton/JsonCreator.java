package androidapp.hackaton.hackaton;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;

public class JsonCreator {

    String encodeRequest(byte[] image) throws UnsupportedEncodingException { //arr[N][3]
        JsonObject json = new JsonObject();
        json.addProperty("image", new String(image, "UTF-8"));

        return json.toString();
    }

    List<String> getURLs(String json) {
        JsonParser jsonParser = new JsonParser();

        JsonObject jsonObject = jsonParser.parse(json).getAsJsonObject();

        Gson gson = new Gson();
        return gson.fromJson("urls", new TypeToken<List<String>>(){}.getType());
    }

    String getLabel(String json) {
        JsonParser jsonParser = new JsonParser();

        JsonObject jsonObject = jsonParser.parse(json).getAsJsonObject();

        return jsonObject.get("label").getAsString();
    }
}
