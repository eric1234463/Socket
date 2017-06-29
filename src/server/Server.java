import java.io.IOException;
import java.net.ServerSocket;


public class Server {
	public static Connection conn;
	public static ServerSocket serversocket;
	public static void main(String[] args) throws IOException {
		serversocket = new ServerSocket(8999);
		while(true){
			conn = new Connection(serversocket);
		}
    }
}
