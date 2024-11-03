import java.io.IOException;
import java.net.*;
import java.io.*;
class Server {
	
	ServerSocket server;
	Socket socket;
	BufferedReader br;
	PrintWriter  out;
	//constructor
	public Server() {
		
		try {
			server= new ServerSocket(7777);
			System.out.println("Server is Ready to accept Connection");
			System.out.println("Waiting...");
			socket=server.accept();
			
			
			br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out=new  PrintWriter(socket.getOutputStream());
			startReading();
			startWriting();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
private void startWriting() {
		// TODO Auto-generated method stub
		Runnable r2=()->{
			System.out.println("Writer Started");
			try {
			while( !socket.isClosed())
			{
				
					BufferedReader br1=new BufferedReader(new InputStreamReader(System.in));
				   String content=br1.readLine();
				   
				 
				   out.println(content);
				   out.flush();
				   if (content.equals("exit")){
					   socket.close();
					   break;
				   }
				
			}
			} catch (IOException e) {
				//e.printStackTrace();
				System.out.println("Connection closed");
			}
			System.out.println("Connection closed");
			
		};
		new Thread(r2).start();
	}
private void startReading() {
		// TODO Auto-generated method stub
	Runnable r1=()->{
		System.out.println("Reader started");
		try {
		while(true){
			String msg=br.readLine();
			if(msg.equals("exit")) {
				System.out.println("Client terminated the chat.");
				socket.close();
				break;
			}
			System.out.println("Client:"+msg);
			
		}
		}catch(Exception e) {
			//e.printStackTrace();
			System.out.println("Connection is closed");
		}
	};
	new Thread(r1).start();
	}
public static void main(String[] args) {
	System.out.println("This is Server...Going To Start Server");
	new Server();
}
}
