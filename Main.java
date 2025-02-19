import static utils.URLHandler.normalizeURL;

public class Main {
	private static final String CLASS_PATH = System.getProperty("java.class.path");

	public static void main(String[] args) {
        String url1 = "https://www.example.com:80/path/to/page?param2=value2&param1=value1#fragment";
        String url2 = "http://www.example.com/path/to/page/";
        String url3 = "http://www.www.example.com/path/to/page";
        String url4 = "/relative/path"; // Example, will return null unless you implement relative URL resolution
        String url5 = "https://www.example.com/path/to/page?param1=value1&param2=value2";

        System.out.println(normalizeURL(url1));
        System.out.println(normalizeURL(url2));
        System.out.println(normalizeURL(url3));
        System.out.println(normalizeURL(url4));
        System.out.println(normalizeURL(url5));

		checkIfInClassPath("postgresql-42.7.5.jar");
	}

	public static void checkIfInClassPath(String jar) {
		if (CLASS_PATH.contains(jar)) {
			System.out.println(jar + " is in the classpath.");
		} else {
			System.out.println(jar + " is NOT in the classpath.");
		}
	}
}