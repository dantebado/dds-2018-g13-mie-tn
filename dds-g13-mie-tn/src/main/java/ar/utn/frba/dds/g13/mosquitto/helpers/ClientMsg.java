package ar.edu.dds.helpers;

public class ClientMsg {

    private String text;
    private int num;

    public ClientMsg() { }

    public ClientMsg(String text, int num) {
        this.text = text;
        this.num = num;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
