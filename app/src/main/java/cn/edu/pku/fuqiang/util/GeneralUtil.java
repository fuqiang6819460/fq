package pku.zhang.util;

import android.content.Context;

/**
 * Created by Zhang on 2015/6/19.
 */
public class GeneralUtil {
    public static int getDrawableId(Context paramContext, String paramString) {
        return paramContext.getResources().getIdentifier(paramString,
                "drawable", paramContext.getPackageName());
    }
}
