package ae2Proxy;

import java.io.File;
import java.io.IOException;


public class ClienteServidor {
	public void lanzar() throws IOException {

		ProcessBuilder processProxy = new ProcessBuilder ("java", "-cp", "bin", "ae2Proxy.Proxy");
		processProxy.redirectError(new File("ErrorProxy.txt"));
		processProxy.redirectOutput(new File("OutputProxy.txt"));
		processProxy.start();
		
		ProcessBuilder processCliente = new ProcessBuilder ("java", "-cp", "bin", "ae2Proxy.Cliente", "www.google.com");
		processCliente.redirectError(new File("ErrorCliente1.txt"));
		processCliente.redirectOutput(new File("OutputCliente1.txt"));
		processCliente.start();

		/*ProcessBuilder processCliente2 = new ProcessBuilder ("java", "-cp", "bin", "ae2Proxy.Cliente", "www.floridaoberta.com");
		processCliente2.redirectError(new File("ErrorCliente2.txt"));
		processCliente2.redirectOutput(new File("OutputCliente2.txt"));
		processCliente2.start();
		
		ProcessBuilder processCliente3 = new ProcessBuilder ("java", "-cp", "bin", "ae2Proxy.Cliente", "www.google.es");
		processCliente3.redirectError(new File("ErrorCliente3.txt"));
		processCliente3.redirectOutput(new File("OutputCliente3.txt"));
		processCliente3.start();*/
		
	}
	public static void main(String[] args) throws InterruptedException, IOException {
		
		ClienteServidor lanzador = new ClienteServidor();
		lanzador.lanzar();
		
	}

}
