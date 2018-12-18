package hiaccounts.in.restopos.application.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class State implements Serializable {
    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("stateCode")
    @Expose
    private String stateCode;
    @SerializedName("stateName")
    @Expose
    private String stateName;
    @SerializedName("vehicleSeries")
    @Expose
    private String vehicleSeries;
    @SerializedName("countryId")
    @Expose
    private CountryId countryId;
    @SerializedName("status")
    @Expose
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getVehicleSeries() {
        return vehicleSeries;
    }

    public void setVehicleSeries(String vehicleSeries) {
        this.vehicleSeries = vehicleSeries;
    }

    public CountryId getCountryId() {
        return countryId;
    }

    public void setCountryId(CountryId countryId) {
        this.countryId = countryId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
