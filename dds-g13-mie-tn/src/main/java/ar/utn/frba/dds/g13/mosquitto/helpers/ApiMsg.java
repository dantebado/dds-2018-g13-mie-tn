package ar.edu.dds.helpers;

public class ApiMsg {

    private String shortMsg;
    private String description;

    public ApiMsg(String shortMsg, String description) {
        this.shortMsg = shortMsg;
        this.description = description;
    }

    public String getShortMsg() {
        return shortMsg;
    }

    public void setShortMsg(String shortMsg) {
        this.shortMsg = shortMsg;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
