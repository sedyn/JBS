package connect;

import constant.Constants;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import message.Header;
import message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class MessageDecoder extends ByteToMessageDecoder{

    private static Logger logger = LoggerFactory.getLogger(MessageDecoder.class);

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> list) throws Exception {
        if(in.readableBytes() < 24) {
            logger.debug("Got trash: " + ByteBufUtil.hexDump(in));
            return;
        }

        while(in.isReadable()) {
            int start = in.getInt(in.readerIndex());
            if(start == Constants.START_STRING) {
                Header header = new Header(in);
                if(header.isValid()) {
                    list.add(new Message(header));
                } else {
                    return;
                }
            } else {
                logger.debug("Start: " + start);
                logger.debug("Payload: " + ByteBufUtil.hexDump(in));
                return;
            }
        }
    }
}
