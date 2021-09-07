import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

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
        final   ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        InetSocketAddress address = new InetSocketAddress(port);
        serverSocketChannel.bind(address);

    }


}
