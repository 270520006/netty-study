import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.Random;

public class IProtocalHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, final Object msg) throws Exception {
        int sleep = 500 * new Random().nextInt(5);
        System.out.println("sleep:" + sleep);
        Thread.sleep(sleep);

        final ByteBuf buf = (ByteBuf) msg;
        char c1 = (char) buf.readByte();
        char c2 = (char) buf.readByte();

        if (c1 != 'J' || c2 != 'W') {
            ctx.fireExceptionCaught(new Exception("magic error"));
            return ;
        }

        buf.readShort();//skip length

        String outputStr = buf.toString(CharsetUtil.UTF_8);
        System.out.println(outputStr);

        ctx.channel().writeAndFlush(outputStr+"\n");

    }
}
