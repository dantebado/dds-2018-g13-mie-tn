package ar.utn.frba.dds.g13.json;

import java.lang.reflect.Type;
import java.util.Date;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class JsonUtils {

    public static String toJson(Object object) {
        final GsonBuilder gson = JsonUtils.getBuilder();
        gson.excludeFieldsWithoutExposeAnnotation();
        return gson.create().toJson(object);
    }
    
    public static GsonBuilder getBuilder() {
    	GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                return new Date(json.getAsJsonPrimitive().getAsLong());
            }
        });
        builder.setDateFormat("yyyy-MM-dd");
    	return builder;
    }


}