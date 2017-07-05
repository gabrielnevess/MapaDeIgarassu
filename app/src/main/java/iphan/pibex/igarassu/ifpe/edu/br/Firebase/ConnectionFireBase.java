package iphan.pibex.igarassu.ifpe.edu.br.Firebase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by gabri on 20/06/2017.
 */

public class ConnectionFireBase {

    private static DatabaseReference referenceFirebase;

    public static DatabaseReference getReferenceFirebase() {
        if (referenceFirebase == null) {
            referenceFirebase = FirebaseDatabase.getInstance().getReference();
        }
        return referenceFirebase;
    }

}
