package iphan.pibex.igarassu.ifpe.edu.br.model;

import com.google.android.gms.maps.GoogleMap;

public class GoogleMapsModel {

    private static GoogleMap map;

    public static void setMap(GoogleMap map){
        GoogleMapsModel.map = map;
    }

    public static GoogleMap getMap(){
        return map;
    }

}
