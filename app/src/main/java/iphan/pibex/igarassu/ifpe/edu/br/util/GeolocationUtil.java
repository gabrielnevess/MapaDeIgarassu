package iphan.pibex.igarassu.ifpe.edu.br.util;


import android.content.Context;
import android.location.LocationManager;

public class GeolocationUtil {

    /**
     * Metodo para calcular a distancia entre duas coordenadas.
     * @param latitude1 double Latitude da coordenada 1.
     * @param longitude1 double Longitude da coordenada 1.
     * @param latitude2 double Latitude da coordenada 2.
     * @param longitude2 double Longitude da coordenada 2.
     * @return A distancia entre as duas coordenadas em metros.
     */
    public static int getDistance(double latitude1, double longitude1, double latitude2, double longitude2) {


        if ((latitude1 == latitude2) && (longitude1 == longitude2))
        {
            return 0;
        }

        return (int) (6378100 * Math.acos(Math.cos(Math.toRadians(latitude1)) * Math.cos(Math.toRadians(latitude2)) * Math.cos(Math.toRadians(longitude1)
                        - Math.toRadians(longitude2)) + ((Math.sin(Math.toRadians(latitude1)) * Math.sin(Math.toRadians(latitude2))))));

    }

    /**
     * Metodo para verificar se o GPS ('Local' em alguns aparelhos) esta ativo.
     * @param context Context O contexto da aplicacao.
     * @return true caso esteja ativo, false caso contrario.
     */
    public static boolean isGPSEnabled(Context context) {

        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER);

    }

}
