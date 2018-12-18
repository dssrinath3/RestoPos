package hiaccounts.in.restopos.application.realm_model.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Realm_Tax extends RealmObject {
    @PrimaryKey
    private Long id;
    @SerializedName("taxId")
    @Expose
    public Long taxId;
    @SerializedName("taxName")
    @Expose
    public String taxName;
    @SerializedName("hsnCode")
    @Expose
    public String hsnCode;
    @SerializedName("taxAmount")
    @Expose
    public Double taxAmount;
    public boolean checked;
    public Realm_Tax() {

    }

    public String getHsnCode() {
        return hsnCode;
    }

    public void setHsnCode(String hsnCode) {
        this.hsnCode = hsnCode;
    }

    public Realm_Tax(Long taxId, String gst, Long l, boolean checked) {

    }

    public Realm_Tax(Long taxId, String taxName, Double taxAmount, boolean checked) {
        this.taxId = taxId;
        this.taxName = taxName;
        this.taxAmount = taxAmount;
        this.checked = checked;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTaxId() {
        return taxId;
    }

    public void setTaxId(Long taxId) {
        this.taxId = taxId;
    }

    public String getTaxName() {
        return taxName;
    }

    public void setTaxName(String taxName) {
        this.taxName = taxName;
    }

    public Double getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(Double taxAmount) {
        this.taxAmount = taxAmount;
    }
}
