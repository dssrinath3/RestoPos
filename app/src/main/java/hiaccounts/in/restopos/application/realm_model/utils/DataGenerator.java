package hiaccounts.in.restopos.application.realm_model.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import hiaccounts.in.restopos.application.model.pageloaddata.ItemCategory;
import hiaccounts.in.restopos.application.realm_model.data.Realm_CheckoutData;
import hiaccounts.in.restopos.application.realm_model.data.Realm_Customer;
import hiaccounts.in.restopos.application.realm_model.data.Realm_Discount;
import hiaccounts.in.restopos.application.realm_model.data.Realm_ItemCategoryList;
import hiaccounts.in.restopos.application.realm_model.data.Realm_ItemList;
import hiaccounts.in.restopos.application.realm_model.data.Realm_SalesItem_Details;
import hiaccounts.in.restopos.application.realm_model.data.Realm_TableName;
import hiaccounts.in.restopos.application.realm_model.data.Realm_TableOrders;
import hiaccounts.in.restopos.application.realm_model.data.Realm_Tax;
import hiaccounts.in.restopos.application.realm_model.data.Realm_Temp_TableOrders;

public class DataGenerator {
    public static Realm_ItemCategoryList generateRestaCategoryItem(ItemCategory itemsList) {
        Realm_ItemCategoryList realmSelectItemList = new Realm_ItemCategoryList();
            if (itemsList!=null){
                try {
                    realmSelectItemList.setItemCategoryId(itemsList.getItemCategoryId());
                    realmSelectItemList.setItemCategoryName(itemsList.getItemCategoryName());
                    realmSelectItemList.setItemCategoryCode(itemsList.getItemCategoryCode());
                    realmSelectItemList.setItemCategoryDesc(itemsList.getItemCategoryDesc());

                }catch (Exception e){
                    Log.e("Item Category Exception",e.toString());
                }
            }
        return realmSelectItemList;
    }
    public static Realm_TableOrders generateRestatableOrders(Realm_ItemList itemsList, String tableName) {
        Realm_TableOrders realmSelectItemList = new Realm_TableOrders();
        if (itemsList!=null){
            try {
                realmSelectItemList.setItemName(itemsList.getItemName());
                realmSelectItemList.setItemId(itemsList.getItemId());
                realmSelectItemList.setItemCode(itemsList.getItemCode());
                realmSelectItemList.setItemCategoryId(itemsList.getItemCategoryId());
                realmSelectItemList.setItemCategoryName(itemsList.getItemCategoryName());
                realmSelectItemList.setUnitPrice(itemsList.getUnitPrice());
                realmSelectItemList.setQty(itemsList.getQty());
                realmSelectItemList.setTotalPrice(realmSelectItemList.getQty()*realmSelectItemList.getUnitPrice());
                realmSelectItemList.setSalesPrice(itemsList.getUnitPrice());
                realmSelectItemList.setTableName(tableName);
                realmSelectItemList.setKotStatus(false);
                realmSelectItemList.setOrderDate("");
                realmSelectItemList.setTaxId(itemsList.getTaxId());
                realmSelectItemList.setTaxName(itemsList.getTaxName());
                realmSelectItemList.setTaxAmount(itemsList.getTaxAmount());
                realmSelectItemList.setHsnCode(itemsList.getHsnCode());
            }catch (Exception e){
                Log.e("TableOrders Exception",e.toString());
            }
        }
        return realmSelectItemList;
    }

    public static Realm_Temp_TableOrders generateRestaTemptableOrders(Realm_TableOrders itemsList, String tableName) {
        Realm_Temp_TableOrders realmSelectItemList = new Realm_Temp_TableOrders();
        if (itemsList!=null){
            try {
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yy hh:mm:ss aa");
                Date date = new Date();

                realmSelectItemList.setItemName(itemsList.getItemName());
                realmSelectItemList.setItemId(itemsList.getItemId());
                realmSelectItemList.setItemCode(itemsList.getItemCode());
                realmSelectItemList.setItemCategoryId(itemsList.getItemCategoryId());
                realmSelectItemList.setItemCategoryName(itemsList.getItemCategoryName());
                realmSelectItemList.setUnitPrice(itemsList.getUnitPrice());
                realmSelectItemList.setQty(itemsList.getQty());
                realmSelectItemList.setTotalPrice(realmSelectItemList.getQty()*realmSelectItemList.getUnitPrice());
                realmSelectItemList.setSalesPrice(itemsList.getUnitPrice());
                realmSelectItemList.setTableName(tableName);
                realmSelectItemList.setKotStatus(true);
                realmSelectItemList.setOrderDate(formatter.format(date));
                realmSelectItemList.setTaxId(itemsList.getTaxId());
                realmSelectItemList.setTaxName(itemsList.getTaxName());
                realmSelectItemList.setTaxAmount(itemsList.getTaxAmount());
                realmSelectItemList.setHsnCode(itemsList.getHsnCode());
            }catch (Exception e){
                Log.e("TableOrders Exception",e.toString());
            }
        }
        return realmSelectItemList;
    }

