package hiaccounts.in.restopos.application.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BusinesstypeId implements Serializable {
    @SerializedName("industryClassificationId")
    @Expose
    private IndustryClassificationId industryClassificationId;
    @SerializedName("businessTypeId")
    @Expose
    private Long businessTypeId;
    @SerializedName("businessTypeName")
    @Expose
    private String businessTypeName;
    @SerializedName("businessTypeDesc")
    @Expose
    private String businessTypeDesc;
    @SerializedName("btflag")
    @Expose
    private boolean btflag;

    public IndustryClassificationId getIndustryClassificationId() {
        return industryClassificationId;
    }

    public void setIndustryClassificationId(IndustryClassificationId industryClassificationId) {
        this.industryClassificationId = industryClassificationId;
    }

    public Long getBusinessTypeId() {
        return businessTypeId;
    }

    public void setBusinessTypeId(Long businessTypeId) {
        this.businessTypeId = businessTypeId;
    }

    public String getBusinessTypeName() {
        return businessTypeName;
    }

    public void setBusinessTypeName(String businessTypeName) {
        this.businessTypeName = businessTypeName;
    }

    public String getBusinessTypeDesc() {
        return businessTypeDesc;
    }

    public void setBusinessTypeDesc(String businessTypeDesc) {
        this.businessTypeDesc = businessTypeDesc;
    }

    public boolean isBtflag() {
        return btflag;
    }

    public void setBtflag(boolean btflag) {
        this.btflag = btflag;
    }
}
