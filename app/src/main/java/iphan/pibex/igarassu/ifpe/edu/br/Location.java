package iphan.pibex.igarassu.ifpe.edu.br;

public class Location {
    private String name;
    private Double longitude;
    private Double latitude;
    private String endereco;

    public Location(String name, Double longitude, Double latitude, String endereco) {
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

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
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
