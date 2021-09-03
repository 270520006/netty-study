import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NIOServer {
    public static void start(int port) throws IOException {
        //1、创建一个信道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //2、设置是否阻塞并设置端口号，这里要用NIO肯定是非阻塞的
        serverSocketChannel.configureBlocking(false);
        InetSocketAddress address = new InetSocketAddress(port);
        //3、同BIN过程，绑定套接字地址,这里可以绑定多个，只要在后面加上.bin即可
        serverSocketChannel.bind(address);
        //4、创建selector并绑定事件
        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while(true){ //这里如果只请求一次不会出错
            //5、进行轮询，查看是否有注册过channel的状态得到了满足
            //但是这块底层会有一些bug，因为非阻塞，所以while会空转
            selector.select();
            //6、从selector中得到集合，但也有可能Socket状态都没改变，集合为空
            Set<SelectionKey> readyKeys = selector.selectedKeys();
            Iterator<SelectionKey> it = readyKeys.iterator();
            //7、进入事件处理三步走
            while (it.hasNext()){ //进入事件处理三步走
                SelectionKey key = it.next();
                if (key.isAcceptable()){
                    //(1)、从信道中获取连接
                    ServerSocketChannel server = (ServerSocketChannel) key.channel();
                    //(2)、同BIO过程，对其进行accept
                    SocketChannel socket = server.accept();
                    System.out.println("Accept !");
                    //(3)、设置连接非阻塞，并且转换连接的状态
                    socket.configureBlocking(false);
                    socket.register(selector,SelectionKey.OP_READ);//将其从accept转换成read
                    System.out.println("经历了一次状态转换过程");
                }
                if (key.isReadable()) {
                    //(1)、从信道中获取连接
                    SocketChannel socket = (SocketChannel) key.channel();
                    //(2)创建字节流，接受传入的流
                    final ByteBuffer buffer =ByteBuffer.allocate(64);
                    final  int bytesRead =socket.read(buffer);//读取流
                    if (bytesRead>0){
                        Buffer flip = buffer.flip();//翻转缓冲区，理解成刷新缓存
                        int ret =socket.write(buffer);
                        if (ret<=0){
                            socket.register(selector,SelectionKey.OP_WRITE);
                        }
                        buffer.clear();
                    } else  if (bytesRead<0){
                        key.cancel();
                        socket.close();
                        System.out.println("Client close");
                    }
                }
                it.remove();
            }
        }
    }
    public static void main(String[] args) throws IOException {
        start(2020);
    }
}