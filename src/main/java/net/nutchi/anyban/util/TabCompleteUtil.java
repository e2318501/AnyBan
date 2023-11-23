package net.nutchi.anyban.util;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class TabCompleteUtil {
    public static List<String> filter(Collection<String> list, String arg) {
        return list.stream().filter(n -> n.toLowerCase().startsWith(arg.toLowerCase())).collect(Collectors.toList());
    }
}
