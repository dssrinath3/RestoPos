package hiaccounts.in.restopos.application.realm_model.data;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Realm_CheckoutData extends RealmObject {
    @PrimaryKey
    private Long id;
    private Long invoiceNumber;
    private Long tokenNumber;
    private String checkOutData;
    private Boolean status;
    private String startDate;
    private String endDate;
    private Boolean flagReset;
    private String orderType;
    private String paymentType;
    private Long customerId;
    private String customerName;
    private Long taxId;
    private String taxName;
    private Double totalCheckoutAmount;
    private Double totalTaxAmount;
    private Double RoundingOfValue;
    private Double DiscountAmount;
    private String tableName;
    private String paymentCardNo;

    public Double getTotalTaxAmount() {
        return totalTaxAmount;
    }

    public void setTotalTaxAmount(Double totalTaxAmount) {
        this.totalTaxAmount = totalTaxAmount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(Long invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Long getTokenNumber() {
        return tokenNumber;
    }

    public void setTokenNumber(Long tokenNumber) {
        this.tokenNumber = tokenNumber;
    }

    public String getCheckOutData() {
        return checkOutData;
    }

    public void setCheckOutData(String checkOutData) {
        this.checkOutData = checkOutData;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Boolean getFlagReset() {
        return flagReset;
    }

    public void setFlagReset(Boolean flagReset) {
        this.flagReset = flagReset;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
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

    public Double getTotalCheckoutAmount() {
        return totalCheckoutAmount;
    }

    public void setTotalCheckoutAmount(Double totalCheckoutAmount) {
        this.totalCheckoutAmount = totalCheckoutAmount;
    }

    public Double getRoundingOfValue() {
        return RoundingOfValue;
    }

    public void setRoundingOfValue(Double roundingOfValue) {
        RoundingOfValue = roundingOfValue;
    }

    public Double getDiscountAmount() {
        return DiscountAmount;
    }

    public void setDiscountAmount(Double discountAmount) {
        DiscountAmount = discountAmount;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getPaymentCardNo() {
        return paymentCardNo;
    }

    public void setPaymentCardNo(String paymentCardNo) {
        this.paymentCardNo = paymentCardNo;
    }
}
