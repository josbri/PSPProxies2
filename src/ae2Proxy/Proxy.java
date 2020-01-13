package ae2Proxy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Proxy implements Runnable {
	
	private ExecutorService executor = Executors.newFixedThreadPool(10); 
		
	@Override
	public void run() {
		//Creamos un socket de escucha para recibir llamadas del cliente
		ServerSocket socketEscucha = null;
		try {
			socketEscucha = new ServerSocket(9856);
		} catch (IOException e) {
			System.out.println("Fallo la creacion del socket de escucha del proxy." + e);
			return;
		}
		while(true) {
			try {
				Socket conexion = socketEscucha.accept();	
				System.out.println("Conexion recibida en el proxy!");
				WorkerProxy worker = new WorkerProxy(conexion);
				executor.execute(worker);				
			} catch (Exception e) {
				try {
					socketEscucha.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				System.out.println(e);
			}
		}		
	}
	
	public static void main(String[] args) {
		Proxy proxy = new Proxy();
		proxy.run();
	}

}
