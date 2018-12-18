package hiaccounts.in.restopos.application.model.pageloaddata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemCategory {
    @SerializedName("itemCategoryId")
    @Expose
    public Long itemCategoryId;
    @SerializedName("itemCategoryCode")
    @Expose
    public String itemCategoryCode;
    @SerializedName("itemCategoryName")
    @Expose
    public String itemCategoryName;
    @SerializedName("itemCategoryDesc")
    @Expose
    public String itemCategoryDesc;
    @SerializedName("companyid")
    @Expose
    public Companyid companyid;

    public Long getItemCategoryId() {
        return itemCategoryId;
    }

    public void setItemCategoryId(Long itemCategoryId) {
        this.itemCategoryId = itemCategoryId;
    }

    public String getItemCategoryCode() {
        return itemCategoryCode;
    }

    public void setItemCategoryCode(String itemCategoryCode) {
        this.itemCategoryCode = itemCategoryCode;
    }

    public String getItemCategoryName() {
        return itemCategoryName;
    }

    public void setItemCategoryName(String itemCategoryName) {
        this.itemCategoryName = itemCategoryName;
    }

    public String getItemCategoryDesc() {
        return itemCategoryDesc;
    }

    public void setItemCategoryDesc(String itemCategoryDesc) {
        this.itemCategoryDesc = itemCategoryDesc;
    }

    public Companyid getCompanyid() {
        return companyid;
    }

    public void setCompanyid(Companyid companyid) {
        this.companyid = companyid;
    }
}
