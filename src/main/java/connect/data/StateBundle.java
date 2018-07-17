package connect.data;

import message.Ping;
import message.Version;
import message.data.IPv6;

public class StateBundle {

    private final String ip;
    private final int port;
    private Ping ping;
    private Version version;
    private final long connectionTry;
    private boolean timeout;
    private boolean success;
    private Throwable exception;

    public StateBundle (final String ip, final int port, final long connection_try) {
        this.ip = ip;
        this.port = port;
        this.connectionTry = connection_try;
        timeout = false;
        success = false;
    }

    public StateBundle (final IPv6 ip, final  int port, final long connectionTry) {
        this(ip.toString(), port, connectionTry);
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setException(Throwable exception) {
        this.exception = exception;
    }

    public void setTimeout(boolean timeout) {
        this.timeout = timeout;
    }

    public void setPing (Ping ping) {
        this.ping = ping;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public Ping getPing() {
        return ping;
    }

    public Version getVersion() {
        return version;
    }

    public long getConnectionTry() {
        return connectionTry;
    }

    public boolean isTimeout() {
        return timeout;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getException() {
        if(exception != null)
            return exception.getMessage();
        else
            return null;
    }
}
