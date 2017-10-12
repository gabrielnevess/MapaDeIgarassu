package iphan.pibex.igarassu.ifpe.edu.br.Util;

import android.content.Context;
import android.content.SharedPreferences;

import iphan.pibex.igarassu.ifpe.edu.br.Constants.Constants;

public class SharedPrefUtil {

    public static SharedPreferences.Editor editor;

    /**
     * Método que pega o contexto da activity e retorna modo de armazenamento no xml.
     * @param context
     * @return
     */
    public static SharedPreferences getPref(Context context){
        return context.getSharedPreferences(Constants.PREF_NAME,Context.MODE_PRIVATE);
    }

    /**
     *
     * Método que armazenar status da activity de introdução, se o checkbox foi marcado ou não.
     * @param context
     * @param status
     */
    public static void updateIntroStatus(Context context, boolean status){
        editor = getPref(context).edit();
        editor.putBoolean("status", status);
        editor.commit();

    }

    /**
     * Método que retorna true ou false para o status da activity de introdução
     * @param context
     * @return
     */
    public static boolean isIntroActivityShow(Context context){
        return getPref(context).getBoolean("status", false);
    }


    public static void setTypeMaps(Context context, String type){
        editor = getPref(context).edit();
        editor.putString("typeMaps", type);
        editor.commit();
    }

    public static String getTypeMaps(Context context){
        return getPref(context).getString("typeMaps", "");
    }

}
