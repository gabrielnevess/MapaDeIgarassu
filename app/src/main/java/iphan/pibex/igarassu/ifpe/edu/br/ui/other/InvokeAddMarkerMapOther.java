package iphan.pibex.igarassu.ifpe.edu.br.ui.other;


import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.firebase.database.ChildEventListener;

import iphan.pibex.igarassu.ifpe.edu.br.model.GoogleMapsModel;
import iphan.pibex.igarassu.ifpe.edu.br.ui.dialog.InvokeProgressDialog;
import iphan.pibex.igarassu.ifpe.edu.br.model.ConnectionFireBaseModel;
import iphan.pibex.igarassu.ifpe.edu.br.util.DataBaseUtil;

public class InvokeAddMarkerMapOther implements OnMapReadyCallback {

    private Context context;
    private DataBaseUtil dataBaseUtil;

    public InvokeAddMarkerMapOther(Context context) {
        this.context = context;
        dataBaseUtil = new DataBaseUtil(context);
    }

    public void onAddMarkerFirebase() {
        
        InvokeProgressDialog.progressDialogStart(context, "Aguarde", "Os pontos estão sendo carregados..."); //Exibindo janela de progresso
        ConnectionFireBaseModel.getReferenceFirebase()
                .child("locations")
                .addValueEventListener(new ValueEventListenerMarkerOther(this.dataBaseUtil));
    }


    public void onAddMarkerSqlite(){
        GoogleMapsModel.getMap().clear(); /*Limpando o mapa*/
        this.dataBaseUtil.addMarkerSqlite();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        onAddMarkerFirebase();
    }


}

