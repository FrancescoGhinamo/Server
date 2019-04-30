package testing;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import com.sun.net.httpserver.HttpServer;

import server.backend.service.fileService.ByteFileServiceFactory;

public class Clients {
	private Socket server=null;
	private static final int port =8080;
	private InputStream input;
	private DataOutputStream output;
	
	public Socket connetti()
	{
		try {
			System.out.println("Provo a connettermi ....");
			Socket server = new Socket(InetAddress.getLocalHost(),port);
			
			System.out.println("Connesso");
			input = server.getInputStream();
			output=new DataOutputStream(server.getOutputStream());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			output.write("Pippo".getBytes());		
			output.flush();
			System.out.print("Message sent");
			try {
				Thread.sleep(1000000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			System.out.println("Host sconosciuto");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return server;
	}
	public static void main(String[] args)
	{
		Clients c = new Clients();
		c.connetti();
		
		
		
	}
	

}
