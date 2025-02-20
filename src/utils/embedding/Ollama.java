package utils.embedding;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.zip.GZIPInputStream;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

public class Ollama {
	public static final String HOST = "127.0.0.1";
	public static final int PORT = 11434; // ollama uses this port
	public static final String BASE_URL = "http://" + HOST + ":" + PORT;
	public static final HttpClient client = HttpClient.newBuilder()
														.version(HttpClient.Version.HTTP_2)
														.connectTimeout(Duration.ofSeconds(10))
														.build();

	private static final Gson gson = new Gson();

	public static JsonObject makeGetRequest(String path) {
		try {
			HttpRequest request = HttpRequest.newBuilder()
						.uri(URI.create(BASE_URL + path))
						.GET()
						.build();
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

			return handleResponse(response);
		} catch (IOException | InterruptedException e) {
			System.err.println("Error in makeGetRequest: " + e.getMessage());
			return null;
		}
	}

	public static JsonObject makePostRequest(String path, String body) {
		try {
			HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(BASE_URL + path))
				.POST(HttpRequest.BodyPublishers.ofString(body))
				.header("Content-Type", "application/json")
				.header("Accept-Encoding", "gzip")
				.build();

			long startTime = System.nanoTime(); 
			HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());
			long endTime = System.nanoTime();
			double elapsedTimeInMs = (endTime - startTime) / 1_000_000.0;
			System.out.println("Response time: " + elapsedTimeInMs + " ms");
			InputStream responseStream = response.body();

			// Handle gzip-compressed responses if necessary
			if ("gzip".equalsIgnoreCase(response.headers().firstValue("Content-Encoding").orElse(""))) {
				responseStream = new GZIPInputStream(responseStream);
			}
			
			// Use GSON's streaming API to parse the JSON directly from the stream
			try (JsonReader jsonReader = new JsonReader(new InputStreamReader(responseStream))) {
				return JsonParser.parseReader(jsonReader).getAsJsonObject();
			}
		} catch (IOException | InterruptedException e) {
			System.err.println("Error in makePostRequest: " + e.getMessage());
			return null;
		}
	}

	private static JsonObject handleResponse(HttpResponse<String> response) {
        if (response.statusCode() == 200) {
            String json = response.body();

            return gson.fromJson(json, JsonObject.class);
        } else {
            System.err.println("Error: API request failed with status code " + response.statusCode() + " and body: " + response.body()); // Include body for debugging
            return null;
        }
    }

}
