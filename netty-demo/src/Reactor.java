import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class Reactor {
    interface ChannelHandler {
        public void onRead(SocketChannel channel) throws Exception;
        public void onAccept();
    }
    private static ChannelHandler echo =new ChannelHandler() {
        @Override
        public void onRead(SocketChannel socket) throws Exception {
            final ByteBuffer buffer = ByteBuffer.allocate(256);
            final int bytesRead = socket.read(buffer);
            if (bytesRead >0){
                buffer.flip();
                socket.write(buffer);
                buffer.clear();
            }else  if(bytesRead<0){
                socket.close();
                System.out.println("client close");
            }
        }
        @Override
        public void onAccept() {}
    };
    public static void start(int port) throws Exception{
        //因为是单reactor单线程，所以只有一个信道
        final ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //设置非阻塞并且注册端口
        serverSocketChannel.configureBlocking(false);
        InetSocketAddress address = new InetSocketAddress(port);
        serverSocketChannel.bind(address);
        final Selector selector =Selector.open();
        SelectionKey sk = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        //连接进来后先进行accept操作
        sk.attach(new ChannelHandler() {
            @Override
            public void onRead(SocketChannel channel) throws Exception {}

            @Override
            public void onAccept() {
                try {
                    SocketChannel socket = serverSocketChannel.accept();
                    System.out.println("Accept ！");
                    System.out.println("接受的socket:"+socket);
                    socket.configureBlocking(false);
                    SelectionKey sk = socket.register(selector, 0);
                    sk.attach(echo);
                    sk.interestOps(SelectionKey.OP_READ);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        while (true){ //循环判断状态，根据状态进行操作
            selector.select();
            Set<SelectionKey> readKeys = selector.selectedKeys();
            Iterator<SelectionKey> it = readKeys.iterator();
            while(it.hasNext()){
                SelectionKey key = it.next();
                ChannelHandler handler = (ChannelHandler) key.attachment();
                if (key.isAcceptable()){
                    handler.onAccept();
                }
                if (key.isReadable()){
                    handler.onRead((SocketChannel) key.channel());
                }
                it.remove();
            }
        }
    }
    public static void main(String[] args) throws Exception {
        start(2020);
    }
}