package hiaccounts.in.restopos.application.realm_model.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Realm_TableOrders extends RealmObject {
    @PrimaryKey
    private Long id;
    @SerializedName("itemId")
    @Expose
    public Long itemId;
    @SerializedName("itemCode")
    @Expose
    public String itemCode;
    @SerializedName("itemName")
    @Expose
    public String itemName;
    @SerializedName("taxId")
    @Expose
    public Long taxId;
    @SerializedName("salesPrice")
    @Expose
    public Double salesPrice;
    @SerializedName("itemCategoryName")
    @Expose
    public String itemCategoryName;
    @SerializedName("unitPrice")
    @Expose
    public Double unitPrice;
    @SerializedName("totalPrice")
    @Expose
    public Double totalPrice;
    @SerializedName("itemCategoryId")
    @Expose
    public Long itemCategoryId;
    @SerializedName("qty")
    @Expose
    public Double qty;
    @SerializedName("brandId")
    @Expose
    public Long brandId;
    @SerializedName("discountAmt")
    @Expose
    public Double discountAmt;
    @SerializedName("imageLocation")
    @Expose
    public String imageLocation;
    @SerializedName("discountInPercent")
    @Expose
    public Boolean discountInPercent;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("itemDesc")
    @Expose
    public String itemDesc;
    @SerializedName("tableName")
    @Expose
    public String tableName;
    @SerializedName("kotStatus")
    @Expose
    public Boolean kotStatus;
    @SerializedName("orderDate")
    @Expose
    public String orderDate;
    @SerializedName("hsnCode")
    @Expose
    public String hsnCode;
    @SerializedName("taxName")
    @Expose
    public String taxName;
    @SerializedName("taxAmount")
    @Expose
    public Double taxAmount;

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

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public Boolean getKotStatus() {
        return kotStatus;
    }

    public void setKotStatus(Boolean kotStatus) {
        this.kotStatus = kotStatus;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Long getTaxId() {
        return taxId;
    }

    public void setTaxId(Long taxId) {
        this.taxId = taxId;
    }

    public Double getSalesPrice() {
        return salesPrice;
    }

    public void setSalesPrice(Double salesPrice) {
        this.salesPrice = salesPrice;
    }

    public String getItemCategoryName() {
        return itemCategoryName;
    }

    public void setItemCategoryName(String itemCategoryName) {
        this.itemCategoryName = itemCategoryName;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Long getItemCategoryId() {
        return itemCategoryId;
    }

    public void setItemCategoryId(Long itemCategoryId) {
        this.itemCategoryId = itemCategoryId;
    }

    public Double getQty() {
        return qty;
    }

    public void setQty(Double qty) {
        this.qty = qty;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public Double getDiscountAmt() {
        return discountAmt;
    }

    public void setDiscountAmt(Double discountAmt) {
        this.discountAmt = discountAmt;
    }

    public String getImageLocation() {
        return imageLocation;
    }

    public void setImageLocation(String imageLocation) {
        this.imageLocation = imageLocation;
    }

    public String getHsnCode() {
        return hsnCode;
    }

    public void setHsnCode(String hsnCode) {
        this.hsnCode = hsnCode;
    }

    public Boolean getDiscountInPercent() {
        return discountInPercent;
    }

    public void setDiscountInPercent(Boolean discountInPercent) {
        this.discountInPercent = discountInPercent;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }
}