    public static Realm_Discount generateRestaDiscountItem(String discountName, Double discountAmt) {
        Realm_Discount realmDiscount = new Realm_Discount();
            try {
                realmDiscount.setDiscountName(discountName);
                realmDiscount.setDiscountAmount(discountAmt);

            }catch (Exception e){
                Log.e("Item Discount Exception",e.toString());
            }
        return realmDiscount;
    }
    public static Realm_Tax generateRestaTaxItem(String taxName, Double taxAmt, String hsncode) {
        Realm_Tax realmTax = new Realm_Tax(0l, "", 0l, false);
        try {
            realmTax.setTaxName(taxName);
            realmTax.setTaxAmount(taxAmt);
            realmTax.setHsnCode(hsncode);
        }catch (Exception e){
            Log.e("Item Tax Exception",e.toString());
        }
        return realmTax;
    }
    public static Realm_TableName generateRestaTable(String tableName) {
        Realm_TableName realmTable = new Realm_TableName();
        try {
            realmTable.setTableName(tableName);

        }catch (Exception e){
            Log.e("realmTableException",e.toString());
        }
        return realmTable;
    }
    public static Realm_ItemCategoryList generateRestaCategoryItem(String categoryName, Long priority) {
        Realm_ItemCategoryList realmItemCategoryList = new Realm_ItemCategoryList();
        try {
            realmItemCategoryList.setItemCategoryName(categoryName);
            realmItemCategoryList.setItemCategoryDesc("");
            realmItemCategoryList.setPriority(priority);
        }catch (Exception e){
            Log.e("Item Category Exception",e.toString());
        }
        return realmItemCategoryList;
    }

    public static Realm_Customer generateRestaCustomer(String phoneno, String customerId, String cusomerName, String emailid, String address, String custdate) {
        Realm_Customer realmCustomer = new Realm_Customer();
        try {
            realmCustomer.setCustomerName(cusomerName);
            realmCustomer.setPhoneNumber(Long.valueOf(phoneno));
            //realmCustomer.setCustomerId(Long.valueOf(customerId));
            realmCustomer.setCustomerAddress(address);
            realmCustomer.setEmailId(emailid);

            realmCustomer.setCustomerDate(custdate);
        }catch (Exception e){
            Log.e("customer Exception",e.toString());
        }
        return realmCustomer;
    }

    public static Realm_ItemList generateRestaItemData(String itemName, Double price, Double qty, Long categoryid, String categoryname, String imagePath, String taxName, Long taxId, Double taxAmount, String hsncode) {
        Realm_ItemList realmItemList = new Realm_ItemList();
        try {
            realmItemList.setItemName(itemName);
            realmItemList.setSalesPrice(price);
            realmItemList.setUnitPrice(price);
            realmItemList.setQty(1.0);
            realmItemList.setItemCategoryId(categoryid);
            realmItemList.setItemCategoryName(categoryname);
            realmItemList.setImageLocation(imagePath);
            realmItemList.setTaxId(taxId);
            realmItemList.setTaxName(taxName);
           realmItemList.setTaxAmount(taxAmount);
            realmItemList.setHsnCode(hsncode);
        }catch (Exception e){
            Log.e("Item Exception",e.toString());
        }
        return realmItemList;
    }

