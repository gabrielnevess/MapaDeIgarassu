package iphan.pibex.igarassu.ifpe.edu.br.util;


import android.content.Context;
import android.location.LocationManager;

public class GeolocationUtil {


    private static final double deg2rad(double deg)
    {
        return (deg * Math.PI / 180.0);
    }

    private static final double rad2deg(double rad)
    {
        return (rad * 180 / Math.PI);
    }
    /**
     * Metodo para calcular a distancia entre duas coordenadas.
     * @param latitude1 double Latitude da coordenada 1.
     * @param longitude1 double Longitude da coordenada 1.
     * @param latitude2 double Latitude da coordenada 2.
     * @param longitude2 double Longitude da coordenada 2.
     * @return A distancia entre as duas coordenadas em metros.
     */
    public static double getDistance(double latitude1, double longitude1, double latitude2, double longitude2, char unit) {


        if ((latitude1 == latitude2) && (longitude1 == longitude2))
        {
            return 0;
        }

        double theta = longitude1 - longitude2;
        double dist = Math.sin(deg2rad(latitude1)) * Math.sin(deg2rad(latitude2)) +
                Math.cos(deg2rad(latitude1)) * Math.cos(deg2rad(latitude2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;

        if (unit == 'K') {
            dist = dist * 1.609344;
        }
        else if (unit == 'N') {
            dist = dist * 0.8684;
        }

        return dist;

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
