package com.arjinmc.recyclerviewdecoration;

import java.util.regex.Pattern;

/**
 * RecyclerView ItemDecoration Util
 * Created by Eminem Lo on 2019-09-24.
 * email: arjinmc@hotmail.com
 */
final class RVItemDecorationUtil {

    /**
     * check is a color string like #xxxxxx or #xxxxxxxx
     *
     * @param colorStr
     * @return
     */
    public static boolean isColorString(String colorStr) {
        return Pattern.matches("^#([0-9a-fA-F]{6}||[0-9a-fA-F]{8})$", colorStr);
    }

    /**
     * check is a color string like #xxxxxx
     *
     * @param colorStr
     * @return
     */
    public static boolean isColorStringWithoutAlpha(String colorStr) {
        return Pattern.matches("^#[0-9a-fA-F]{6}$", colorStr);
    }
}