    public static Realm_CheckoutData generateRestaCheckoutOrders(List<Realm_TableOrders> checkoutOrder, String tablename, String orderType, String paymentType, Long customerId, String customerName, Double roundingVal) {
        Realm_CheckoutData realmCheckoutData = new Realm_CheckoutData();

            try {
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yy hh:mm:ss aa");
                Date date = new Date();

                List<Realm_TableOrders> tableOrdersList = new ArrayList<>();
                Double totalCheckoutAmount=0.00,totalTaxAmount=0.00;
                if (checkoutOrder!=null && checkoutOrder.size()>0){
                    for (Realm_TableOrders itemsList : checkoutOrder){
                        Realm_TableOrders realmSelectItemList = new Realm_TableOrders();
                        realmSelectItemList.setItemName(itemsList.getItemName());
                        realmSelectItemList.setItemId(itemsList.getItemId());
                        realmSelectItemList.setItemCode(itemsList.getItemCode());
                        realmSelectItemList.setItemCategoryId(itemsList.getItemCategoryId());
                        realmSelectItemList.setItemCategoryName(itemsList.getItemCategoryName());
                        realmSelectItemList.setUnitPrice(itemsList.getUnitPrice());
                        realmSelectItemList.setQty(itemsList.getQty());
                        realmSelectItemList.setTotalPrice(realmSelectItemList.getQty()*realmSelectItemList.getUnitPrice());
                        realmSelectItemList.setSalesPrice(itemsList.getUnitPrice());
                        realmSelectItemList.setTableName(itemsList.getTableName());
                        realmSelectItemList.setKotStatus(itemsList.getKotStatus());
                        realmSelectItemList.setOrderDate(formatter.format(date));
                        realmSelectItemList.setTaxId(itemsList.getTaxId());
                        realmSelectItemList.setTaxName(itemsList.getTaxName());
                        realmSelectItemList.setTaxAmount(itemsList.getTaxAmount());
                        realmSelectItemList.setHsnCode(itemsList.getHsnCode());
                        totalCheckoutAmount += realmSelectItemList.getTotalPrice()+realmSelectItemList.getTaxAmount();
                        totalTaxAmount += realmSelectItemList.getTaxAmount();
                        tableOrdersList.add(realmSelectItemList);
                    }
                }

                Type listType = new TypeToken<List<Realm_TableOrders>>() {}.getType();
                Gson gson = new Gson();
                String json = gson.toJson(tableOrdersList, listType);

                Log.e("jsonData",json);

                realmCheckoutData.setTotalCheckoutAmount(totalCheckoutAmount);
                realmCheckoutData.setTotalTaxAmount(totalTaxAmount);
                //realmCheckoutData.setCheckOutData(json);
                realmCheckoutData.setOrderType(orderType);
                realmCheckoutData.setPaymentType(paymentType);
                realmCheckoutData.setTableName(tablename);
                realmCheckoutData.setStatus(true);
                realmCheckoutData.setStartDate(formatter.format(date));
                realmCheckoutData.setEndDate(formatter.format(date));
                realmCheckoutData.setCustomerId(customerId);
                realmCheckoutData.setCustomerName(customerName);
                realmCheckoutData.setRoundingOfValue(roundingVal);

            }catch (Exception e){
                Log.e("Checkout Exception",e.toString());
            }

        return realmCheckoutData;
    }
    public static Realm_SalesItem_Details generateRestaSalesItemDetails(Realm_TableOrders itemsList, String tablename, String orderType, String paymentType, Long customerId, String customerName, Double roundingVal, Long invoiceNo) {
        Realm_SalesItem_Details realmSelectItemList = new Realm_SalesItem_Details();

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yy hh:mm:ss aa");
            Date date = new Date();
                    realmSelectItemList.setInvoiceNumber(invoiceNo);
                    realmSelectItemList.setItemName(itemsList.getItemName());
                    realmSelectItemList.setItemId(itemsList.getItemId());
                    realmSelectItemList.setItemCode(itemsList.getItemCode());
                    realmSelectItemList.setItemCategoryId(itemsList.getItemCategoryId());
                    realmSelectItemList.setItemCategoryName(itemsList.getItemCategoryName());
                    realmSelectItemList.setUnitPrice(itemsList.getUnitPrice());
                    realmSelectItemList.setQty(itemsList.getQty());
                    realmSelectItemList.setTotalPrice(realmSelectItemList.getQty()*realmSelectItemList.getUnitPrice());
                    realmSelectItemList.setSalesPrice(itemsList.getUnitPrice());
                    realmSelectItemList.setTableName(itemsList.getTableName());
                    realmSelectItemList.setKotStatus(itemsList.getKotStatus());
                    realmSelectItemList.setOrderDate(formatter.format(date));
                    realmSelectItemList.setTaxId(itemsList.getTaxId());
                    realmSelectItemList.setTaxName(itemsList.getTaxName());
                    realmSelectItemList.setTaxAmount(itemsList.getTaxAmount());
                    realmSelectItemList.setHsnCode(itemsList.getHsnCode());
                    realmSelectItemList.setOrderType(orderType);
                    realmSelectItemList.setPaymentType(paymentType);
                    realmSelectItemList.setStartDate(formatter.format(date));
                    realmSelectItemList.setEndDate(formatter.format(date));
                    realmSelectItemList.setCustomerId(customerId);
                    realmSelectItemList.setCustomerName(customerName);

        }catch (Exception e){
            Log.e("Checkoutsales Exception",e.toString());
        }

        return realmSelectItemList;
    }
}
