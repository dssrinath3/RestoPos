package hiaccounts.in.restopos.application.realm_model.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Realm_Customer extends RealmObject implements Serializable {
    @PrimaryKey
    private Long id;
    @SerializedName("customerId")
    @Expose
    public Long customerId;
    @SerializedName("customerName")
    @Expose
    public String customerName;
    @SerializedName("emailId")
    @Expose
    public String emailId;
    @SerializedName("phoneNumber")
    @Expose
    public Long phoneNumber;
    @SerializedName("customerAddress")
    @Expose
    public String customerAddress;
    @SerializedName("customerDate")
    @Expose
    public String customerDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerDate() {
        return customerDate;
    }

    public void setCustomerDate(String customerDate) {
        this.customerDate = customerDate;
    }
}
