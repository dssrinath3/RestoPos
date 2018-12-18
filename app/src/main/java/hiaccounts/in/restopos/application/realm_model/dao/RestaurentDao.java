package hiaccounts.in.restopos.application.realm_model.dao;

import android.util.Log;

import java.io.File;
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
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class RestaurentDao {
    private Realm realm;

    public RestaurentDao(Realm realm) {
        this.realm = realm;
    }

    public void saveRestuarentCategoryData(final Realm_ItemCategoryList categoryList, final String category) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Number currentIdNum = realm.where(Realm_ItemCategoryList.class).max("id");
                Long nextId;
                if (currentIdNum == null) {
                    nextId = 1l;
                } else {
                    nextId = currentIdNum.intValue() + 1l;
                }

                categoryList.setItemCategoryId(getCategoryLastIndexId() + 1);
                categoryList.setItemCategoryCode("0"+categoryList.getItemCategoryId());

                if (!checkIfExists(category)){
                    categoryList.setId(nextId);
                    realm.copyToRealmOrUpdate(categoryList);
                }

            }
        });
    }
    public void saveRestuarentCustomerData(final Realm_Customer customer) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Number currentIdNum = realm.where(Realm_Customer.class).max("id");
                Long nextId;
                if (currentIdNum == null) {
                    nextId = 1l;
                } else {
                    nextId = currentIdNum.intValue() + 1l;
                }


                    customer.setId(nextId);
                    customer.setCustomerId(nextId);
                    realm.copyToRealmOrUpdate(customer);

            }
        });
    }

    public void updateItemQty(final Realm_TableOrders categoryList, final Realm_TableOrders order) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {


                Realm_TableOrders obj = realm.where(Realm_TableOrders.class).equalTo("id", order.getId()).findFirst();
                if(obj == null) {
                    obj = realm.createObject(Realm_TableOrders.class, obj.getId());
                }
                obj.setQty(categoryList.getQty());
                obj.setUnitPrice(categoryList.getUnitPrice());
                obj.setSalesPrice(categoryList.getSalesPrice());
                obj.setTotalPrice(categoryList.getTotalPrice());
            }
        });
    }

    public void updateChangeTableName(final String currentTableName, final String changeTableName) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {


                List<Realm_TableOrders> obj = realm.where(Realm_TableOrders.class).equalTo("tableName", currentTableName).findAll();
                if (obj!=null && obj.size()>0){
                    for (Realm_TableOrders orders : obj){
                        if(orders == null) {

                            orders = realm.createObject(Realm_TableOrders.class, orders.getId());
                        }
                        orders.setTableName(changeTableName);
                    }

                }


            }
        });
    }

    public void updateKotStatus(final Realm_TableOrders order) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yy hh:mm:ss aa");
                Date date = new Date();
                Realm_TableOrders obj = realm.where(Realm_TableOrders.class).equalTo("id", order.getId()).findFirst();
                if(obj == null) {
                    obj = realm.createObject(Realm_TableOrders.class, obj.getId());
                }
                obj.setKotStatus(true);
                obj.setOrderDate(formatter.format(date));
            }
        });
    }

    public boolean checkIfExists(String itemCategoryName){

        RealmQuery<Realm_ItemCategoryList> query = realm.where(Realm_ItemCategoryList.class)
                .equalTo("itemCategoryName", itemCategoryName);
        return query.count() != 0;
    }

    public Realm_TableOrders getItemCategoryDetails(Long itemId, String tableName){
        Realm_TableOrders  query = realm.where(Realm_TableOrders.class).equalTo("itemId", itemId).and().equalTo("tableName",tableName).findFirst();

        return query;
    }
    public Realm_Temp_TableOrders getTempItemCategoryDetails(Long itemId, String tableName){
        Realm_Temp_TableOrders  query = realm.where(Realm_Temp_TableOrders.class).equalTo("itemId", itemId).and().equalTo("tableName",tableName).findFirst();

        return query;
    }

    public void saveRestuarentDiscountData(final Realm_Discount discount) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Number currentIdNum = realm.where(Realm_Discount.class).max("id");
                Long nextId;
                if (currentIdNum == null) {
                    nextId = 1l;
                } else {
                    nextId = currentIdNum.intValue() + 1l;
                }
                discount.setId(nextId);
                discount.setDiscountId(nextId);
                realm.copyToRealmOrUpdate(discount);
            }
        });
    }

    public void saveRestuarentTableOrders(final Realm_TableOrders itemList, final Realm_ItemList item, final String tableName) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Number currentIdNum = realm.where(Realm_TableOrders.class).max("id");
                Long nextId;
                if (currentIdNum == null) {
                    nextId = 1l;
                } else {
                    nextId = currentIdNum.intValue() + 1l;
                }


                Realm_TableOrders tableOrders =   getItemCategoryDetails(item.getItemId(),tableName);

                if (tableOrders!=null){
                    if (item.getItemId() !=null && item.getItemId() == tableOrders.getItemId()){
                        itemList.setId(tableOrders.getId());
                        itemList.setQty(tableOrders.getQty()+1);
                        itemList.setUnitPrice(tableOrders.getUnitPrice());
                        itemList.setTotalPrice(itemList.getQty()*itemList.getUnitPrice());
                        realm.copyToRealmOrUpdate(itemList);
                    }
                }else{
                    itemList.setId(nextId);
                    realm.copyToRealmOrUpdate(itemList);
                }


            }
        });
    }
    public void saveRestuarentTempTableOrders(final Realm_Temp_TableOrders itemList, final Realm_TableOrders item, final String tableName) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Number currentIdNum = realm.where(Realm_Temp_TableOrders.class).max("id");
                Long nextId;
                if (currentIdNum == null) {
                    nextId = 1l;
                } else {
                    nextId = currentIdNum.intValue() + 1l;
                }


                Realm_Temp_TableOrders tableOrders =   getTempItemCategoryDetails(item.getItemId(),tableName);

                if (tableOrders!=null){
                    if (item.getItemId() !=null && item.getItemId() == tableOrders.getItemId()){
                        itemList.setId(tableOrders.getId());
                        realm.copyToRealmOrUpdate(itemList);
                    }
                }else{
                    itemList.setId(nextId);
                    realm.copyToRealmOrUpdate(itemList);
                }


            }
        });
    }

    public void saveRestuarentTaxData(final Realm_Tax tax) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Number currentIdNum = realm.where(Realm_Tax.class).max("id");
                Long nextId;
                if (currentIdNum == null) {
                    nextId = 1l;
                } else {
                    nextId = currentIdNum.intValue() + 1l;
                }
                tax.setId(nextId);
                tax.setTaxId(nextId);
                realm.copyToRealmOrUpdate(tax);
            }
        });
    }

    public void saveRestuarentCheckoutData(final Realm_CheckoutData checkoutData) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Number currentIdNum = realm.where(Realm_CheckoutData.class).max("id");
                Long nextId;
                if (currentIdNum == null) {
                    nextId = 1l;
                } else {
                    nextId = currentIdNum.intValue() + 1l;
                }
                checkoutData.setId(nextId);
                checkoutData.setInvoiceNumber(nextId);
                checkoutData.setTokenNumber(nextId);
                realm.copyToRealmOrUpdate(checkoutData);
            }
        });
    }
    public void saveRestuarentSalesItemDetails(final Realm_SalesItem_Details checkoutData) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Number currentIdNum = realm.where(Realm_SalesItem_Details.class).max("id");
                Long nextId;
                if (currentIdNum == null) {
                    nextId = 1l;
                } else {
                    nextId = currentIdNum.intValue() + 1l;
                }
                checkoutData.setId(nextId);
                realm.copyToRealmOrUpdate(checkoutData);
            }
        });
    }

    public void saveRestuarentTableNameData(final Realm_TableName table) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Number currentIdNum = realm.where(Realm_TableName.class).max("id");
                Long nextId;
                if (currentIdNum == null) {
                    nextId = 1l;
                } else {
                    nextId = currentIdNum.intValue() + 1l;
                }
                table.setId(nextId);
                realm.copyToRealmOrUpdate(table);
            }
        });
    }
    public void saveRestuarentItemData(final Realm_ItemList itemList) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Number currentIdNum = realm.where(Realm_ItemList.class).max("id");
                Long nextId;
                if (currentIdNum == null) {
                    nextId = 1l;
                } else {
                    nextId = currentIdNum.intValue() + 1l;
                }

                itemList.setItemId(nextId);
                itemList.setItemCode("00"+nextId);
                itemList.setId(nextId);
                realm.copyToRealmOrUpdate(itemList);
            }
        });
    }
    public List<ItemCategory> getRestaCategoryList() {
        List<ItemCategory> data = new ArrayList<>();
            RealmResults<Realm_ItemCategoryList> realmSelec= realm.where(Realm_ItemCategoryList.class).findAll();

            for (Realm_ItemCategoryList realmSelectItem : realmSelec){
                ItemCategory selectedItemsList = new ItemCategory();
                selectedItemsList.setItemCategoryId(realmSelectItem.getItemCategoryId());
                selectedItemsList.setItemCategoryName(realmSelectItem.getItemCategoryName());
                selectedItemsList.setItemCategoryCode(realmSelectItem.getItemCategoryCode());
                selectedItemsList.setItemCategoryDesc(realmSelectItem.getItemCategoryDesc());
                data.add(selectedItemsList);
            }
        return data;
    }

    public List<Realm_ItemList> getRestaItemList(ItemCategory itemCateList) {
        RealmResults<Realm_ItemList> realmSelec= realm.where(Realm_ItemList.class).equalTo("itemCategoryId",itemCateList.getItemCategoryId()).findAll();
        return realmSelec;
    }
    public List<Realm_Tax> getRestaItemTaxList() {
        RealmResults<Realm_Tax> realmSelec= realm.where(Realm_Tax.class).findAll();
        return realmSelec;
    }
    public List<Realm_ItemList> getRestaItemListFilter() {
        RealmResults<Realm_ItemList> realmSelec= realm.where(Realm_ItemList.class).findAll();
        return realmSelec;
    }
    public List<Realm_Customer> getRestaCustomerListFilter() {
        RealmResults<Realm_Customer> realmSelec= realm.where(Realm_Customer.class).findAll();
        return realmSelec;
    }
    public List<Realm_ItemCategoryList> getRestaCategoryListFilter() {
        RealmResults<Realm_ItemCategoryList> realmSelec= realm.where(Realm_ItemCategoryList.class).findAll();
        return realmSelec;
    }
    public List<Realm_Tax> getRestaTaxListFilter() {
        RealmResults<Realm_Tax> realmSelec= realm.where(Realm_Tax.class).findAll();
        return realmSelec;
    }
    public List<Realm_Discount> getRestaDiscountListFilter() {
        RealmResults<Realm_Discount> realmSelec= realm.where(Realm_Discount.class).findAll();
        return realmSelec;
    }
    public List<Realm_TableName> getRestaTableListFilter() {
        RealmResults<Realm_TableName> realmSelec= realm.where(Realm_TableName.class).findAll();
        return realmSelec;
    }
    public List<Realm_Customer> getCustomerData() {
        RealmResults<Realm_Customer> realmSelec= realm.where(Realm_Customer.class).findAll();
        return realmSelec;
    }
    public Realm_CheckoutData getCheckoutData() {
        Realm_CheckoutData realmSelec= realm.where(Realm_CheckoutData.class).findFirst();
        return realmSelec;
    }
    public List<Realm_CheckoutData> getAllCheckoutData() {
        List<Realm_CheckoutData> realmSelec= realm.where(Realm_CheckoutData.class).findAll();
        return realmSelec;
    }
    public RealmResults<Realm_SalesItem_Details> getAllSalesItemDetails() {
        RealmResults<Realm_SalesItem_Details> realmSelec= realm.where(Realm_SalesItem_Details.class).findAll();
        return realmSelec;
    }
    public RealmResults<Realm_SalesItem_Details> getDistinctItems() {
        RealmResults<Realm_SalesItem_Details> realmSelec= realm.where(Realm_SalesItem_Details.class).distinct("itemName");
        return realmSelec;
    }

    public RealmResults<Realm_SalesItem_Details> getDistinctCategorys() {
        RealmResults<Realm_SalesItem_Details> realmSelec= realm.where(Realm_SalesItem_Details.class).distinct("itemCategoryName");
        return realmSelec;
    }
    public RealmQuery getTop3Items(Realm_SalesItem_Details details) {
        RealmQuery realmSelec= realm.where(Realm_SalesItem_Details.class).equalTo("itemName",details.getItemName());
        return realmSelec;
    }
    public RealmQuery getTop3Category(Realm_SalesItem_Details details) {
        RealmQuery realmSelec= realm.where(Realm_SalesItem_Details.class).equalTo("itemCategoryName",details.getItemCategoryName());
        return realmSelec;
    }
    public Long getLastIndexCheckoutData() {
        try {
            List<Realm_CheckoutData> checkoutData = realm.where(Realm_CheckoutData.class).findAll();
            return checkoutData.get(checkoutData.size() - 1).getInvoiceNumber();
        } catch (Exception e) {
            return 0L;
        }

    }

    public ItemCategory getRestaCategoryItemList(Long id) {
        ItemCategory itemCategory = new ItemCategory();
       Realm_ItemCategoryList realmSelec= realm.where(Realm_ItemCategoryList.class).equalTo("id",id).findFirst();
        itemCategory.setItemCategoryId(realmSelec.getItemCategoryId());
        itemCategory.setItemCategoryName(realmSelec.getItemCategoryName());
        itemCategory.setItemCategoryCode(realmSelec.getItemCategoryCode());
        return itemCategory;
    }

    public List<Realm_TableName> getRestaTableList() {
        RealmResults<Realm_TableName> realmSelec= realm.where(Realm_TableName.class).findAll();
        return realmSelec;
    }
    public List<Realm_TableName> getRestaMergeTableList(String tablename) {
        RealmResults<Realm_TableName> realmSelec= realm.where(Realm_TableName.class).notEqualTo("tableName",tablename).findAll();
        return realmSelec;
    }

    public Long getCategoryLastIndexId() {
        try {
            List<Realm_ItemCategoryList> category = realm.where(Realm_ItemCategoryList.class).findAll();
            return category.get(category.size() - 1).getItemCategoryId();
        } catch (Exception e) {
            return 0L;
        }

    }
    public void deleteOrdersOfTable(final String tablename) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Realm_TableOrders> result = realm.where(Realm_TableOrders.class).equalTo("tableName",tablename).findAll();
                result.deleteAllFromRealm();
            }
        });

    }
    public void deleteOrdersOfTempTable(final String tablename) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Realm_Temp_TableOrders> result = realm.where(Realm_Temp_TableOrders.class).equalTo("tableName",tablename).findAll();
                result.deleteAllFromRealm();
            }
        });
    }
    public void deleteItem(final Realm_ItemList item) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Realm_ItemList result = realm.where(Realm_ItemList.class).equalTo("id",item.getId()).findFirst();
                result.deleteFromRealm();
            }
        });

    }
    public void deleteTable(final Realm_TableName item) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Realm_TableName result = realm.where(Realm_TableName.class).equalTo("id",item.getId()).findFirst();
                result.deleteFromRealm();
            }
        });

    }
    public void deleteTax(final Realm_Tax item) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Realm_Tax result = realm.where(Realm_Tax.class).equalTo("id",item.getId()).findFirst();
                result.deleteFromRealm();
            }
        });

    }
    public void deleteCategory(final Realm_ItemCategoryList item) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Realm_ItemCategoryList result = realm.where(Realm_ItemCategoryList.class).equalTo("id",item.getId()).findFirst();
                result.deleteFromRealm();
            }
        });

    }
    public List<Realm_TableOrders> getRestaTableOrderList(String tableName) {
        RealmResults<Realm_TableOrders> realmSelec= realm.where(Realm_TableOrders.class).equalTo("tableName",tableName).findAll();
        return realmSelec;
    }
    public List<Realm_Temp_TableOrders> getRestaTempTableOrderList(String tableName) {
        RealmResults<Realm_Temp_TableOrders> realmSelec= realm.where(Realm_Temp_TableOrders.class).equalTo("tableName",tableName).findAll();
        return realmSelec;
    }

    public Realm_Temp_TableOrders getRestaTempTableOrderData(String tableName, Realm_TableOrders norders) {
       Realm_Temp_TableOrders realmSelec= realm.where(Realm_Temp_TableOrders.class).equalTo("itemCode",norders.getItemCode()).equalTo("tableName",tableName).findFirst();
        return realmSelec;
    }

    public List<Realm_ItemList> getRestaAllTableOrderList() {
        RealmResults<Realm_ItemList> realmSelec= realm.where(Realm_ItemList.class).findAll();
        return realmSelec;
    }

    public void updateItemDetails(final String itemName, final Double price, final Double qty, final Long categoryid, final String categoryname, final String path, final String taxname, final Long taxId, final Double taxAmount, final String hsncode, final Realm_ItemList item) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {


                Realm_ItemList obj = realm.where(Realm_ItemList.class).equalTo("id", item.getId()).findFirst();
                if(obj == null) {
                    obj = realm.createObject(Realm_ItemList.class, obj.getId());
                }
                obj.setItemName(itemName);
                obj.setQty(qty);
                obj.setUnitPrice(price);
                obj.setSalesPrice(price);
                obj.setItemCategoryName(categoryname);
                obj.setItemCategoryId(categoryid);
                obj.setImageLocation(path);
                obj.setTaxId(taxId);
                obj.setTaxName(taxname);
                obj.setTaxAmount(taxAmount);
                obj.setHsnCode(hsncode);

            }
        });
    }
    public void updateTableDetails(final String tableName, final Realm_TableName item) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {


                Realm_TableName obj = realm.where(Realm_TableName.class).equalTo("id", item.getId()).findFirst();
                if(obj == null) {
                    obj = realm.createObject(Realm_TableName.class, obj.getId());
                }
                obj.setTableName(tableName);


            }
        });
    }
    public void updateTaxDetails(final String taxname, final String hsncode, final Realm_Tax item) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {


                Realm_Tax obj = realm.where(Realm_Tax.class).equalTo("id", item.getId()).findFirst();
                if(obj == null) {
                    obj = realm.createObject(Realm_Tax.class, obj.getId());
                }
                obj.setTaxName(taxname);
                obj.setHsnCode(hsncode);

            }
        });
    }
    public void updateCategoryDetails(final String category, final Long priority, final Realm_ItemCategoryList item) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {


                Realm_ItemCategoryList obj = realm.where(Realm_ItemCategoryList.class).equalTo("id", item.getId()).findFirst();
                if(obj == null) {
                    obj = realm.createObject(Realm_ItemCategoryList.class, obj.getId());
                }
                obj.setItemCategoryName(category);
                obj.setPriority(priority);

            }
        });
    }



}
