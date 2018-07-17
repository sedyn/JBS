package util;

import io.netty.buffer.ByteBufUtil;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Utils {

    public static byte[] sha256 (byte[] pByte, int offset, int length) {
        MessageDigest digest = getSHA256();
        digest.update(pByte, offset, length);
        return digest.digest();
    }

    public static byte[] sha256 (byte[] pByte) {
        return sha256(pByte, 0, pByte.length);
    }

    public static String sha256 (String s) {
        MessageDigest digest = getSHA256();
        digest.update(s.getBytes());
        return ByteBufUtil.hexDump(digest.digest());
    }

    private static MessageDigest getSHA256() {
        try {
            return MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e){
            throw new RuntimeException(e);
        }
    }

    public static byte[] sha256Twice (byte[] pBytes, int offset, int length) {
        return sha256(sha256(pBytes, offset, length));
    }

    public static byte[] sha256Twice (byte[] pBytes) {
        return sha256(sha256(pBytes));
    }

    public static String[] splitByLength(String pString, int length) {
        ArrayList<String> strings = new ArrayList<String>(pString.length() / length);
        int index = 0;
        while (index < pString.length()) {
            strings.add(pString.substring(index, Math.min(index + length, pString.length())));
            index += length;
        }

        String[] tokens = new String[strings.size()];
        return strings.toArray(tokens);
    }
}
