package netty;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @ClassName OnlyWrite
 * @Description 用来写数据的线程
 * @Author lzq
 * @Date 2019/6/1 23:52
 * @Version 1.0
 **/
public class OnlyWrite implements Runnable{
    private ChannelHandlerContext ctx = null;
    private boolean flag = true;
    private BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    public OnlyWrite(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public void run() {
        while (flag) {
            try {
                String s = bufferedReader.readLine();
                ctx.writeAndFlush(Unpooled.copiedBuffer(s,CharsetUtil.UTF_8));
            } catch (IOException e) {
                this.flag = false;
                System.out.println("获取数据错误...");
            }
        }
    }
}
