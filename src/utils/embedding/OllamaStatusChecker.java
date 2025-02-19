package utils.embedding;

import java.io.IOException;
import java.net.Socket;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class OllamaStatusChecker {
	private static final String HOST = "127.0.0.1";
	private static final int PORT = 11434; // ollama uses this port
	private static final String BASE_URL = "http://" + HOST + ":" + PORT;
	private static final HttpClient client = HttpClient.newHttpClient();

	public static boolean isOllamaRunning() {

		try (Socket socket = new Socket(HOST, PORT)) {
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean isModelInstalled(String modelName) {
		try {
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create(BASE_URL + "/api/tags"))
					.GET()
					.build();

			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

			if (response.statusCode() == 200) {
				String json = response.body();

				// Use Gson to parse the JSON response
				Gson gson = new Gson();
				JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
				JsonArray modelsArray = jsonObject.getAsJsonArray("models");

				for (int i = 0; i < modelsArray.size(); i++) {
					JsonObject model = modelsArray.get(i).getAsJsonObject();
					String name = model.get("name").getAsString();

					if (name.equals(modelName)) {
						return true; // Model found
					}
				}

				return false; // Model not found

			} else {
				System.err.println("Error: API request failed with status code " + response.statusCode());
				return false; // API error
			}

		} catch (IOException | InterruptedException e) {
			System.err.println("Error: " + e.getMessage());
			return false; // Exception occurred
		}
	}

}
