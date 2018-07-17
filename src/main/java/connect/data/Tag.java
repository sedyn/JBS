package connect.data;

public class Tag {

    private String from_ip;
    private long from_time;

    public Tag (String from_ip, long from_time) {
        this.from_ip = from_ip;
        this.from_time = from_time;
    }

    public String getFrom_ip() {
        return from_ip;
    }

    public long getFrom_time() {
        return from_time;
    }
}
