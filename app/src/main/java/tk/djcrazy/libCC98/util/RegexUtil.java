package tk.djcrazy.libCC98.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.util.Log;

import com.orhanobut.logger.Logger;

import tk.djcrazy.libCC98.exception.ParseContentException;

public final class RegexUtil {
    public static final String PraseInfoNotFound="0098";
    public static final String sp=" |";
    private static final String logger_info_header ="getMatchedString regex Not Found:";
    private static StringBuilder regx_info=new StringBuilder(logger_info_header);

	/**
	 * @param regex
	 * @param content
	 * @return the first matched string.
	 * @throws ParseContentException if does not match any string.
	 */
	public static String getMatchedString(String regex, String content)
			throws ParseContentException {
		Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
		Matcher matcher = pattern.matcher(content);
		if (matcher.find()) {
			return matcher.group().trim();
		} else {


            regx_info.delete(logger_info_header.length(),regx_info.length())
                    .append(regex)
                    .append(sp)
                    .append(String.valueOf(regex.length()));

            Logger.t(RegexUtil.class.getSimpleName()).i(regx_info.toString());
            //Logger.t(RegexUtil.class.getSimpleName()).e("getMatchedString String: "+content);
			throw new ParseContentException(PraseInfoNotFound);


		}
	}

	/**
	 * @param regex
	 * @param content
	 * @param expectedMatchStringNumber <p>
	 *        If the size of the result list does not match
	 *        the value, it will throw ParseContentException.
	 *        </p>
	 *        <p>
	 *        If the expectedMatchStringNumber<=0, means that the number is not specified, and does
	 *        not throw ParseContentException
	 *        </p>
	 * @return matched string as List, if not found and expectedMatchStringNumber<=0, The size of List is 0.
	 * @throws ParseContentException if expectedMatchStringNumber > 0 and the size of list does not match the number.
	 */
	public static List<String> getMatchedStringList(String regex,
			String content, int expectedMatchStringNumber)
			throws ParseContentException {
		List<String> list = new ArrayList<String>();
		Matcher matcher = Pattern.compile(regex, Pattern.DOTALL).matcher(
				content);
		while (matcher.find()) {
			list.add(matcher.group());
		}
		if (expectedMatchStringNumber > 0
				&& list.size() < expectedMatchStringNumber) {
			throw new ParseContentException(
					"result does not equals the expected match number!");
		}
		return list;
	}
}
