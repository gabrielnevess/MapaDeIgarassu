package iphan.pibex.igarassu.ifpe.edu.br;

import com.google.android.gms.maps.GoogleMap;

public class GoogleMaps {

    private static GoogleMap map;

    public static void setMap(GoogleMap map){
        GoogleMaps.map = map;
    }

    public static GoogleMap getMap(){
        return map;
    }

}
