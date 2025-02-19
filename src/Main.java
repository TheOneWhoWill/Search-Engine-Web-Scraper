import static utils.URLHandler.normalizeURL;
import static utils.embedding.OllamaStatusChecker.isModelInstalled;
import static utils.embedding.OllamaStatusChecker.isOllamaRunning;

public class Main {
	private static final String CLASS_PATH = System.getProperty("java.class.path");

	public static void main(String[] args) {
		boolean ollamaRunning = isOllamaRunning();

		if (ollamaRunning) {
			System.out.println("Ollama is running...");
		} else {
			System.out.println("Ollama is not running on the host machine.");
			return;
		}

		if (isModelInstalled("snowflake-arctic-embed2")) {
			System.out.println("Snowflake Arctic Embedder is installed...");
		} else {
			System.out.println("Snowflake Arctic Embedder is NOT installed.");
			return;
		}

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
		checkIfInClassPath("gson-2.12.1.jar");
	}

	public static void checkIfInClassPath(String jar) {
		if (CLASS_PATH.contains(jar)) {
			System.out.println(jar + " is in the classpath.");
		} else {
			System.out.println(jar + " is NOT in the classpath.");
		}
	}
}