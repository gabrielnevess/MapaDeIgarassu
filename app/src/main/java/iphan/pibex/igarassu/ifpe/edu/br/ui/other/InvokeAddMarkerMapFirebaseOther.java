package iphan.pibex.igarassu.ifpe.edu.br.ui.other;


import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

import iphan.pibex.igarassu.ifpe.edu.br.ui.dialog.InvokeProgressDialog;
import iphan.pibex.igarassu.ifpe.edu.br.model.ConnectionFireBaseModel;

public class InvokeAddMarkerMapFirebaseOther implements OnMapReadyCallback {

    private Context context;

    public InvokeAddMarkerMapFirebaseOther(Context context) {
        this.context = context;
    }

    public void onAddMarker() {

        ConnectionFireBaseModel.getReferenceFirebase().onDisconnect();

        InvokeProgressDialog.progressDialogStart(context, "Aguarde", "Os pontos estão sendo carregados..."); //Exibindo janela de progresso
        ConnectionFireBaseModel.getReferenceFirebase()
                .child("locations")
                .addValueEventListener(new ValueEventListenerMarkerOther(this.context));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        onAddMarker();
    }

}

