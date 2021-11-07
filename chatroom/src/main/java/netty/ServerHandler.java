package netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.HashMap;
import java.util.Set;


/**
 * @ClassName ServerHandler
 * @Description 服务端的业务处理
 * @Author lzq
 * @Date 2019/6/1 21:27
 * @Version 1.0
 **/
public class ServerHandler extends SimpleChannelInboundHandler<String> {
    public static HashMap<ChannelHandlerContext,String> hashMap = new HashMap<>();
    
    /**
     * 通道就绪，就是有新客户连接了
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //channel.writeAndFlush(Unpooled.copiedBuffer("请输入昵称：",CharsetUtil.UTF_8));
        //已经指定了类型是String的，所以也可以这样写：
        channel.writeAndFlush("请输入昵称：");
    }

    /**
     * 读取数据
     * @param ctx
     * @param s
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String s) throws Exception {
        //第一次读取该客户端，s是昵称
        if(!hashMap.containsKey(ctx)) {
            hashMap.put(ctx,s);
            String str = "加入聊天室...";
            System.out.println("<"+s+">"+str);
            sendData(ctx,str,true);
        }else {  //后面的是消息，那么转发给其他客户端
            //首先要处理是私发消息还是广播
            if(s.contains("@")) {  //约定有@符号的就是私发消息
                sendData(ctx,s,false);
            }else {  //广播
                sendData(ctx,s,true);
            }
        }
    }

    /**
     * 广播消息
     * @param ctx
     * @param s
     * @param flag  标记是私发消息还是广播
     */
    private void sendData(ChannelHandlerContext ctx, String s, boolean flag) {
        String name = hashMap.get(ctx);  //获取发消息的客户端的名字
        Set<ChannelHandlerContext> channelHandlerContexts = hashMap.keySet();
        String str = "<"+name+">"+s;
        if(flag) {  //广播
            for (ChannelHandlerContext c : channelHandlerContexts) {
                if(c != ctx) {  //给其他所有客户端广播消息
                    Channel channel = c.channel();  //获取通道
                   // channel.writeAndFlush(Unpooled.copiedBuffer(str,CharsetUtil.UTF_8));
                    channel.writeAndFlush(str);
                }
            }
        }else {
            for (ChannelHandlerContext c : channelHandlerContexts) {
                if(c != ctx) {
                    String c_name = hashMap.get(c);
                    if(s.contains(c_name)) {
                        Channel channel = c.channel();  //获取通道
                       // channel.writeAndFlush(Unpooled.copiedBuffer(str,CharsetUtil.UTF_8));
                         channel.writeAndFlush(str);
                    }
                }
            }
        }
    }


    /**
     * 处理异常
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        String str = "退出了聊天室...";
        System.out.println("<"+hashMap.get(ctx)+">"+str);
        sendData(ctx,str,true);
        hashMap.remove(ctx);
        ctx.close();
    }
}
