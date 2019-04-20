package gestione;
import java.io.*;
import java.net.*;
public class Server {
	private static final int port = 8080;
	private ServerSocket server =null;
	private Clients clienti;
	
	private DataInputStream input;
	private DataOutputStream output;
	public void comunica()
	{
		
			//aspetto messaggio client
			try {
				String letto=input.readLine();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			//server genera la pagina e mi restitusce la roba(gia convertita oppure solo la pagina come vuoi tu :)
			try {
				output.writeBytes("messaggio o la pagine web???");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				clienti.get(0).close();//chiudo la connessione con il client
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
	}
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
		s.comunica();
	}

}
