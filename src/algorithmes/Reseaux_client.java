package algorithmes;
import java.io.*;
import java.net.*;
public class Reseaux_client {
    private final Socket client;
    private final OutputStream Ostream;
    private final OutputStreamWriter OWstream;
    public Reseaux_client(String IP, int port) throws IOException{
       client = new Socket(IP, port);
       Ostream = client.getOutputStream();
       OWstream = new OutputStreamWriter(Ostream);
       OWstream.write("1");
       OWstream.flush();
    }
}
