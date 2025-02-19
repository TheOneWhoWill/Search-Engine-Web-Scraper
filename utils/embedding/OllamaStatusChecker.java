package utils.embedding;

import java.net.Socket;

public class OllamaStatusChecker {
	public static boolean isOllamaRunning() {
		String host = "127.0.0.1";
		int port = 11434; // Ollama uses this port

		try (Socket socket = new Socket(host, port)) {
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
