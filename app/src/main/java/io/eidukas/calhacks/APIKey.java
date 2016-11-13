package io.eidukas.calhacks;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.Nullable;

import java.io.InputStream;

public class APIKey {

    public static String getIBMKey(Context context){
        return readFromResource(context, R.raw.ibm);
    }

    public static String getRecipeKey(Context context){
        return readFromResource(context, R.raw.recipe);
    }

    public static String getModelKey(Context context){
        return readFromResource(context, R.raw.model);
    }

    @Nullable
    private static String readFromResource(Context context, int resource){
        try {
            Resources res = context.getResources();
            InputStream in_s = res.openRawResource(resource);
            byte[] b = new byte[in_s.available()];
            in_s.read(b);
            return new String(b);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
