package utils.crawler;

import java.sql.*;

public class WebPage {
	private int id;
	private String url;
	private String title;
	private String content;
	private double pageRank;
	private float[] embedding;
	private Timestamp createdAt;
	private Timestamp updatedAt;

	// Constructor for new page
	public WebPage(String url, String title, String content, float[] embedding) {
		this.url = url;
		this.title = title;
		this.content = content;
		this.pageRank = 1.0;
		this.embedding = embedding;
		this.createdAt = new Timestamp(System.currentTimeMillis());
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

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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