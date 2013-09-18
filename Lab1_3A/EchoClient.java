// TCP echo client
import java.io.*;
import java.net.*;

class EchoClient {
  public static void main(String[] args) throws IOException {
    if (args.length != 2) {
      System.err.println("java EchoClient host port");
      System.exit(1);
    }

    if (!args[1].matches("[0-9]+")) {
      System.err.println("Invalid port: " + args[1]);
      System.exit(2);
    }

    int port = Integer.parseInt(args[1]);
    Socket s = null;
    try {
      s = new Socket(args[0], port);  

      PrintWriter sockOut = new PrintWriter(
                              s.getOutputStream(), true);  // true = autoflush 
      BufferedReader sockIn = new BufferedReader(
                                new InputStreamReader(s.getInputStream()));
      BufferedReader stdin = new BufferedReader(
                               new InputStreamReader(System.in));

      String  line, echoedLine;
      while (true) {
        System.out.print("> ");
        if ((line = stdin.readLine()) == null)
          break; 
        sockOut.println(line);
        if ((echoedLine = sockIn.readLine()) == null)
          break; 
        System.out.println(echoedLine);
      }
    } finally {
      if (s != null)
        s.close();
    }
  }
}

