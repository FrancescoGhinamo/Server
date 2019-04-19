package gestione;
import java.net.*;
import java.io.*;

public class Server {
	private static final int port = 8080;
	private ServerSocket server =null;
	private Clients clienti;
	
	private DataInputStream input;
	private DataOutputStream output;
	
	public Socket attendi()
	{
		
			
		clienti = new Clients();
		try {
			System.out.println("Inizializzo server");
			server = new ServerSocket(port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			System.out.println("Pronto, in ascolto sulla porta "+port);
			clienti.add(server.accept());
			System.out.println("Connessione stabilita ");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			server.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			input = new DataInputStream(clienti.get(0).getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			output = new DataOutputStream(clienti.get(0).getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return clienti.get(0);
		
	}
	public static void main(String[] args)
	{
		Server s = new Server();
		s.attendi();
	}

}
