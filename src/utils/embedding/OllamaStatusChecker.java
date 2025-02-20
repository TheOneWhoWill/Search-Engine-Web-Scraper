package utils.embedding;

import java.net.Socket;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import static utils.embedding.Ollama.HOST;
import static utils.embedding.Ollama.PORT;
import static utils.embedding.Ollama.makeGetRequest;

public class OllamaStatusChecker {

	public static boolean isOllamaRunning() {

		try (Socket socket = new Socket(HOST, PORT)) {
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	
	public static boolean isModelInstalled(String modelName) {
		JsonObject jsonObject = makeGetRequest("/api/tags");
		JsonArray modelsArray = jsonObject.getAsJsonArray("models");

		for (int i = 0; i < modelsArray.size(); i++) {
			JsonObject model = modelsArray.get(i).getAsJsonObject();
			String name = model.get("name").getAsString();

			if (name.equals(modelName)) {
				return true; // Model found
			}
		}

		return false;
	}

}
