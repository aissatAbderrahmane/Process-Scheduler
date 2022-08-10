package Algorithmes;
import java.io.*;
import java.net.*;
public class Reseaux_server {
    private final ServerSocket server;
    public Reseaux_server(int port) throws IOException{
        server = new ServerSocket(port);
        System.out.println("Server Active Dans : "+port+" ....");
        while(true){
            Socket sck = server.accept();
            InputStream INstream = sck.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(INstream));
            if("1".equals(reader.readLine())){
                System.out.println("\n Un Client est online avec le server...");
            }
        }
    }
}
