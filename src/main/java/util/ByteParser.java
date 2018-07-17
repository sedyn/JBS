package util;

import exception.InvalidIpAddressException;
import message.data.IPv6;
import message.data.Inventory;
import message.data.NetAddr;
import message.data.VarInt;

import java.math.BigInteger;
import java.nio.ByteBuffer;

public class ByteParser {

    private byte[] bytes;
    private int position;

    public ByteParser (byte[] bytes) {
        this.bytes = bytes;
        position = 0;
    }

    public ByteParser (ByteBuffer byteBuffer) {
        this.bytes = byteBuffer.array();
        position = 0;
    }

    public void forward(int x) {
        position += x;
    }

    public void back(int x){
        position -= x;
    }

    public int getPosition() {
        return position;
    }

    public int remain () {
        return bytes.length - position;
    }

    public byte[] parseRemain () {
        return ByteUtils.subBytes(bytes, position, bytes.length - position);
    }

    public int parseInt (boolean reverse) {
        byte[] sub = reverse ? ByteUtils.reverseBytes(bytes, position, 4) : ByteUtils.subBytes(bytes, position, 4);
        position += 4;
        return (int)Long.parseLong(ByteUtils.bytesToHexString(sub), 16);
    }

    public long parseLong (boolean reverse) {
        byte[] sub = reverse ? ByteUtils.reverseBytes(bytes, position, 8) : ByteUtils.subBytes(bytes, position, 8);
        position += 8;
        return new BigInteger(ByteUtils.bytesToHexString(sub), 16).longValue();
    }

    public VarInt parseVarInt () {
        VarInt varInt = new VarInt(bytes, position);
        position += varInt.length();
        return varInt;
    }

    public String parseHexString (int length, boolean reverse) {
        byte[] sub;
        if(reverse)
            sub = ByteUtils.reverseBytes(bytes, position, length);
        else
            sub = ByteUtils.subBytes(bytes, position, length);
        position += length;
        return ByteUtils.bytesToHexString(sub);
    }

    public String parseHexString (int length) {
        return parseHexString(length, false);
    }

    public String parseCharString (int length) {
        byte[] sub = ByteUtils.subBytes(bytes, position, length);
        position += length;
        return ByteUtils.bytesToCharString(sub);
    }

    public byte[] parseByte (int length) {
        byte[] sub = ByteUtils.subBytes(bytes, position, length);
        position += length;
        return sub;
    }

    public int parsePort () {
        byte[] sub = ByteUtils.subBytes(bytes, position, 2);
        position += 2;
        return (int)Long.parseLong(ByteUtils.bytesToHexString(sub) ,16);
    }

    public IPv6 parseIPv6 () {
        StringBuilder sb = new StringBuilder(39);
        byte[] sub = ByteUtils.subBytes(bytes, position, 16);
        position += 16;
        String[] address = Utils.splitByLength(ByteUtils.bytesToHexString(sub), 4);
        for(int i = 0; i < address.length; i++){
            sb.append(address[i]);
            if(i != address.length - 1)
                sb.append(":");
        }

        return new IPv6(sb.toString());
    }

    public NetAddr parseNetAddr () {
        int time = parseInt(true);
        long services = parseLong(true);
        IPv6 ip = parseIPv6();
        int port = parsePort();
        return new NetAddr(time, services, ip, port);
    }

    public Inventory parseInventory() {
        byte[] sub = ByteUtils.subBytes(bytes, position, 36);
        position += 36;
        return new Inventory(sub);
    }

    public byte parseChar () {
        position++;
        return (byte)(bytes[position - 1] & 0xff);
    }
}
