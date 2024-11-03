
import java.net.*;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Client extends JFrame {
	Socket socket;
	BufferedReader br;
	PrintWriter  out;
	//Declare Components 
	private JLabel heading =new JLabel();
	private JTextArea messagArea =new JTextArea();
	private JTextField  messageInput=new JTextField();
	private Font font=new Font("Roboto",Font.PLAIN,20);
	//constructor
	public Client() {
		try {
			System.out.println("Sending request to server");
			socket=new Socket("192.168.29.101",7777);
			System.out.println("Connection Done");
			
			br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out=new  PrintWriter(socket.getOutputStream());
//			
			createGUI();
			handleEvents();
			startReading();
//			startWriting();
			
		}catch(Exception e) {
			
		}
	}
	private void handleEvents() {
		// TODO Auto-generated metho stub
		messageInput.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
//				System.out.println("Key released "+e.getKeyCode());
				if(e.getKeyCode()==10) {
//					System.out.println("You have pressed enter button");
					
				String contentToSend=messageInput.getText();
				messagArea.append("Me:"+contentToSend+"\n");
				out.println(contentToSend);
				out.flush();
				messageInput.setText("");
				messageInput.requestFocus();
				}
			}});
	
		}
	private void createGUI() {
		//gui code
		this.setTitle("Client Messager[END]");
		this.setSize(600,700);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		//coding for component
		heading.setFont(font);
		messagArea.setFont(font);
		messageInput.setFont(font);
		heading.setIcon(new ImageIcon("Bumblebee.jpeg")); 
		heading.setHorizontalTextPosition(SwingConstants.CENTER);
		heading.setVerticalTextPosition(SwingConstants.BOTTOM);
		
		
		heading.setHorizontalAlignment(SwingConstants.CENTER);
		heading.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
		messagArea.setEditable(false);
		messageInput.setHorizontalAlignment(SwingConstants.CENTER);
		//frame layout 
		this.setLayout(new BorderLayout());
	//adding the components to frame
		this.add(heading,BorderLayout.NORTH);
		JScrollPane jScrollPane=new JScrollPane(messagArea);
		this.add(jScrollPane,BorderLayout.CENTER);
		this.add(messageInput,BorderLayout.SOUTH);
		
		
		this.setVisible(true);
	}
	//start writing send  [method]
	private void startWriting() {
		// TODO Auto-generated method stub
		Runnable r2=()->{
			System.out.println("Writer Started");
			try {
			while(true && !socket.isClosed())
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
			System.out.println("Connection is  closed");
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			
		};
		new Thread(r2).start();
	}
	//start reading [method]
private void startReading() {
		// TODO Auto-generated method stub
	Runnable r1=()->{
		System.out.println("Reader started");
		try {
		while(true){
			
			String msg=br.readLine();
			if(msg.equals("exit")) {
				System.out.println("Sever terminated the chat.");
				JOptionPane.showMessageDialog(this,"Server Terminated the chat");
				messageInput.setEnabled(false);
				socket.close();
				break;
			}
			//System.out.println("Server :"+msg);
			messagArea.append("Server :"+msg +"\n");
			}
		}
		catch(Exception e) {
			//e.printStackTrace();
			System.out.println("Connection closed");
		}
		
	};
	new Thread(r1).start();
	}
public static void main(String[] args) {
	System.out.println("This is client");
	new Client();
}
}
