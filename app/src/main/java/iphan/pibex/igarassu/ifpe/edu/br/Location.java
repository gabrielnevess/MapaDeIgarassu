package iphan.pibex.igarassu.ifpe.edu.br;

public class Location {
    private String name;
    private double longitude;
    private double latitude;
    private String endereco;

    public Location() {}

    public Location(String name, double longitude, double latitude, String endereco) {
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.endereco = endereco;
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

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
}
