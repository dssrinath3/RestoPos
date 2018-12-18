package hiaccounts.in.restopos.application.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CompanyInfoId implements Serializable {
    @SerializedName("companyId")
    @Expose
    private Long companyId;
    @SerializedName("companyName")
    @Expose
    private String companyName;
    @SerializedName("cfgFileName")
    @Expose
    private Object cfgFileName;
    @SerializedName("dbName")
    @Expose
    private Object dbName;
    @SerializedName("financialYear")
    @Expose
    private Object financialYear;
    @SerializedName("createdDate")
    @Expose
    private Object createdDate;
    @SerializedName("cmpStatus")
    @Expose
    private String cmpStatus;
    @SerializedName("locationId")
    @Expose
    private Object locationId;
    @SerializedName("useraccount_id")
    @Expose
    private Object useraccountId;
    @SerializedName("saasToken")
    @Expose
    private String saasToken;

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Object getCfgFileName() {
        return cfgFileName;
    }

    public void setCfgFileName(Object cfgFileName) {
        this.cfgFileName = cfgFileName;
    }

    public Object getDbName() {
        return dbName;
    }

    public void setDbName(Object dbName) {
        this.dbName = dbName;
    }

    public Object getFinancialYear() {
        return financialYear;
    }

    public void setFinancialYear(Object financialYear) {
        this.financialYear = financialYear;
    }

    public Object getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Object createdDate) {
        this.createdDate = createdDate;
    }

    public String getCmpStatus() {
        return cmpStatus;
    }

    public void setCmpStatus(String cmpStatus) {
        this.cmpStatus = cmpStatus;
    }

    public Object getLocationId() {
        return locationId;
    }

    public void setLocationId(Object locationId) {
        this.locationId = locationId;
    }

    public Object getUseraccountId() {
        return useraccountId;
    }

    public void setUseraccountId(Object useraccountId) {
        this.useraccountId = useraccountId;
    }

    public String getSaasToken() {
        return saasToken;
    }

    public void setSaasToken(String saasToken) {
        this.saasToken = saasToken;
    }
}
