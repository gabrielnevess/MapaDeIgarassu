package iphan.pibex.igarassu.ifpe.edu.br.ui.other;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import iphan.pibex.igarassu.ifpe.edu.br.ui.dialog.InvokeProgressDialog;
import iphan.pibex.igarassu.ifpe.edu.br.model.ConnectionFireBaseModel;
import iphan.pibex.igarassu.ifpe.edu.br.model.LocationModel;
import iphan.pibex.igarassu.ifpe.edu.br.util.DataBaseUtil;
import iphan.pibex.igarassu.ifpe.edu.br.model.GoogleMapsModel;

import static java.lang.Integer.parseInt;

public class ValueEventListenerMarkerOther implements ValueEventListener {

    private DataBaseUtil dataBaseUtil;

    /**
     * Método de Listener(esse método ficará ouvindo um evento se por acaso ouver alguma mudança no firebase
     * por exemplo: a adição de um novo ponto).
     */

    public ValueEventListenerMarkerOther(DataBaseUtil dataBaseUtil) {
        this.dataBaseUtil = dataBaseUtil;
    }


    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {


        GoogleMapsModel.getMap().clear(); /*Limpando o mapa*/
        this.dataBaseUtil.dropTable();
        Iterable<DataSnapshot> dataSnapshots = dataSnapshot.getChildren();

        for(DataSnapshot dataSnapshot1 :  dataSnapshots){

            LocationModel local = dataSnapshot1.getValue(LocationModel.class);
            this.dataBaseUtil.insertLocation(local); //Inserindo pontos marcados no mapa para o banco local
            MarkerOther.marker(local.getName(), local.getLatitude(), local.getLongitude()); //Add marker

        }

        ConnectionFireBaseModel.getReferenceFirebase().onDisconnect();
        InvokeProgressDialog.progressDialogDismiss();

    }

    @Override
    public void onCancelled(DatabaseError error) { }

}
