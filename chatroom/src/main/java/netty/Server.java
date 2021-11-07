package netty;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @ClassName Server
 * @Description 服务端
 * @Author lzq
 * @Date 2019/6/1 21:33
 * @Version 1.0
 **/
public class Server {
    private int port;

    public Server(int port) {
        this.port = port;
    }

    public void start() {
        //创建两个线程组
        EventLoopGroup boss = new NioEventLoopGroup();  //用来监听并连接客户端
        EventLoopGroup worker = new NioEventLoopGroup(); //用来监听通道并处理数据
        ServerBootstrap serverBootstrap = new ServerBootstrap(); //启动助手，辅助
        try {
            serverBootstrap.group(boss,worker);
            serverBootstrap.channel(NioServerSocketChannel.class);
            serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE,true); //设置常连接
            serverBootstrap.childHandler(new ChannelInitializer<Channel>() {
                @Override
                protected void initChannel(Channel channel) throws Exception {
                    ChannelPipeline pipeline = channel.pipeline();
                    pipeline.addLast("dec",new StringDecoder());  //解码器
                    pipeline.addLast("enc",new StringEncoder());   //编码器
                    pipeline.addLast(new ServerHandler()); //自定义业务处理

                }
            });

            System.out.println("服务端已就绪...");
            ChannelFuture future = serverBootstrap.bind(this.port).sync();
            System.out.println("服务端已启动...");
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            System.out.println("客户端断开连接...");
//            e.printStackTrace();
        }finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
            System.out.println("服务端关闭...");
        }
    }

    public static void main(String[] args) {
        new Server(1234).start();
    }
}
