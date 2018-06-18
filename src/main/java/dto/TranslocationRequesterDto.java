package dto;

import java.time.LocalDate;

public class TranslocationRequesterDto {

    private String startDate;
    private String endDate;
    private boolean isForeign = false;
    private long vehicleId = 0;

    public TranslocationRequesterDto(String startDate, String endDate, boolean isForeign, long vehicleId) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.isForeign = isForeign;
        this.vehicleId = vehicleId;
    }

    public TranslocationRequesterDto() { }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public boolean isForeign() {
        return isForeign;
    }

    public void setForeign(boolean foreign) {
        isForeign = foreign;
    }

    public long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(long vehicleId) {
        this.vehicleId = vehicleId;
    }
}
