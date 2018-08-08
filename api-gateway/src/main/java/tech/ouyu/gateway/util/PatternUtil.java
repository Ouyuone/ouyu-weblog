package tech.ouyu.gateway.util;

import java.util.regex.Pattern;

/**
 * Created by Administrator on 2018/7/16.
 */
public class PatternUtil {
    public static final String IMG_REG = ".+(.JPEG|.jpeg|.JPG|.jpg|.GIF|.gif|.BMP|.bmp|.PNG|.png)$";
    public static final String ANDROID_REG = "\\bandroid|Nexus\\b";
    public static final String IOS_REG = "ip(hone|od|ad)";

    public PatternUtil() {
    }

    public static boolean MatcherMobile(String userAgent) {
        if(null == userAgent) {
            return false;
        } else {
            userAgent = userAgent.toLowerCase();
            return MatcherFind("\\bandroid|Nexus\\b", userAgent) || MatcherFind("ip(hone|od|ad)", userAgent);
        }
    }

    public static boolean MatcherImg(String imgName) {
        return MatcherReg(".+(.JPEG|.jpeg|.JPG|.jpg|.GIF|.gif|.BMP|.bmp|.PNG|.png)$", imgName);
    }

    public static boolean MatcherReg(String regEx, String data) {
        return Pattern.compile(regEx).matcher(data.toLowerCase()).matches();
    }

    public static boolean MatcherFind(String regEx, String data) {
        return Pattern.compile(regEx).matcher(data.toLowerCase()).find();
    }
}
