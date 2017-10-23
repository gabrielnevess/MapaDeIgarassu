package iphan.pibex.igarassu.ifpe.edu.br.ui.fragments;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;

import iphan.pibex.igarassu.ifpe.edu.br.constants.Constants;
import iphan.pibex.igarassu.ifpe.edu.br.model.GoogleMapsModel;
import iphan.pibex.igarassu.ifpe.edu.br.R;
import iphan.pibex.igarassu.ifpe.edu.br.util.SharedPreferencesUtil;

/**
 * Método de AlertDialog para escolha do tipo do mapa
 */
public class DialogTypeMapsFragment extends DialogFragment {
    private static AlertDialog.Builder builder;

    public static void alertDialog(final Context context) {
        builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.type_maps)
                .setItems(R.array.type_maps, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (id == 0) {
                            GoogleMapsModel.getMap().setMapType(Constants.MAP_TYPE_NORMAL);
                            SharedPreferencesUtil.setTypeMaps(context, Constants.MAP_TYPE_NORMAL);
                        } else if (id == 1) {
                            GoogleMapsModel.getMap().setMapType(Constants.MAP_TYPE_HYBRID);
                            SharedPreferencesUtil.setTypeMaps(context, Constants.MAP_TYPE_HYBRID);
                        }
                    }
                });
        builder.show();
    }

}
