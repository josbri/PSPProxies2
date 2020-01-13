package ae2Proxy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class WorkerProxy implements Runnable{
	private Socket conexion;

	//Clase que procesa la peticion: 
	public WorkerProxy(Socket c) {
		this.conexion = c;
	}

	@Override
	public void run() {
		try {
			InputStream is = conexion.getInputStream();
			OutputStream os = conexion.getOutputStream();
			InputStreamReader isr = new InputStreamReader(is);
			OutputStreamWriter osw = new OutputStreamWriter(os);
			BufferedReader bf = new BufferedReader(isr);
			PrintWriter pw = new PrintWriter(osw);
			String lineaPeticion = bf.readLine();	
			
			//Conexion con el servidor web:
			
			Socket socket = new Socket();
			System.out.println(lineaPeticion);
			InetSocketAddress direccion = new InetSocketAddress(lineaPeticion, 80);
			try {
				socket.connect(direccion);
				// Si llegamos aquí, es que la conexión sí se hizo.
				InputStream isInet = socket.getInputStream();
				OutputStream osInet = socket.getOutputStream();
				// Flujos que manejan caracteres
				InputStreamReader isrInet = new InputStreamReader(isInet);
				OutputStreamWriter oswInet = new OutputStreamWriter(osInet);
				// Flujos de líneas
				BufferedReader bReader = new BufferedReader(isrInet);
				PrintWriter pWriter = new PrintWriter(oswInet);

				System.out.println("A punto de hacer el get");
				
				//Enviamos la peticion al cliente:
				pWriter.println("GET /index.html");
				pWriter.flush();
				
				System.out.println("Termina el get");
				
				
				String linea = bReader.readLine();			

				//Enviamos la respuesta al cliente:
				pw.write(linea);
				pw.flush();
				
				//pw.write("Proxy: He recibido tu peticion\n");
				//pw.write(resultado);
				
				
				//Cerramos las conexiones
				pWriter.close();
				bReader.close();
				isr.close();
				osw.close();
				is.close();
				os.close();
				
			} catch (IOException e) {
				System.out.println("No se pudo establecer la conexion con el servidor web.");
			}
			
			finally {
				socket.close();
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}
		finally {
			try {
				conexion.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	
}
