package upkit.bp.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {

	public static void main(String[] args) {
		Socket socket = null;

		try {
			socket = new Socket("127.0.0.1", 12007);
//			socket.setSoTimeout(15000);
			socket.setKeepAlive(false);
//			socket.shutdownOutput();
			InputStream inputStream = socket.getInputStream();
			InputStreamReader reader = new InputStreamReader(inputStream);

			StringBuilder builder = new StringBuilder();
			for (int c = reader.read(); c != -1; c = reader.read()) {
				System.out.println((char) c);
				System.out.println("aaa");
			}
			System.out.println("sadsd");
		} catch (IOException r) {
			r.printStackTrace();
		} finally {
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
}
