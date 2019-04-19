package client;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class POClient extends JFrame implements ActionListener, WindowListener {
	
	
	private static final long serialVersionUID = 1L;
	private Socket socket;
	private Scanner socketIn;
	private PrintWriter socketOut;
	
	private JPanel connectPanel;
	private JPanel submitPanel;
	private JPanel resultPanel;
	private JPanel mainPanel;
	
	private JLabel lblIP;
	private JLabel lblPort;
	private JLabel lblSubmit;
	private JLabel lblResult;
	
	private JTextArea txtIP;
	private JTextArea txtPort;
	private JTextArea txtSubmit;
	private JTextArea txtResult;
	
	private JButton connect;
	private JButton disconnect;
	private JButton submit;
	
	public POClient() {
		connectPanel = new JPanel(new FlowLayout());
		submitPanel = new JPanel(new FlowLayout());
		resultPanel = new JPanel(new FlowLayout());
		mainPanel = new JPanel(new FlowLayout());
		
		lblIP = new JLabel("Server IP: ");
		lblPort = new JLabel("Port: ");
		lblSubmit = new JLabel("Phrase to operate on: ");
		lblResult = new JLabel("Result: ");
		
		txtIP = new JTextArea();
		txtIP.setColumns(20);
		txtIP.setRows(1);
		txtPort = new JTextArea();
		txtPort.setColumns(6);
		txtPort.setRows(1);
		txtSubmit = new JTextArea();
		txtSubmit.setColumns(50);
		txtSubmit.setRows(5);
		txtResult = new JTextArea();
		txtResult.setColumns(50);
		txtResult.setRows(5);
		txtResult.setEditable(false);
		
		connect = new JButton("Connect to server");
		connect.addActionListener(this);
		disconnect = new JButton("Disconnect from server");
		disconnect.addActionListener(this);
		submit = new JButton("Submit");
		submit.setEnabled(false);
		submit.addActionListener(this);
		
		
		connectPanel.add(lblIP);
		connectPanel.add(txtIP);
		connectPanel.add(lblPort);
		connectPanel.add(txtPort);
		connectPanel.add(connect);
		connectPanel.add(disconnect);
		
		submitPanel.add(lblSubmit);
		submitPanel.add(txtSubmit);
		submitPanel.add(submit);
	
		
		resultPanel.add(lblResult);
		resultPanel.add(txtResult);
		
		mainPanel.add(connectPanel);
		mainPanel.add(submitPanel);
		mainPanel.add(resultPanel);
		
		this.getContentPane().add(mainPanel);
		this.addWindowListener(this);
		this.setSize(800, 300);
		this.setResizable(false);
		this.setTitle("Phrase Operations Client");
		this.setEnabled(true);
		this.setVisible(true);
	}
	

	public static void main(String[] args) {
		POClient c = new POClient();
		

	}
	

	@Override
	public void windowActivated(WindowEvent arg0) {}

	@Override
	public void windowClosed(WindowEvent arg0) {}

	@Override
	public void windowClosing(WindowEvent arg0) {
		
		try {
			disconnectFromServer();
		} catch (IOException | NullPointerException e) {
			txtResult.setText("Restart client please");
		}
		System.exit(0);
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {}

	@Override
	public void windowDeiconified(WindowEvent arg0) {}

	@Override
	public void windowIconified(WindowEvent arg0) {}

	@Override
	public void windowOpened(WindowEvent arg0) {}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String source = arg0.getActionCommand();
		
		if(source == "Connect to server") {
			connectToServer();
			
		}
		
		if(source == "Disconnect from server") {
			try {
				disconnectFromServer();
			} catch (IOException | NullPointerException e) {
				txtResult.setText("Restart client please");
			}
		}
		
		if(source == "Submit") {
			submit();
		}
	}
	
	
	
	public void connectToServer() {
		String ip = "127.0.0.1";
		if(txtIP.getText() != "") {
			ip = txtIP.getText();
		}
		int port;
		try {
			port = Integer.parseInt(txtPort.getText());
		} catch(NumberFormatException e) {
			port = 8080;
		}
		
		try {
			socket = new Socket(ip, port);
			socketIn = new Scanner(socket.getInputStream());
			socketOut = new PrintWriter(socket.getOutputStream());
			submit.setEnabled(true);
			
		} catch (IOException e) {
			txtResult.setText("Restart client please");
		}
		
		
		
		
	}
	
	
	public void submit() {
		socketOut.println(txtSubmit.getText());
		socketOut.flush();
		
		String _text = "";
		_text += socketIn.nextLine() + "\n";
		_text += socketIn.nextLine() + "\n";
		_text += socketIn.nextLine();
		txtResult.setText(_text.toString());
		
	}

	
	public void disconnectFromServer() throws IOException, NullPointerException {
		socketOut.close();
		socketIn.close();
		socket.close();
		submit.setEnabled(false);
	}
}
