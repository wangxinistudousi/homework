package test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiThreadServer {
	
	public static void main (String[] args) {
		new MultiThreadServer();
	}
	
	public MultiThreadServer () {
		
		try {
			ServerSocket serversocket = new ServerSocket( 3333);
			
			int clientNo = 1;
			
			while (true) {
				Socket socket = serversocket.accept();
			
				InetAddress inetAddress = socket.getInetAddress();
				
				System.out.println("client "+clientNo+"ip is" + inetAddress.getHostAddress());
				
				HandleAClient task = new HandleAClient(socket);
				
				new Thread(task).start();
				
				clientNo++;
	      	
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	class HandleAClient implements Runnable{
		private Socket socket;
		
		public HandleAClient (Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			
			
			String line = null;
			
			try {
				BufferedReader buf = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				BufferedWriter bufout = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
				
				while ((line=buf.readLine())!=null) {
					System.out.println(line);
					
					StringBuffer re = new StringBuffer(line);
					
					String str = re.reverse().toString();
					System.out.println(re+"++ ++"+str);
					
					bufout.write(str);//输出字符到客户端
					bufout.newLine();
					bufout.flush();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
