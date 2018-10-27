package androidapp.hackaton.hackaton;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;

public class JsonCreator {

    String encodeRequest(byte[] image) throws UnsupportedEncodingException { //arr[N][3]
        JsonObject json = new JsonObject();
        json.addProperty("image", new String(image, "UTF-8"));

        return json.toString();
    }

    String[] getURLs(String json) {
        JsonParser jsonParser = new JsonParser();

        JsonObject jsonObject = jsonParser.parse(json).getAsJsonObject();

        JsonArray jsonArray = jsonObject.get("urls").getAsJsonArray();
        Iterator iter = jsonArray.iterator();
    }

    String getLabel(String json) {
        JsonParser jsonParser = new JsonParser();

        JsonObject jsonObject = jsonParser.parse(json).getAsJsonObject();

        return jsonObject.get("label").getAsString();
    }
}
