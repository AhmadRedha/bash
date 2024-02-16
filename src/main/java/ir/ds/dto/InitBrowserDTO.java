package ir.ds.dto;

public class InitBrowserDTO {

    String token;
    String url;
    String dest;
    String topic;
    String server;
    int port;

    public InitBrowserDTO(String token, String url, String dest, String topic, String server, int port) {
        this.token = token;
        this.url = url;
        this.dest = dest;
        this.topic = topic;
        this.server = server;
        this.port = port;
    }

    public String getToken() {
        return token;
    }

    public String getUrl() {
        return url;
    }

    public String getDest() {
        return dest;
    }

    public String getTopic() {
        return topic;
    }

    public String getServer() {
        return server;
    }

    public int getPort() {
        return port;
    }
}