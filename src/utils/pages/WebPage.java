package utils.pages;

import java.sql.Timestamp;

public class WebPage {
	private String url;
	private String title;
	private String description;
	private String author;
	private String language = "en";
	private PageType type;
	private double pageRank;
	private float[] embedding;
	private Timestamp crawledAt;
	private Timestamp updatedAt;

	// Constructor for new page
	public WebPage(String url, String title, String description, float[] embedding) {
		this.url = url;
		this.title = title;
		this.description = description;
		this.pageRank = 1.0;
		this.embedding = embedding;
		this.crawledAt = new Timestamp(System.currentTimeMillis());
		this.updatedAt = new Timestamp(System.currentTimeMillis());
	}

	public void updateEmbedding(float[] embedding) {
		this.embedding = embedding;
	}

	private static String vectorToSqlArray(float[] vector) {
		StringBuilder sb = new StringBuilder("[");

		for (int i = 0; i < vector.length; i++) {
			sb.append(vector[i]);

			if (i < vector.length - 1) sb.append(", ");
		}

		sb.append("]");

		return sb.toString();
	}

	private static float[] parseSqlArray(String sqlArray) throws IllegalArgumentException {
		if (sqlArray == null || sqlArray.isEmpty()) {
			throw new IllegalArgumentException("Embedding data is missing or malformed.");
		}

		sqlArray = sqlArray.replaceAll("[\\[\\]]", "");
		String[] parts = sqlArray.split(",\\s*");
		float[] vector = new float[parts.length];

		try {
			for (int i = 0; i < parts.length; i++) {
				vector[i] = Float.parseFloat(parts[i]);
			}
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Error parsing embedding vector: " + e.getMessage());
		}

		return vector;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Timestamp getCrawledAt() {
		return crawledAt;
	}

	public void setCrawledAt(Timestamp crawledAt) {
		this.crawledAt = crawledAt;
	}

	public float[] getEmbedding() {
		return embedding;
	}

	public void setEmbedding(float[] embedding) {
		this.embedding = embedding;
	}

	public double getPageRank() {
		return pageRank;
	}

	public void setPageRank(double pageRank) {
		this.pageRank = pageRank;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}