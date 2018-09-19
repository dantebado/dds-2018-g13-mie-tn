package ar.utn.frba.dds.g13.json;

public abstract class BeanToJson <T>{

    public abstract T getObj();

    @Override
    public String toString() {
        return JsonUtils.toJson(getObj());
    }
}