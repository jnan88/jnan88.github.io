
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexKit {
	static final String		URL_REGEX	= "(https?|http)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]";
	static final Pattern	URL_PATTERN	= Pattern.compile(URL_REGEX);

	/**
	 * 
	 * @param text
	 * @return 提取文本中的第一个URL
	 */
	public static String findFirstUrl(String text) {
		Matcher matcher = URL_PATTERN.matcher(text);
		if (matcher.find()) {
			return matcher.group();
		}
		return null;
	}

	/**
	 * 
	 * @param text
	 * @return 提取文本中全部URL
	 */
	public static List<String> findUrl(String text) {
		Matcher matcher = URL_PATTERN.matcher(text);
		List<String> urlList = new ArrayList<>();
		while (matcher.find()) {
			urlList.add(matcher.group());
		}
		return urlList;
	}
}
