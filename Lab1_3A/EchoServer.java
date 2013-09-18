// TCP echo server (non-concurrent)
// Exercise: make this into a multithreaded server
import java.net.*;
import java.io.*;

class EchoServer {
  public static void main(String[] args) {
    if (args.length != 1) {
      System.err.println("need to specify port");
      System.exit(1);
    }

    ServerSocket listeningSocket = null;
    Socket connectedSocket = null;
    try {
      int port = Integer.parseInt(args[0]);
      listeningSocket = new ServerSocket(port);
      connectedSocket = listeningSocket.accept();
      BufferedReader socketReader = new BufferedReader(new InputStreamReader(
                                      connectedSocket.getInputStream()));
      PrintWriter socketWriter = new PrintWriter(new OutputStreamWriter(
                                   connectedSocket.getOutputStream()));

      String line; 
      while ((line = socketReader.readLine()) != null) {
         socketWriter.println(line);
         socketWriter.flush();
      }

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (connectedSocket != null)
          connectedSocket.close();
        if (listeningSocket != null)
          listeningSocket.close();
      } catch (IOException e) {
        e.printStackTrace();
      }  
    }
  }
}
