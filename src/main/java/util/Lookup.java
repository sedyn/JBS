package util;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Lookup {

    private Lookup () {

    }

    public static InetAddress[] lookup (String host) {
        try {
            return InetAddress.getAllByName(host);
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return null;
        }
    }
}
