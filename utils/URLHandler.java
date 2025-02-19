package utils;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class URLHandler {
	public static String normalizeURL(String url) {
		if (url == null || url.trim().isEmpty()) {
			return null;
		}

		url = url.trim().toLowerCase();

		try {
			URI uri = new URI(url);

			// Checking if the url is abs (not a relative path like in a folder structure or same domain)
			if (!uri.isAbsolute()) {
				return null;
			}

			java.net.URL parsedUrl = uri.toURL();

			String base = parsedUrl.toString();

			// Removing any anchor tags
			if (base.contains("#")) {
				base = base.substring(0, base.indexOf("#"));
			}

			uri = new URI(base);
			parsedUrl = uri.toURL();

			// Remove ports from url (80 for https, 443 for http)
			int port = parsedUrl.getPort();
			String scheme = parsedUrl.getProtocol();
			
			if ((scheme.equals("http") && port == 80) || (scheme.equals("https") && port == 443) || port == -1) {
				uri = new URI(parsedUrl.getProtocol(), parsedUrl.getHost(), parsedUrl.getPath(), parsedUrl.getQuery(), null);
				parsedUrl = uri.toURL();
			}

			// The end of a url doesn't need a slash unless its the first
			String path = parsedUrl.getPath();

			if (path.endsWith("/") && !path.equals("/")) {
				uri = new URI(parsedUrl.getProtocol(), parsedUrl.getHost(), path.substring(0, path.length() - 1), parsedUrl.getQuery(), null);
				parsedUrl = uri.toURL();
			}

			// 6. Remove www (optional, depends on your preference):
			String host = parsedUrl.getHost();
			if (host.startsWith("www.")) {
				uri = new URI(parsedUrl.getProtocol(), host.substring(4), parsedUrl.getPath(), parsedUrl.getQuery(), null);
				parsedUrl = uri.toURL();
			}

			// 7. Sort query parameters (optional but recommended):
			String query = parsedUrl.getQuery();
			if (query != null) {
				String cleanedQuery = removeTrackingParams(query);
				uri = new URI(parsedUrl.getProtocol(), parsedUrl.getHost(), parsedUrl.getPath(), cleanedQuery, null);
				parsedUrl = uri.toURL();
			}

			return parsedUrl.toString();
		} catch (MalformedURLException | URISyntaxException e) {
			return null;
		}
	}

	private static String removeTrackingParams(String query) {
		if (query == null || query.isEmpty()) {
			return null;
		}

		List<String> params = new ArrayList<>();

		for (String param : query.split("&")) {
			if (!isTrackingParameter(param)) {
				params.add(param);
			}
		}

		Collections.sort(params);

		return params.isEmpty() ? null : String.join("&", params);
	}


	private static boolean isTrackingParameter(String param) {
		// 1. Known tracking parameters:
		int equalsIndex = param.indexOf('=');
		String paramName = equalsIndex == -1 ? param : param.substring(0, equalsIndex);

		return TrackingParams.KNOWN_PARAMS_SET.contains(paramName);
	}

}
