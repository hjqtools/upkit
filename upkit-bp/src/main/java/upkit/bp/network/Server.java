package upkit.bp.network;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class Server {

	public static void main(String[] args) throws IOException {

		Date date = new Date();
		ServerSocket serverSocket = new ServerSocket(12007);
		while (true) {
			Socket connection = serverSocket.accept();
			OutputStream os = connection.getOutputStream();
			byte[] bytes = new String(date.getTime() + "").getBytes();
			os.write(bytes);
			os.flush();
		}

	}

}
