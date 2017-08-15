package iphan.pibex.igarassu.ifpe.edu.br;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {


    public static SharedPreferences getPref(Context context){
        return context.getSharedPreferences(Constants.PREF_NAME,Context.MODE_PRIVATE);
    }

    public static void updateIntroStatus(Context context, boolean status){
        SharedPreferences.Editor editor = getPref(context).edit();
        editor.putBoolean("status", status);
        editor.commit();

    }

    public static boolean isIntroActivityShow(Context context){
        return getPref(context).getBoolean("status", false);
    }


}
