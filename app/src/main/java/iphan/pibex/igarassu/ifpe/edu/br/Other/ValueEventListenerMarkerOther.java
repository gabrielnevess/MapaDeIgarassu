package iphan.pibex.igarassu.ifpe.edu.br.Other;

import android.content.Context;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.sql.SQLException;

import iphan.pibex.igarassu.ifpe.edu.br.Dialog.InvokeProgressDialog;
import iphan.pibex.igarassu.ifpe.edu.br.Fragments.DialogTypeMapsFragment;
import iphan.pibex.igarassu.ifpe.edu.br.Model.ConnectionFireBaseModel;
import iphan.pibex.igarassu.ifpe.edu.br.Model.LocationModel;
import iphan.pibex.igarassu.ifpe.edu.br.Util.DataBaseUtil;
import iphan.pibex.igarassu.ifpe.edu.br.Model.GoogleMapsModel;

public class ValueEventListenerMarkerOther implements ValueEventListener {

    private Context context;

    /**
     * Método de Listener(esse método ficará ouvindo um evento se por acaso ouver alguma mudança no firebase
     * por exemplo: a adição de um novo ponto).
     *
     * @param context
     */
    public ValueEventListenerMarkerOther(Context context) {
        this.context = context;
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {

        Iterable<DataSnapshot> dataSnapshots = dataSnapshot.getChildren();
        GoogleMapsModel.getMap().clear(); /*Limpando o mapa*/

        DataBaseUtil dataBaseUtil = new DataBaseUtil(this.context);
        dataBaseUtil.dropTable(); //drop table

        for (DataSnapshot dataSnapshot1 : dataSnapshots) { /*Inserindo pontos ao mapa*/

            final LocationModel local = dataSnapshot1.getValue(LocationModel.class);
            dataBaseUtil.insertLocation(local); /*Inserindo pontos marcados no mapa para o banco local*/
            MarkerOther.marker(local.getName(), local.getLatitude(), local.getLongitude()); //Add marker

        }

        InvokeProgressDialog.progressDialogDismiss(); //Fechando progress Dialog
        ConnectionFireBaseModel.getReferenceFirebase().onDisconnect(); //Fechando conexão do firebase.

    }

    @Override
    public void onCancelled(DatabaseError error) {
    }

}
