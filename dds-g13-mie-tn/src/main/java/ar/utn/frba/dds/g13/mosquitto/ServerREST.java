package ar.utn.frba.dds.g13.mosquitto;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ar.utn.frba.dds.g13.mosquitto.helpers.ApiMsg;
import ar.utn.frba.dds.g13.mosquitto.helpers.ClientMsg;
import ar.utn.frba.dds.g13.mosquitto.helpers.JsonTransformer;

import static spark.Spark.*;


public class ServerREST {

    public static void main(String[] args) {
        port(4567);
        List<String> msgs = new ArrayList<>();

        get("/", (req, res) -> msgs, new JsonTransformer());
        post("/", (req, res) -> {
            ClientMsg msg = null;
            try {
                ObjectMapper mapper = new ObjectMapper();
                msg = mapper.readValue(req.body(), ClientMsg.class);
            } catch (JsonParseException e) {
                res.status(400);
                return new ApiMsg("json bad formed", e.getLocalizedMessage());
            }
            msgs.add(msg.getText());
            res.status(201);
            return new ApiMsg("created", "new msg ! ");

        }, new JsonTransformer());


    }

}
