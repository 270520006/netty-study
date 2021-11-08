

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;

public class PrintInboundHandler implements ChannelInboundHandler {
    private final String id ;

    public PrintInboundHandler(String id) {
        this.id = id;
    }

    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerAdded " + id);

    }

    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerRemoved "+ id);

    }

    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelRegistered "+ id);
        ctx.fireChannelRegistered();

    }

    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelUnregistered "+ id);
        ctx.fireChannelUnregistered();

    }

    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelActive "+ id);
        ctx.fireChannelActive();

    }

    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelInactive "+ id);
        ctx.fireChannelInactive();
    }

    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("channelRead "+ id);
        ctx.fireChannelRead(msg);
        //ctx.channel().pipeline().fireChannelRead(msg);
    }

    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelReadComplete "+ id);
        ctx.fireChannelReadComplete();
    }

    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        System.out.println("userEventTriggered "+ id);

    }

    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelWritabilityChanged "+ id);

    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("exceptionCaught "+ id);

    }

}
