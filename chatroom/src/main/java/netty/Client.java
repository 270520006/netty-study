package netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;


/**
 * @ClassName Client
 * @Description 客户端
 * @Author lzq
 * @Date 2019/6/1 21:50
 * @Version 1.0
 **/
public class Client {
    private String host;  //地址
    private int port;  //端口号

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() {
        EventLoopGroup group = new NioEventLoopGroup();  //客户端的线程组
        try {
            Bootstrap bootstrap = new Bootstrap(); //启动助手，辅助
            bootstrap.group(group);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.handler(new ChannelInitializer<Channel>() {
                @Override
                protected void initChannel(Channel channel) throws Exception {
                    ChannelPipeline pipeline = channel.pipeline();
                    pipeline.addLast(new ClientHandler());
                }
            });

            System.out.println("客户端已就绪...");
            ChannelFuture future = bootstrap.connect(this.host, this.port).sync();
            System.out.println("客户端已启动...");
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            System.out.println("服务端断开连接...");
        }finally {
            group.shutdownGracefully();
            System.out.println("客户端关闭...");
        }
    }

    public static void main(String[] args) {
        new Client("127.0.0.1",1234).start();
    }
}
