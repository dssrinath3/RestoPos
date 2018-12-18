package hiaccounts.in.restopos.application.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CompanyData implements Serializable {
    @SerializedName("companyId")
    @Expose
    private Long companyId;
    @SerializedName("lang")
    @Expose
    private String lang;
    @SerializedName("countryId")
    @Expose
    private CountryId countryId;
    @SerializedName("registeredCompany")
    @Expose
    private boolean registeredCompany;
    @SerializedName("registerNo")
    @Expose
    private String registerNo;
    @SerializedName("company_name")
    @Expose
    private String companyName;
    @SerializedName("company_no")
    @Expose
    private String companyNo;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("fax")
    @Expose
    private String fax;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("hiConnectStatus")
    @Expose
    private Object hiConnectStatus;
    @SerializedName("hiConnectCompnyRegNo")
    @Expose
    private Object hiConnectCompnyRegNo;
    @SerializedName("web")
    @Expose
    private String web;
    @SerializedName("yearclosing")
    @Expose
    private String yearclosing;
    @SerializedName("startperiod")
    @Expose
    private String startperiod;
    @SerializedName("closingperiod")
    @Expose
    private String closingperiod;
    @SerializedName("startyear")
    @Expose
    private String startyear;
    @SerializedName("endyear")
    @Expose
    private String endyear;
    @SerializedName("closingMethod")
    @Expose
    private String closingMethod;
    @SerializedName("incdate")
    @Expose
    private String incdate;
    @SerializedName("currencyId")
    @Expose
    private CurrencyId currencyId;
    @SerializedName("accCodeSep")
    @Expose
    private Object accCodeSep;
    @SerializedName("gstId")
    @Expose
    private long gstId;
    @SerializedName("industryclassificationId")
    @Expose
    private IndustryclassificationId industryclassificationId;
    @SerializedName("businesstypeId")
    @Expose
    private BusinesstypeId businesstypeId;
    @SerializedName("logo")
    @Expose
    private Object logo;
    @SerializedName("bufferDays")
    @Expose
    private long bufferDays;
    @SerializedName("createOrSelectAM")
    @Expose
    private String createOrSelectAM;
    @SerializedName("companyInfoId")
    @Expose
    private CompanyInfoId companyInfoId;
    @SerializedName("lan")
    @Expose
    private Object lan;
    @SerializedName("complitionstatus")
    @Expose
    private String complitionstatus;
    @SerializedName("imageFile")
    @Expose
    private Object imageFile;
    @SerializedName("locationId")
    @Expose
    private Object locationId;
    @SerializedName("useraccount_id")
    @Expose
    private Object useraccountId;
    @SerializedName("fileName")
    @Expose
    private String fileName;
    @SerializedName("imageBlob")
    @Expose
    private Object imageBlob;
    @SerializedName("panNumber")
    @Expose
    private String panNumber;
    @SerializedName("gstregisteredDate")
    @Expose
    private String gstregisteredDate;
    @SerializedName("state")
    @Expose
    private State state;

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public CountryId getCountryId() {
        return countryId;
    }

    public void setCountryId(CountryId countryId) {
        this.countryId = countryId;
    }

    public boolean isRegisteredCompany() {
        return registeredCompany;
    }

    public void setRegisteredCompany(boolean registeredCompany) {
        this.registeredCompany = registeredCompany;
    }

    public String getRegisterNo() {
        return registerNo;
    }

    public void setRegisterNo(String registerNo) {
        this.registerNo = registerNo;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyNo() {
        return companyNo;
    }

    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Object getHiConnectStatus() {
        return hiConnectStatus;
    }

    public void setHiConnectStatus(Object hiConnectStatus) {
        this.hiConnectStatus = hiConnectStatus;
    }

    public Object getHiConnectCompnyRegNo() {
        return hiConnectCompnyRegNo;
    }

    public void setHiConnectCompnyRegNo(Object hiConnectCompnyRegNo) {
        this.hiConnectCompnyRegNo = hiConnectCompnyRegNo;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getYearclosing() {
        return yearclosing;
    }

    public void setYearclosing(String yearclosing) {
        this.yearclosing = yearclosing;
    }

    public String getStartperiod() {
        return startperiod;
    }

    public void setStartperiod(String startperiod) {
        this.startperiod = startperiod;
    }

    public String getClosingperiod() {
        return closingperiod;
    }

    public void setClosingperiod(String closingperiod) {
        this.closingperiod = closingperiod;
    }

    public String getStartyear() {
        return startyear;
    }

    public void setStartyear(String startyear) {
        this.startyear = startyear;
    }

    public String getEndyear() {
        return endyear;
    }

    public void setEndyear(String endyear) {
        this.endyear = endyear;
    }

    public String getClosingMethod() {
        return closingMethod;
    }

    public void setClosingMethod(String closingMethod) {
        this.closingMethod = closingMethod;
    }

    public String getIncdate() {
        return incdate;
    }

    public void setIncdate(String incdate) {
        this.incdate = incdate;
    }

    public CurrencyId getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(CurrencyId currencyId) {
        this.currencyId = currencyId;
    }

    public Object getAccCodeSep() {
        return accCodeSep;
    }

    public void setAccCodeSep(Object accCodeSep) {
        this.accCodeSep = accCodeSep;
    }

    public long getGstId() {
        return gstId;
    }

    public void setGstId(long gstId) {
        this.gstId = gstId;
    }

    public IndustryclassificationId getIndustryclassificationId() {
        return industryclassificationId;
    }

    public void setIndustryclassificationId(IndustryclassificationId industryclassificationId) {
        this.industryclassificationId = industryclassificationId;
    }

    public BusinesstypeId getBusinesstypeId() {
        return businesstypeId;
    }

    public void setBusinesstypeId(BusinesstypeId businesstypeId) {
        this.businesstypeId = businesstypeId;
    }

    public Object getLogo() {
        return logo;
    }

    public void setLogo(Object logo) {
        this.logo = logo;
    }

    public long getBufferDays() {
        return bufferDays;
    }

    public void setBufferDays(long bufferDays) {
        this.bufferDays = bufferDays;
    }

    public String getCreateOrSelectAM() {
        return createOrSelectAM;
    }

    public void setCreateOrSelectAM(String createOrSelectAM) {
        this.createOrSelectAM = createOrSelectAM;
    }

    public CompanyInfoId getCompanyInfoId() {
        return companyInfoId;
    }

    public void setCompanyInfoId(CompanyInfoId companyInfoId) {
        this.companyInfoId = companyInfoId;
    }

    public Object getLan() {
        return lan;
    }

    public void setLan(Object lan) {
        this.lan = lan;
    }

    public String getComplitionstatus() {
        return complitionstatus;
    }

    public void setComplitionstatus(String complitionstatus) {
        this.complitionstatus = complitionstatus;
    }

    public Object getImageFile() {
        return imageFile;
    }

    public void setImageFile(Object imageFile) {
        this.imageFile = imageFile;
    }

    public Object getLocationId() {
        return locationId;
    }

    public void setLocationId(Object locationId) {
        this.locationId = locationId;
    }

    public Object getUseraccountId() {
        return useraccountId;
    }

    public void setUseraccountId(Object useraccountId) {
        this.useraccountId = useraccountId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Object getImageBlob() {
        return imageBlob;
    }

    public void setImageBlob(Object imageBlob) {
        this.imageBlob = imageBlob;
    }

    public String getPanNumber() {
        return panNumber;
    }

    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }

    public String getGstregisteredDate() {
        return gstregisteredDate;
    }

    public void setGstregisteredDate(String gstregisteredDate) {
        this.gstregisteredDate = gstregisteredDate;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
