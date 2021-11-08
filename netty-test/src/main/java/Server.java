import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.DefaultEventExecutorGroup;

import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

public class Server {

    private static  void use(ChannelPipeline pipeline, Consumer<ChannelPipeline> strategy){
        strategy.accept(pipeline);
    }
    private static Consumer<ChannelPipeline> echo= p ->{
        p.addLast(
                new LineBasedFrameDecoder(80,false,false),
                new StringDecoder(),
                new EchoHandler(),
                new PipelinePrintHandler(),
                new StringEncoder(StandardCharsets.UTF_8)
        );
    };
    private  static Consumer<ChannelPipeline> print =p -> {
      p.addLast(new PrintInboundHandler("id1"));
    };

    private static  Consumer<ChannelPipeline> decode= p->{
      p.addLast(new LengthFieldBasedFrameDecoder(1024,2,2,-2,0))
        .addLast(new DefaultEventExecutorGroup(16),new IProtocalHandler())
        .addLast(new StringEncoder(CharsetUtil.UTF_8));
    };

    private static  void start(int port) throws InterruptedException {
        // bossGroups,是专门做accept功能用的
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //workerGroup,对应read、send等其他操作
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b=new ServerBootstrap();
            b.group(bossGroup,workerGroup);
            b.channel(NioServerSocketChannel.class);
            b.childHandler(new ChannelInitializer() {
                @Override
                protected void initChannel(Channel ch) throws Exception {
                    use(ch.pipeline(), echo);
                }
            });
            ChannelFuture f=b.bind(port).sync();
            f.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }


    public static void main(String[] args) throws InterruptedException {
        start(2020);
    }
}
