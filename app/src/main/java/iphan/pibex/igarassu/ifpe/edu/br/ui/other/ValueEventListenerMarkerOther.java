package iphan.pibex.igarassu.ifpe.edu.br.ui.other;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import iphan.pibex.igarassu.ifpe.edu.br.ui.dialog.InvokeProgressDialog;
import iphan.pibex.igarassu.ifpe.edu.br.model.ConnectionFireBaseModel;
import iphan.pibex.igarassu.ifpe.edu.br.model.LocationModel;
import iphan.pibex.igarassu.ifpe.edu.br.util.DataBaseUtil;
import iphan.pibex.igarassu.ifpe.edu.br.model.GoogleMapsModel;

import static java.lang.Integer.parseInt;

public class ValueEventListenerMarkerOther implements ChildEventListener {

    private DataBaseUtil dataBaseUtil;

    /**
     * Método de Listener(esse método ficará ouvindo um evento se por acaso ouver alguma mudança no firebase
     * por exemplo: a adição de um novo ponto).
     */

    public ValueEventListenerMarkerOther(DataBaseUtil dataBaseUtil) {
        this.dataBaseUtil = dataBaseUtil;
        GoogleMapsModel.getMap().clear(); /*Limpando o mapa*/
        //this.dataBaseUtil.dropTable(); //drop table
    }


    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) { //inserindo marcadores na tabela Location

        Log.e("", "ID INSERIDO: " + dataSnapshot.getKey());
        LocationModel local = dataSnapshot.getValue(LocationModel.class);
        this.dataBaseUtil.insertLocation(local); //Inserindo pontos marcados no mapa para o banco local
        MarkerOther.marker(local.getName(), local.getLatitude(), local.getLongitude()); //Add marker
        InvokeProgressDialog.progressDialogDismiss();

    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) { //atualizando marcadores da tabela Location

        Log.e("", "ID ATUALIZADO: " + dataSnapshot.getKey());
        LocationModel local = dataSnapshot.getValue(LocationModel.class);
        this.dataBaseUtil.updateLocation(local); //Inserindo pontos marcados no mapa para o banco local
        MarkerOther.marker(local.getName(), local.getLatitude(), local.getLongitude()); //Add marker
        InvokeProgressDialog.progressDialogDismiss();
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {

        LocationModel local = dataSnapshot.getValue(LocationModel.class);
        this.dataBaseUtil.deleteLocation(local.getId());

    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
    }

    @Override
    public void onCancelled(DatabaseError error) {
    }

}
