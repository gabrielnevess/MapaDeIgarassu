package iphan.pibex.igarassu.ifpe.edu.br.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConnectionFireBaseModel {

    private static DatabaseReference referenceFirebase;

    public static DatabaseReference getReferenceFirebase() {
        if (referenceFirebase == null) {
            referenceFirebase = FirebaseDatabase.getInstance().getReference();
        }
        return referenceFirebase;
    }

}
