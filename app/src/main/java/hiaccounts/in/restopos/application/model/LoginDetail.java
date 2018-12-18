package hiaccounts.in.restopos.application.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LoginDetail implements Serializable {
    @SerializedName("status")
    @Expose
    public String status;

    @SerializedName("redirecturli")
    @Expose
    public String redirecturli;

    @SerializedName("syncurl")
    @Expose
    public String syncurl;

    @SerializedName("cloud_app_url")
    @Expose
    public String cloud_app_url;

    @SerializedName("authToken")
    @Expose
    public String authToken;
    @SerializedName("companyRegNo")
    @Expose
    public String companyRegNo;
    @SerializedName("userId")
    @Expose
    public String userId;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRedirecturli() {
        return redirecturli;
    }

    public void setRedirecturli(String redirecturli) {
        this.redirecturli = redirecturli;
    }

    public String getSyncurl() {
        return syncurl;
    }

    public void setSyncurl(String syncurl) {
        this.syncurl = syncurl;
    }

    public String getCloud_app_url() {
        return cloud_app_url;
    }

    public void setCloud_app_url(String cloud_app_url) {
        this.cloud_app_url = cloud_app_url;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getCompanyRegNo() {
        return companyRegNo;
    }

    public void setCompanyRegNo(String companyRegNo) {
        this.companyRegNo = companyRegNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
