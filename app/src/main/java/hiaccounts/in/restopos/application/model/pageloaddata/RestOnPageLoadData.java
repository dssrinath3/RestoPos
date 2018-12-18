package hiaccounts.in.restopos.application.model.pageloaddata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RestOnPageLoadData {
    @SerializedName("itemCategorys")
    @Expose
    public List<ItemCategory> itemCategorys = null;
    @SerializedName("taxList")
    @Expose
    public List<TaxList> taxList = null;
    @SerializedName("employeeList")
    @Expose
    public List<EmployeeList> employeeList = null;
    @SerializedName("customers")
    @Expose
    public List<Customer> customers = null;
    @SerializedName("tableConfigDetails")
    @Expose
    public List<TableConfigDetail> tableConfigDetails = null;

    public List<ItemCategory> getItemCategorys() {
        return itemCategorys;
    }

    public void setItemCategorys(List<ItemCategory> itemCategorys) {
        this.itemCategorys = itemCategorys;
    }

    public List<TaxList> getTaxList() {
        return taxList;
    }

    public void setTaxList(List<TaxList> taxList) {
        this.taxList = taxList;
    }

    public List<EmployeeList> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<EmployeeList> employeeList) {
        this.employeeList = employeeList;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public List<TableConfigDetail> getTableConfigDetails() {
        return tableConfigDetails;
    }

    public void setTableConfigDetails(List<TableConfigDetail> tableConfigDetails) {
        this.tableConfigDetails = tableConfigDetails;
    }
}
