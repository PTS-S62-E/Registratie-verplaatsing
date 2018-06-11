package dto;

import entities.Translocation;
import util.LocalDateTimeParser;

public class TrackingTranslocationDto {
    private String serialNumber;
    private double lat;
    private double lon;
    private String dateTime;
    private String countryCode;

    public TrackingTranslocationDto() { }

    public TrackingTranslocationDto(Translocation translocation) {
        this.serialNumber = translocation.getSerialNumber();
        this.lat = translocation.getLat();
        this.lon = translocation.getLon();
        this.dateTime = LocalDateTimeParser.localDateTimeToString(translocation.getTimestamp());
        this.countryCode = translocation.getCountryCode();
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
}
