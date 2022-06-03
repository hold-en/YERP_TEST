package yerp.common.valid;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DefaultValidUtil {
    public boolean isNull(String str) {
        return (str == null || str.equals(""));
    }
    public boolean isNumber(String str) {
        String regex = "\\D";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return !matcher.find();
    }

}
