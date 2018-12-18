package hiaccounts.in.restopos.application.model.pageloaddata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Companyid {
    @SerializedName("companyId")
    @Expose
    public Long companyId;
    @SerializedName("companyName")
    @Expose
    public String companyName;
    @SerializedName("cmpStatus")
    @Expose
    public String cmpStatus;
    @SerializedName("saasToken")
    @Expose
    public String saasToken;

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

    public String getCmpStatus() {
        return cmpStatus;
    }

    public void setCmpStatus(String cmpStatus) {
        this.cmpStatus = cmpStatus;
    }

    public String getSaasToken() {
        return saasToken;
    }

    public void setSaasToken(String saasToken) {
        this.saasToken = saasToken;
    }
}
