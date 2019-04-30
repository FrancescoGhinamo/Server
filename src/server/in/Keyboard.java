package server.in;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class Keyboard {
	
	private InputStreamReader streamReader;
	private BufferedReader bufferedReader;
	
	public Keyboard() {
		streamReader = new InputStreamReader(System.in);
		bufferedReader = new BufferedReader(streamReader);
	}
	
	public String readString() {
		String res = "";
		try {
			res = bufferedReader.readLine();
		} catch (IOException e) {
			System.out.println(e);
		}
		return res;
	}
	
	public int readInt() {
		int r = 0;
		r = Integer.parseInt(readString());
		return r;
	}
	
	public float readFloat() {
		float r = 0;
		r = Float.parseFloat(readString());
		return r;
	}
	
	public double readDouble() {
		double r = 0;
		r = Double.parseDouble(readString());
		return r;
	}
	
	public boolean readBoolean() {
		boolean r = false;
		r = Boolean.parseBoolean(readString());
		return r;
	}
	
	public GregorianCalendar readGregorianCalendar() {
		GregorianCalendar date = new GregorianCalendar();
		
		SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
		try {
			date.setTime(fmt.parse(readString()));
		} catch (ParseException e) {
			System.out.println(e);
		}
		return date;
	}

}
