package hiaccounts.in.restopos.application.realm_model.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Realm_ItemList extends RealmObject {
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
    @SerializedName("outputTaxId")
    @Expose
    public Long outputTaxId;
    @SerializedName("stock")
    @Expose
    public Double stock;
    @SerializedName("discountAmountRpercent")
    @Expose
    public Double discountAmountRpercent;
    @SerializedName("isDiscountInPercent")
    @Expose
    public Boolean isDiscountInPercent;
    @SerializedName("salesPrice")
    @Expose
    public Double salesPrice;
    @SerializedName("purchasePrice")
    @Expose
    public Double purchasePrice;
    @SerializedName("itemTypeName")
    @Expose
    public String itemTypeName;
    @SerializedName("itemCategoryName")
    @Expose
    public String itemCategoryName;
    @SerializedName("attributeJSON")
    @Expose
    public String attributeJSON;
    @SerializedName("unitPrice")
    @Expose
    public Double unitPrice;
    @SerializedName("unitPriceIn")
    @Expose
    public Double unitPriceIn;
    @SerializedName("serializableStatus")
    @Expose
    public String serializableStatus;
    @SerializedName("itemCategoryId")
    @Expose
    public Long itemCategoryId;
    @SerializedName("itemTypeId")
    @Expose
    public Long itemTypeId;
    @SerializedName("mscid")
    @Expose
    public Long mscid;
    @SerializedName("inputTaxId")
    @Expose
    public Long inputTaxId;
    @SerializedName("qty")
    @Expose
    public Double qty;
    @SerializedName("brandId")
    @Expose
    public Long brandId;
    @SerializedName("discountAmt")
    @Expose
    public Double discountAmt;
    @SerializedName("taxPer")
    @Expose
    public Double taxPer;
    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("productionName")
    @Expose
    public String productionName;
    @SerializedName("serviceDescription")
    @Expose
    public String serviceDescription;
    @SerializedName("taxid")
    @Expose
    public Long taxid;
    @SerializedName("imageLocation")
    @Expose
    public String imageLocation;
    @SerializedName("cess")
    @Expose
    public Double cess;
    @SerializedName("hsnCode")
    @Expose
    public String hsnCode;
    @SerializedName("taxName")
    @Expose
    public String taxName;
    @SerializedName("taxAmount")
    @Expose
    public Double taxAmount;
    @SerializedName("uomName")
    @Expose
    public String uomName;
    @SerializedName("inclusiveJSON")
    @Expose
    public String inclusiveJSON;
    @SerializedName("convertedQuantity")
    @Expose
    public Double convertedQuantity;
    @SerializedName("barCode")
    @Expose
    public Long barCode;
    @SerializedName("fileName")
    @Expose
    public String fileName;
    @SerializedName("discountInPercent")
    @Expose
    public Boolean discountInPercent;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("itemDesc")
    @Expose
    public String itemDesc;

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

    public Long getOutputTaxId() {
        return outputTaxId;
    }

    public void setOutputTaxId(Long outputTaxId) {
        this.outputTaxId = outputTaxId;
    }

    public Double getStock() {
        return stock;
    }

    public void setStock(Double stock) {
        this.stock = stock;
    }

    public Double getDiscountAmountRpercent() {
        return discountAmountRpercent;
    }

    public void setDiscountAmountRpercent(Double discountAmountRpercent) {
        this.discountAmountRpercent = discountAmountRpercent;
    }

    public Boolean getDiscountInPercent() {
        return isDiscountInPercent;
    }

    public void setDiscountInPercent(Boolean discountInPercent) {
        isDiscountInPercent = discountInPercent;
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

    public Double getSalesPrice() {
        return salesPrice;
    }

    public void setSalesPrice(Double salesPrice) {
        this.salesPrice = salesPrice;
    }

    public Double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public String getItemTypeName() {
        return itemTypeName;
    }

    public void setItemTypeName(String itemTypeName) {
        this.itemTypeName = itemTypeName;
    }

    public String getItemCategoryName() {
        return itemCategoryName;
    }

    public void setItemCategoryName(String itemCategoryName) {
        this.itemCategoryName = itemCategoryName;
    }

    public String getAttributeJSON() {
        return attributeJSON;
    }

    public void setAttributeJSON(String attributeJSON) {
        this.attributeJSON = attributeJSON;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Double getUnitPriceIn() {
        return unitPriceIn;
    }

    public void setUnitPriceIn(Double unitPriceIn) {
        this.unitPriceIn = unitPriceIn;
    }

    public String getSerializableStatus() {
        return serializableStatus;
    }

    public void setSerializableStatus(String serializableStatus) {
        this.serializableStatus = serializableStatus;
    }

    public Long getItemCategoryId() {
        return itemCategoryId;
    }

    public void setItemCategoryId(Long itemCategoryId) {
        this.itemCategoryId = itemCategoryId;
    }

    public Long getItemTypeId() {
        return itemTypeId;
    }

    public void setItemTypeId(Long itemTypeId) {
        this.itemTypeId = itemTypeId;
    }

    public Long getMscid() {
        return mscid;
    }

    public void setMscid(Long mscid) {
        this.mscid = mscid;
    }

    public Long getInputTaxId() {
        return inputTaxId;
    }

    public void setInputTaxId(Long inputTaxId) {
        this.inputTaxId = inputTaxId;
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

    public Double getTaxPer() {
        return taxPer;
    }

    public void setTaxPer(Double taxPer) {
        this.taxPer = taxPer;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProductionName() {
        return productionName;
    }

    public void setProductionName(String productionName) {
        this.productionName = productionName;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

    public Long getTaxid() {
        return taxid;
    }

    public void setTaxid(Long taxid) {
        this.taxid = taxid;
    }

    public String getImageLocation() {
        return imageLocation;
    }

    public void setImageLocation(String imageLocation) {
        this.imageLocation = imageLocation;
    }

    public Double getCess() {
        return cess;
    }

    public void setCess(Double cess) {
        this.cess = cess;
    }

    public String getHsnCode() {
        return hsnCode;
    }

    public void setHsnCode(String hsnCode) {
        this.hsnCode = hsnCode;
    }

    public String getUomName() {
        return uomName;
    }

    public void setUomName(String uomName) {
        this.uomName = uomName;
    }

    public String getInclusiveJSON() {
        return inclusiveJSON;
    }

    public void setInclusiveJSON(String inclusiveJSON) {
        this.inclusiveJSON = inclusiveJSON;
    }

    public Double getConvertedQuantity() {
        return convertedQuantity;
    }

    public void setConvertedQuantity(Double convertedQuantity) {
        this.convertedQuantity = convertedQuantity;
    }

    public Long getBarCode() {
        return barCode;
    }

    public void setBarCode(Long barCode) {
        this.barCode = barCode;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return itemName;
    }
}
