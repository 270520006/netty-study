import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.ReferenceCounted;

/**
 * ChannelInboundHandlerAdapter 主动帮我们去使用下一个handler
 * 不用关注于下一个handler的执行
 */
public class EchoHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)  {
        String in=(String) msg;
        ctx.channel().writeAndFlush(in);
        ReferenceCountUtil.release(msg);
    }
}
