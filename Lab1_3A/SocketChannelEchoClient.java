import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.ByteBuffer;
import java.net.InetSocketAddress;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class SocketChannelEchoClient {
  private static final int BUFSIZE = 1024;

  public static void main(String[] args) throws IOException {
    if (args.length != 2) {
      System.err.println("java EchoClient host port");
      return;
    }

    if (!args[1].matches("[0-9]+")) {
      System.err.println("Invalid port: " + args[1]);
      System.exit(2);
    }

    int port = Integer.parseInt(args[1]);
    InetSocketAddress socketAddress = new InetSocketAddress(args[0], port);
    SocketChannel channel = SocketChannel.open(socketAddress);

    Charset charset = Charset.forName("UTF-8");
    BufferedReader stdin = 
      new BufferedReader(new InputStreamReader(System.in));
    ByteBuffer buffer = ByteBuffer.allocate(BUFSIZE);
    String  line;

    while (true) {
      System.out.print("> ");
      if ((line = stdin.readLine()) == null)
        break; 
      channel.write(charset.encode(line));
      if (channel.read(buffer) == -1)
        break;
      buffer.flip();
      System.out.println(charset.decode(buffer));
      buffer.clear();
    }
    channel.close();
  }
}
