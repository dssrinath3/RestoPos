package hiaccounts.in.restopos.application.model.pageloaddata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TaxList {
    @SerializedName("taxid")
    @Expose
    public Long taxid;
    @SerializedName("taxString")
    @Expose
    public String taxString;
    @SerializedName("taxGroup")
    @Expose
    public Object taxGroup;
    @SerializedName("taxCode")
    @Expose
    public Object taxCode;
    @SerializedName("taxName")
    @Expose
    public Object taxName;
    @SerializedName("taxPercentage")
    @Expose
    public Double taxPercentage;
    @SerializedName("flag")
    @Expose
    public Boolean flag;
    @SerializedName("taxDescription")
    @Expose
    public Object taxDescription;

    public Long getTaxid() {
        return taxid;
    }

    public void setTaxid(Long taxid) {
        this.taxid = taxid;
    }

    public String getTaxString() {
        return taxString;
    }

    public void setTaxString(String taxString) {
        this.taxString = taxString;
    }

    public Object getTaxGroup() {
        return taxGroup;
    }

    public void setTaxGroup(Object taxGroup) {
        this.taxGroup = taxGroup;
    }

    public Object getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(Object taxCode) {
        this.taxCode = taxCode;
    }

    public Object getTaxName() {
        return taxName;
    }

    public void setTaxName(Object taxName) {
        this.taxName = taxName;
    }

    public Double getTaxPercentage() {
        return taxPercentage;
    }

    public void setTaxPercentage(Double taxPercentage) {
        this.taxPercentage = taxPercentage;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public Object getTaxDescription() {
        return taxDescription;
    }

    public void setTaxDescription(Object taxDescription) {
        this.taxDescription = taxDescription;
    }
}
