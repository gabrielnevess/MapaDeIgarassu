package iphan.pibex.igarassu.ifpe.edu.br.Fragments;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;

import com.google.android.gms.maps.GoogleMap;

import iphan.pibex.igarassu.ifpe.edu.br.Constants.Constants;
import iphan.pibex.igarassu.ifpe.edu.br.Model.GoogleMapsModel;
import iphan.pibex.igarassu.ifpe.edu.br.R;
import iphan.pibex.igarassu.ifpe.edu.br.Util.SharedPrefUtil;

public class DialogTypeMapsFragment extends DialogFragment {
    public static void alertDialog(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.type_maps)
                .setItems(R.array.type_maps, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            GoogleMapsModel.getMap().setMapType(GoogleMap.MAP_TYPE_NORMAL);
                            SharedPrefUtil.setTypeMaps(context, Constants.NORMAL);
                        } else if (which == 1) {
                            GoogleMapsModel.getMap().setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                            SharedPrefUtil.setTypeMaps(context, Constants.SATELLITE);

                        } else if (which == 2) {
                            GoogleMapsModel.getMap().setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                            SharedPrefUtil.setTypeMaps(context, Constants.TERRAIN);
                        }
                    }
                });
        builder.show();
    }
}
