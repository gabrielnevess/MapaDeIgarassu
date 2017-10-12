package iphan.pibex.igarassu.ifpe.edu.br.Firebase;

import com.google.android.gms.maps.GoogleMap;

/**
 * Created by gabri on 11/10/2017.
 */

public class GoogleMaps {

    private static GoogleMap map;

    public static void setMap(GoogleMap map){
        GoogleMaps.map = map;
    }

    public static GoogleMap getMap(){
        return map;
    }

}
