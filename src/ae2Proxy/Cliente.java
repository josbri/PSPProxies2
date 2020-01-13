package ae2Proxy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Cliente implements Runnable{
	
	private String direccionInternet;
	
	public Cliente(String dir) {
		this.direccionInternet = dir;
	}
	
	@Override
	public void run() {
		InetSocketAddress direccion = new InetSocketAddress("localhost", 9856);
		Socket socket = new Socket();
		try {
			socket.setReuseAddress(true);
			socket.connect(direccion);
			InputStreamReader isr = new InputStreamReader(socket.getInputStream());
			BufferedReader bfr = new BufferedReader(isr);
			PrintWriter pw = new PrintWriter(socket.getOutputStream());
			//Le pasamos la direccion
			pw.println(direccionInternet);
			pw.flush();
			String resultado = bfr.readLine();
			System.out.println(resultado);
			bfr.close();
			pw.close();
			isr.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		
		finally {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		Cliente cliente	= new Cliente(args[0]);
		cliente.run();
	}
}
