package me.z5882852.requestpermission.utils.json;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.HashMap;
import java.util.Map;

public class Json {

    public static Map<String, Object> jsonToMap(JsonElement jsonElement) {
        Map<String, Object> map = new HashMap<>();
        if (jsonElement.isJsonObject()) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                String key = entry.getKey();
                JsonElement value = entry.getValue();
                if (value.isJsonPrimitive()) {
                    if (value.getAsJsonPrimitive().isNumber()) {
                        map.put(key, value.getAsNumber());
                    } else if (value.getAsJsonPrimitive().isString()) {
                        map.put(key, value.getAsString());
                    } else if (value.getAsJsonPrimitive().isBoolean()) {
                        map.put(key, value.getAsBoolean());
                    } else if (value.getAsJsonPrimitive().isJsonNull()) {
                        map.put(key, null);
                    }
                } else if (value.isJsonObject() || value.isJsonArray()) {
                    map.put(key, jsonToMap(value));
                }
            }
        }
        return map;
    }

    public static JsonElement stringToJson(String string) {
        JsonParser jsonParser = new JsonParser();
        return jsonParser.parse(string);
    }

    public static String mapToJsonString(Map<String, Object> map) {
        Gson gson = new Gson();
        return gson.toJson(map);
    }

    public static Map<String, Object> jsonStringToMap(String json) {
        JsonElement jsonElement = stringToJson(json);
        return jsonToMap(jsonElement);
    }
}
