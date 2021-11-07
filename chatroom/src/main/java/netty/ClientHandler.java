package netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName ClientHandler
 * @Description 客户端业务处理类
 * @Author lzq
 * @Date 2019/6/1 21:53
 * @Version 1.0
 **/
public class ClientHandler extends ChannelInboundHandlerAdapter {

    //一个线程池
    private static ExecutorService threadPool = Executors.newCachedThreadPool();


    /**
     * 通道就绪
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        threadPool.execute(new OnlyWrite(ctx));
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf)msg;
        System.out.println(byteBuf.toString(CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("聊天室关闭...");
        ctx.close();
    }
}
