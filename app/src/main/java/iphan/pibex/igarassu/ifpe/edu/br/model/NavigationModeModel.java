package iphan.pibex.igarassu.ifpe.edu.br.model;

public class NavigationModeModel {

    private String name;
    private double longitude;
    private double latitude;

    public NavigationModeModel(){ }

    public NavigationModeModel(String name, double longitude, double latitude) {
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

}
