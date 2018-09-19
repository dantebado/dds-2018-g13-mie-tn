package ar.utn.frba.dds.g13.json;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.reflect.TypeToken;

import ar.utn.frba.dds.g13.user.Residence;;

public class ResidenceLoader {
	
	public static List<Residence> load(File file) throws IOException {
        final String json = new JsonFile(file.getAbsolutePath()).read();
        final Type listType = new TypeToken<ArrayList<Residence>>(){}.getType();
        return JsonUtils.getBuilder().create().fromJson(json, listType);
    }

}
