package iphan.pibex.igarassu.ifpe.edu.br.Model;

public class LocationModel {
    private String name;
    private double longitude;
    private double latitude;
    private String address;
    private String description;

    public LocationModel() {
    }

    public LocationModel(String name, double longitude, double latitude, String address, String description) {
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
        this.description = description;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
