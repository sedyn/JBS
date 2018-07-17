package message.data;


import exception.InvalidIpAddressException;

import java.nio.ByteBuffer;

public class IPv6 {

    private String ip;
    /* v4Regex and v6Regex from "http://www.regexpal.com" */
    private static final String v4Regex = "^([0-9]{1,3}\\.){3}[0-9]{1,3}?$";
    private static final String v6Regex = "^s*((([0-9A-Fa-f]{1,4}:){7}([0-9A-Fa-f]{1,4}|:))|(([0-9A-Fa-f]{1,4}:){6}(:[0" +
            "-9A-Fa-f]{1,4}|((25[0-5]|2[0-4]d|1dd|[1-9]?d)(.(25[0-5]|2[0-4]d|1dd|[1-9]?d)){3})|:))|(([0-9A-Fa-f]" +
            "{1,4}:){5}(((:[0-9A-Fa-f]{1,4}){1,2})|:((25[0-5]|2[0-4]d|1dd|[1-9]?d)(.(25[0-5]|2[0-4]d|1dd|[1-9]?d" +
            ")){3})|:))|(([0-9A-Fa-f]{1,4}:){4}(((:[0-9A-Fa-f]{1,4}){1,3})|((:[0-9A-Fa-f]{1,4})?:((25[0-5]|2[0-4" +
            "]d|1dd|[1-9]?d)(.(25[0-5]|2[0-4]d|1dd|[1-9]?d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){3}(((:[0-9A-Fa-f]{1,4}" +
            "){1,4})|((:[0-9A-Fa-f]{1,4}){0,2}:((25[0-5]|2[0-4]d|1dd|[1-9]?d)(.(25[0-5]|2[0-4]d|1dd|[1-9]?d)){3})" +
            ")|:))|(([0-9A-Fa-f]{1,4}:){2}(((:[0-9A-Fa-f]{1,4}){1,5})|((:[0-9A-Fa-f]{1,4}){0,3}:((25[0-5]|2[0-4]d" +
            "|1dd|[1-9]?d)(.(25[0-5]|2[0-4]d|1dd|[1-9]?d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){1}(((:[0-9A-Fa-f]{1,4}){" +
            "1,6})|((:[0-9A-Fa-f]{1,4}){0,4}:((25[0-5]|2[0-4]d|1dd|[1-9]?d)(.(25[0-5]|2[0-4]d|1dd|[1-9]?d)){3}))|" +
            ":))|(:(((:[0-9A-Fa-f]{1,4}){1,7})|((:[0-9A-Fa-f]{1,4}){0,5}:((25[0-5]|2[0-4]d|1dd|[1-9]?d)(.(25[0-5]" +
            "|2[0-4]d|1dd|[1-9]?d)){3}))|:)))(%.+)?s*?$";
    private static final long[] pow = {16777216, 65536, 256, 1};

    public IPv6 (String ip){

        if(ip.matches(v4Regex)) {
            final StringBuilder sb = new StringBuilder(39);
            sb.append("0000:0000:0000:0000:0000:ffff:");
            String[] token = ip.split("\\.");
            Long ipAsNumber = 0L;
            for(int i = 0; i < 4; i++)
                ipAsNumber += Long.parseLong(token[i]) * pow[i];
            final StringBuilder ipAsString = new StringBuilder(Long.toHexString(ipAsNumber));
            int length = 8 - ipAsString.length();
            for(int i = 0; i < length; i++) ipAsString.append("0");
            sb.append(ipAsString.toString().substring(0, 4));
            sb.append(":");
            sb.append(ipAsString.toString().substring(4, 8));
            this.ip = sb.toString();
        } else if (ip.matches(v6Regex)) {
            this.ip = ip;
        }
    }

    public static String convert (String ip) {
        if(ip.matches(v4Regex)) {
            final StringBuilder sb = new StringBuilder(39);
            sb.append("0000:0000:0000:0000:0000:ffff:");
            String[] token = ip.split("\\.");
            Long ipAsNumber = 0L;
            for(int i = 0; i < 4; i++)
                ipAsNumber += Long.parseLong(token[i]) * pow[i];
            final StringBuilder ipAsString = new StringBuilder(Long.toHexString(ipAsNumber));
            int length = 8 - ipAsString.length();
            for(int i = 0; i < length; i++) ipAsString.append("0");
            sb.append(ipAsString.toString().substring(0, 4));
            sb.append(":");
            sb.append(ipAsString.toString().substring(4, 8));
            return sb.toString();
        } else if (ip.matches(v6Regex)) {
            if(ip.length() == 39)
                return ip;

            StringBuilder sb = new StringBuilder(39);
            for(String token : ip.split(":")) {
                for(int i = 0; i < 4 - token.length(); i++)
                    sb.append("0");
                sb.append(token);
                if(sb.length() != 39)
                    sb.append(":");
            }

            return sb.toString();
        }

        return ip;
    }

    public byte[] toByteArray() {
        ByteBuffer buffer = ByteBuffer.allocate(16);
        for(String token : ip.split(":"))
            buffer.putShort(token.equals("") ? 0 : (short) Integer.parseInt(token, 16));
        return buffer.array();
    }

    @Override
    public String toString() {
        return ip;
    }
}
