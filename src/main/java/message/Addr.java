package message;


import constant.CommandMap;
import constant.Constants;
import message.data.NetAddr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.ByteParser;

import java.io.File;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.stream.Stream;

public class Addr extends Message implements Receivable {

    private Logger logger = LoggerFactory.getLogger(Addr.class);
    private long count;
    private List<NetAddr> addr_list;

    public Addr(ByteBuffer buffer) {
        super(CommandMap.ADDR);
        deserialize(buffer);
    }

    public Addr(Header header) {
        super(CommandMap.ADDR);
        deserialize(header);
    }

    @Override
    public void deserialize (ByteBuffer buffer) {
        header = new Header(buffer);
        ByteParser parser = new ByteParser(header.getContent());
        count = parser.parseVarInt().value();
        addr_list = new ArrayList<>((int)count);
        for(int i = 0; i < count; i++) {
            NetAddr addr = parser.parseNetAddr();
            addr_list.add(addr);
        }
        logger.info(count + "  NetAddr have been processed");
    }

    private void deserialize (Header header) {
        this.header = header;
        ByteParser parser = new ByteParser(header.getContent());
        count = parser.parseVarInt().value();
        addr_list = new ArrayList<>((int)count);
        for(int i = 0; i < count; i++) {
            NetAddr addr = parser.parseNetAddr();
            addr_list.add(addr);
        }
        logger.info(count + " NetAddr have been processed");
    }

    @Override
    public String toString () {
        final StringBuilder sb = new StringBuilder("Addr ");
        sb.append("count: ").append(count).append("\n");
        int max = (int)(count > 20 ? 20 : count);
        for(int i = 0; i < max; i++) {
            sb.append(String.format("#%02d", i + 1)).append(" ").append(addr_list.get(i));
            if(i != max - 1)
                sb.append("\n");
        }
        return sb.toString();
    }

    public Stream<NetAddr> getStream() {
        return addr_list.stream();
    }

    public List<NetAddr> getList() {
        return addr_list;
    }

    public void toFile (Version recvVersion, String ip, int port, long timestamp) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("timestamp", timestamp);
        map.put("ip", ip);
        map.put("port", port);
        map.put("version", recvVersion);
        map.put("addr", addr_list);
        try {
            File file = new File("data" + File.separator + timestamp + "-" + new Date().getTime() + ".txt");
            if(!file.exists())
                logger.debug("file.createNewFile(): " + file.createNewFile());
            Constants.om.writerWithDefaultPrettyPrinter().writeValue(file, map);
        } catch (Exception e) {
            logger.error("ADDR - toFile", e);
        }
    }
}
