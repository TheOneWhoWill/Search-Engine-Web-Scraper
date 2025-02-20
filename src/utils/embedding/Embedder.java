package utils.embedding;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import static utils.embedding.Ollama.makePostRequest;

public class Embedder {
	public static float[] embed(String model, String text) {
		String requestBody = String.format("{\"model\": \"%s\", \"input\": [\"%s\"]}", model, text);

		try {
			JsonObject response = makePostRequest("/api/embed", requestBody);

			if (response == null) {
				return null;
			}

			JsonArray embeddingsArray = response.getAsJsonArray("embeddings");

			if (embeddingsArray == null || embeddingsArray.size() == 0) {
				return null;
			}

			JsonElement firstEmbeddingElement = embeddingsArray.get(0);

			if (!firstEmbeddingElement.isJsonArray()) {
				System.err.println("The first element in 'embeddings' is not an array.");
				return null;
			}

			JsonArray firstEmbeddingArray = firstEmbeddingElement.getAsJsonArray();

			int size = firstEmbeddingArray.size();
			float[] floatArray = new float[size];


			for (int i = 0; i < size; i++) {
				floatArray[i] = firstEmbeddingArray.get(i).getAsFloat();
			}

			return floatArray;
		} catch (Exception e) {
			System.err.println("Error processing the response: " + e.getMessage());
			return null;
		}
	}
}
