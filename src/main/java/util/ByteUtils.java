package util;

public class ByteUtils {

    public static String bytesToHexString (byte[] pBytes, int offset, int length, boolean toUpper) {
        final StringBuilder sb = new StringBuilder();
        for(int i = offset; i < offset + length; i++) {
            int normal = (pBytes[i] & 0xff);
            if(normal < 0x10)
                sb.append("0");
            sb.append(toUpper ? Integer.toHexString(normal).toUpperCase() : Integer.toHexString(normal));
        }
        return sb.toString();
    }

    public static String bytesToHexString (byte[] pBytes, int offset, int length) {
        return bytesToHexString(pBytes, offset, length, false);
    }

    public static String bytesToHexString (byte[] pBytes, boolean toUpper) {
        return bytesToHexString(pBytes, 0, pBytes.length, toUpper);
    }

    public static String bytesToHexString (byte[] pBytes) {
        return bytesToHexString(pBytes, false);
    }

    public static String bytesToCharString (byte[] pBytes) {
        final StringBuilder sb = new StringBuilder();
        for(byte b : pBytes)
            sb.append((char)(b & 0xff));
        return sb.toString();
    }

    /**
     *
     * @param pBytes Bytes which want to reverse.
     * @param offset Offset of reversing.
     * @param length Length of reversing.
     * */
    public static byte[] reverseBytes (byte[] pBytes, int offset, int length) {
        byte[] reverse = new byte[length];
        for(int i = 0; i < length; i++)
            reverse[i] = pBytes[length + offset - i - 1];
        return reverse;
    }

    public static byte[] reverseBytes (byte[] pBytes) {
        return reverseBytes(pBytes, 0, pBytes.length);
    }

    public static byte[] subBytes (byte[] pBytes, int offset, int length) {
        if(length == 0) return new byte[]{};
        byte[] sub = new byte[length];
        System.arraycopy(pBytes, offset, sub, 0, length);
        return sub;
    }

    public static byte[] stringToBytes (String pString) {
        char[] cString = pString.toCharArray();
        byte[] bytes = new byte[cString.length];
        for(int i = 0; i < cString.length; i++)
            bytes[i] = (byte)cString[i];
        return bytes;
    }

    public static byte[] hexStringToBytes (String pString){
        String[] hex = Utils.splitByLength(pString, 2);
        byte[] bytes = new byte[hex.length];
        for(int i = 0; i < bytes.length; i++)
            bytes[i] = (byte) Short.parseShort(hex[i], 16);
        return bytes;
    }
}
