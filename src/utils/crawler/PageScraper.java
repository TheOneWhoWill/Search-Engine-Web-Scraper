package utils.crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.stream.Collectors;

import static utils.URLHandler.normalizeURL;
import utils.pages.WebPage;

public class PageScraper {
	private static final int MAX_REDIRECTS = 5;

	public String scrapeUrlRawData(String url) {
		return scrapeUrlRawData(url, 0);
	}

	public String scrapeUrlRawData(String urlString, int redirectCount) {
		if (redirectCount >= MAX_REDIRECTS) {
            System.err.println("Too many redirects");
            return null;
        }

		try {
			URI uri = new URI(urlString);
			URL url = uri.toURL();

			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setInstanceFollowRedirects(false); // we are handling redirects
			connection.setRequestMethod("GET");
			connection.setRequestProperty("User-Agent", "Mozilla/5.0");

			int status = connection.getResponseCode();

			// Handling redirects
			if (status == HttpURLConnection.HTTP_MOVED_PERM ||status == HttpURLConnection.HTTP_MOVED_TEMP) {
				String newUrl = connection.getHeaderField("Location");
				return scrapeUrlRawData(newUrl, redirectCount + 1);
			}

			InputStreamReader pageInputStream = new InputStreamReader(connection.getInputStream());
            String content;

			try (BufferedReader reader = new BufferedReader(pageInputStream)) {
				content = reader.lines().collect(Collectors.joining());
			}

			return content;
		} catch (IOException | URISyntaxException e) {
			return null;
		}
	}

	public WebPage scrapePage(String url) {
		url = normalizeURL(url);

		if (url == null) {
			return null;
		}

		String rawPage = scrapeUrlRawData(url);

		return null;
	}
}
