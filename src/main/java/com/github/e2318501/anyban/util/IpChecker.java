package com.github.e2318501.anyban.util;

import java.util.regex.Pattern;

public class IpChecker {
    public static boolean isIp(String input) {
        String regex = "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
        return Pattern.compile(regex).matcher(input).find();
    }
}
