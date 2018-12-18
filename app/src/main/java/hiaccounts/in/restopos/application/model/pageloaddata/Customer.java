package hiaccounts.in.restopos.application.model.pageloaddata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Customer {
    @SerializedName("customerId")
    @Expose
    public Long customerId;
    @SerializedName("customerName")
    @Expose
    public String customerName;
    @SerializedName("customerNumber")
    @Expose
    public String customerNumber;
    @SerializedName("customerCode")
    @Expose
    public Object customerCode;
    @SerializedName("customerEmail")
    @Expose
    public String customerEmail;
    @SerializedName("customerContact")
    @Expose
    public Object customerContact;
    @SerializedName("gstIn")
    @Expose
    public String gstIn;
    @SerializedName("state")
    @Expose
    public String state;
    @SerializedName("bankName")
    @Expose
    public String bankName;
    @SerializedName("accountNo")
    @Expose
    public String accountNo;
    @SerializedName("iFSCCode")
    @Expose
    public String iFSCCode;
    @SerializedName("branchName")
    @Expose
    public String branchName;
    @SerializedName("personIncharge")
    @Expose
    public String personIncharge;
    @SerializedName("countryId")
    @Expose
    public String countryId;
    @SerializedName("currencyId")
    @Expose
    public String currencyId;
    @SerializedName("panNO")
    @Expose
    public String panNO;
    @SerializedName("website")
    @Expose
    public String website;
    @SerializedName("billingAddress")
    @Expose
    public Object billingAddress;
    @SerializedName("loyaltyPoints")
    @Expose
    public Double loyaltyPoints;
    @SerializedName("totalLoyaltyPoints")
    @Expose
    public Double totalLoyaltyPoints;
    @SerializedName("phoneNumber")
    @Expose
    public String phoneNumber;
    @SerializedName("address")
    @Expose
    public String address;
    @SerializedName("contactPerson")
    @Expose
    public Object contactPerson;
    @SerializedName("companyNumber")
    @Expose
    public Object companyNumber;
    @SerializedName("faxTelex")
    @Expose
    public Object faxTelex;
    @SerializedName("creditedLimit")
    @Expose
    public String creditedLimit;
    @SerializedName("terms")
    @Expose
    public Object terms;
    @SerializedName("status")
    @Expose
    public Object status;
    @SerializedName("pincode")
    @Expose
    public String pincode;
    @SerializedName("vehicleSeries")
    @Expose
    public String vehicleSeries;

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

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public Object getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(Object customerCode) {
        this.customerCode = customerCode;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public Object getCustomerContact() {
        return customerContact;
    }

    public void setCustomerContact(Object customerContact) {
        this.customerContact = customerContact;
    }

    public String getGstIn() {
        return gstIn;
    }

    public void setGstIn(String gstIn) {
        this.gstIn = gstIn;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getiFSCCode() {
        return iFSCCode;
    }

    public void setiFSCCode(String iFSCCode) {
        this.iFSCCode = iFSCCode;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getPersonIncharge() {
        return personIncharge;
    }

    public void setPersonIncharge(String personIncharge) {
        this.personIncharge = personIncharge;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public String getPanNO() {
        return panNO;
    }

    public void setPanNO(String panNO) {
        this.panNO = panNO;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Object getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(Object billingAddress) {
        this.billingAddress = billingAddress;
    }

    public Double getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(Double loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    public Double getTotalLoyaltyPoints() {
        return totalLoyaltyPoints;
    }

    public void setTotalLoyaltyPoints(Double totalLoyaltyPoints) {
        this.totalLoyaltyPoints = totalLoyaltyPoints;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Object getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(Object contactPerson) {
        this.contactPerson = contactPerson;
    }

    public Object getCompanyNumber() {
        return companyNumber;
    }

    public void setCompanyNumber(Object companyNumber) {
        this.companyNumber = companyNumber;
    }

    public Object getFaxTelex() {
        return faxTelex;
    }

    public void setFaxTelex(Object faxTelex) {
        this.faxTelex = faxTelex;
    }

    public String getCreditedLimit() {
        return creditedLimit;
    }

    public void setCreditedLimit(String creditedLimit) {
        this.creditedLimit = creditedLimit;
    }

    public Object getTerms() {
        return terms;
    }

    public void setTerms(Object terms) {
        this.terms = terms;
    }

    public Object getStatus() {
        return status;
    }

    public void setStatus(Object status) {
        this.status = status;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getVehicleSeries() {
        return vehicleSeries;
    }

    public void setVehicleSeries(String vehicleSeries) {
        this.vehicleSeries = vehicleSeries;
    }
}
