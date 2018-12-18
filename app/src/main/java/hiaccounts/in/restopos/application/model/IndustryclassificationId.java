package hiaccounts.in.restopos.application.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IndustryclassificationId {
    @SerializedName("locationId")
    @Expose
    private Object locationId;
    @SerializedName("useraccount_id")
    @Expose
    private Object useraccountId;
    @SerializedName("industryClassificationId")
    @Expose
    private Long industryClassificationId;
    @SerializedName("industryClassificationName")
    @Expose
    private String industryClassificationName;
    @SerializedName("industryClassificationDesc")
    @Expose
    private String industryClassificationDesc;
    @SerializedName("icflag")
    @Expose
    private boolean icflag;

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

    public Long getIndustryClassificationId() {
        return industryClassificationId;
    }

    public void setIndustryClassificationId(Long industryClassificationId) {
        this.industryClassificationId = industryClassificationId;
    }

    public String getIndustryClassificationName() {
        return industryClassificationName;
    }

    public void setIndustryClassificationName(String industryClassificationName) {
        this.industryClassificationName = industryClassificationName;
    }

    public String getIndustryClassificationDesc() {
        return industryClassificationDesc;
    }

    public void setIndustryClassificationDesc(String industryClassificationDesc) {
        this.industryClassificationDesc = industryClassificationDesc;
    }

    public boolean isIcflag() {
        return icflag;
    }

    public void setIcflag(boolean icflag) {
        this.icflag = icflag;
    }
}
