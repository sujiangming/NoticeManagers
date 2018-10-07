package com.jdry.noticemanagers.utils;


import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * dp、sp 转换为 px 的工具类
 *
 * @author fxsky 2012.11.12
 */
public class PhoneNumberUtil {
    public static Pattern p = Pattern.compile("(\\d{3})(\\d{4})(\\d{4})");

    public static String format(String phoneNumber) {

        if (phoneNumber.indexOf("-") > 0) {
            return phoneNumber;
        }
        if (phoneNumber.equals("NaN")) {
            return phoneNumber;
        }

        boolean isMobile = isMobileNO(phoneNumber);

        if (isMobile) {
            Matcher m = p.matcher(phoneNumber);
            String str = "";
            while (m.find()) {
                str += m.group(1) + "-" + m.group(2) + "-" + m.group(3);
            }
            return str;
        }

        return phoneNumber;
    }

    public static String unformat(String phoneNumber) {
        if (null == phoneNumber) {
            return null;
        }

        String str = phoneNumber.replace("-", "");
        return str;
    }

    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobiles) {
        /*
        移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
		联通：130、131、132、152、155、156、185、186
		电信：133、153、180、189、（1349卫通）
		总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
		*/
        if (mobiles.length() < 11) {
            return false;
        }
        String telRegex = "[1][3456789]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles)) return false;
        else return mobiles.matches(telRegex);
    }
}