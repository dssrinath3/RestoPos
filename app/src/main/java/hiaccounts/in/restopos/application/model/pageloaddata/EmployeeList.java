package hiaccounts.in.restopos.application.model.pageloaddata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmployeeList {
    @SerializedName("selectedEmployee")
    @Expose
    public Boolean selectedEmployee;
    @SerializedName("status")
    @Expose
    public Object status;
    @SerializedName("status1")
    @Expose
    public String status1;
    @SerializedName("employeeAddr")
    @Expose
    public String employeeAddr;
    @SerializedName("employeePhone")
    @Expose
    public String employeePhone;
    @SerializedName("accountCode")
    @Expose
    public Object accountCode;
    @SerializedName("employeeName")
    @Expose
    public String employeeName;
    @SerializedName("employeeCode")
    @Expose
    public String employeeCode;
    @SerializedName("employeeDOB")
    @Expose
    public String employeeDOB;
    @SerializedName("employeeDOJ")
    @Expose
    public String employeeDOJ;
    @SerializedName("gender")
    @Expose
    public String gender;
    @SerializedName("employeeAccountMaster")
    @Expose
    public Object employeeAccountMaster;
    @SerializedName("employeeId")
    @Expose
    public Long employeeId;

    public Boolean getSelectedEmployee() {
        return selectedEmployee;
    }

    public void setSelectedEmployee(Boolean selectedEmployee) {
        this.selectedEmployee = selectedEmployee;
    }

    public Object getStatus() {
        return status;
    }

    public void setStatus(Object status) {
        this.status = status;
    }

    public String getStatus1() {
        return status1;
    }

    public void setStatus1(String status1) {
        this.status1 = status1;
    }

    public String getEmployeeAddr() {
        return employeeAddr;
    }

    public void setEmployeeAddr(String employeeAddr) {
        this.employeeAddr = employeeAddr;
    }

    public String getEmployeePhone() {
        return employeePhone;
    }

    public void setEmployeePhone(String employeePhone) {
        this.employeePhone = employeePhone;
    }

    public Object getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(Object accountCode) {
        this.accountCode = accountCode;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getEmployeeDOB() {
        return employeeDOB;
    }

    public void setEmployeeDOB(String employeeDOB) {
        this.employeeDOB = employeeDOB;
    }

    public String getEmployeeDOJ() {
        return employeeDOJ;
    }

    public void setEmployeeDOJ(String employeeDOJ) {
        this.employeeDOJ = employeeDOJ;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Object getEmployeeAccountMaster() {
        return employeeAccountMaster;
    }

    public void setEmployeeAccountMaster(Object employeeAccountMaster) {
        this.employeeAccountMaster = employeeAccountMaster;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }
}
