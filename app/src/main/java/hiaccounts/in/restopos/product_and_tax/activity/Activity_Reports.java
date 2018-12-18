package hiaccounts.in.restopos.product_and_tax.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hiaccounts.in.restopos.R;
import hiaccounts.in.restopos.application.realm_model.data.Realm_CheckoutData;
import hiaccounts.in.restopos.application.realm_model.data.Realm_SalesItem_Details;
import hiaccounts.in.restopos.application.realm_model.data.Realm_TableOrders;
import hiaccounts.in.restopos.application.realmdbmanager.RealmManager;
import hiaccounts.in.restopos.utility.UtilView;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class Activity_Reports extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @BindView(R.id.fab_Print)
    FloatingActionButton fabPrint;
    @BindView(R.id.tvRs)
    TextView tvRs;
    @BindView(R.id.total_sales_Amount)
    TextView totalSalesAmount;
    @BindView(R.id.total_no_bills)
    TextView totalNoBills;
    @BindView(R.id.tvRstx)
    TextView tvRstx;
    @BindView(R.id.total_tax_Amount)
    TextView totalTaxAmount;
    @BindView(R.id.tvRssal)
    TextView tvRssal;
    @BindView(R.id.total_avg_bill)
    TextView totalAvgBill;
    @BindView(R.id.tvRscash)
    TextView tvRscash;
    @BindView(R.id.total_cash_payment)
    TextView totalCashPayment;
    @BindView(R.id.tvRscard)
    TextView tvRscard;
    @BindView(R.id.total_card_payment)
    TextView totalCardPayment;
    @BindView(R.id.tvRscredit)
    TextView tvRscredit;
    @BindView(R.id.total_credit_payment)
    TextView totalCreditPayment;
    @BindView(R.id.tvRsdinin)
    TextView tvRsdinin;
    @BindView(R.id.total_dinein_payment)
    TextView totalDineinPayment;
    @BindView(R.id.tvRstakeaway)
    TextView tvRstakeaway;
    @BindView(R.id.total_takeaway_payment)
    TextView totalTakeawayPayment;
    @BindView(R.id.tvRsdelivery)
    TextView tvRsdelivery;
    @BindView(R.id.total_delivery_payment)
    TextView totalDeliveryPayment;
    @BindView(R.id.tvRsitemname1)
    TextView tvRsitemname1;
    @BindView(R.id.total_itemname_1)
    TextView totalItemname1;
    @BindView(R.id.tvRsitemname2)
    TextView tvRsitemname2;
    @BindView(R.id.total_itemname_2)
    TextView totalItemname2;
    @BindView(R.id.tvRsitemname3)
    TextView tvRsitemname3;
    @BindView(R.id.total_itemname_3)
    TextView totalItemname3;
    @BindView(R.id.tvRscategoryname1)
    TextView tvRscategoryname1;
    @BindView(R.id.total_categoryname_1)
    TextView totalCategoryname1;
    @BindView(R.id.tvRscategoryname2)
    TextView tvRscategoryname2;
    @BindView(R.id.total_categoryname_2)
    TextView totalCategoryname2;
    @BindView(R.id.tvRscategoryname3)
    TextView tvRscategoryname3;
    @BindView(R.id.total_categoryname_3)
    TextView totalCategoryname3;
    @BindView(R.id.tvRsseller1)
    TextView tvRsseller1;
    @BindView(R.id.total_sellername_1)
    TextView totalSellername1;
    @BindView(R.id.tvRsseller2)
    TextView tvRsseller2;
    @BindView(R.id.total_sellername_2)
    TextView totalSellername2;
    @BindView(R.id.tvRsseller3)
    TextView tvRsseller3;
    @BindView(R.id.total_sellername_3)
    TextView totalSellername3;
    @BindView(R.id.tvRsrefunds)
    TextView tvRsrefunds;
    @BindView(R.id.total_refunds)
    TextView totalRefunds;
    @BindView(R.id.tvRsrefundna)
    TextView tvRsrefundna;
    @BindView(R.id.total_refund_na)
    TextView totalRefundNa;
    @BindView(R.id.tvRssellerreason)
    TextView tvRssellerreason;
    @BindView(R.id.total_refund_reason)
    TextView totalRefundReason;
    @BindView(R.id.tvRscustomers)
    TextView tvRscustomers;
    @BindView(R.id.total_customers)
    TextView totalCustomers;
    @BindView(R.id.tvRstopcustomer)
    TextView tvRstopcustomer;
    @BindView(R.id.total_top_customer)
    TextView totalTopCustomer;
    @BindView(R.id.tvRsdiscount)
    TextView tvRsdiscount;
    @BindView(R.id.total_discount)
    TextView totalDiscount;
    @BindView(R.id.tvRsdiscountbills)
    TextView tvRsdiscountbills;
    @BindView(R.id.total_discount_bills)
    TextView totalDiscountBills;
    @BindView(R.id.total_cash_percent)
    TextView totalCashPercent;
    @BindView(R.id.total_card_percent)
    TextView totalCardPercent;
    @BindView(R.id.total_credit_percent)
    TextView totalCreditPercent;
    @BindView(R.id.total_dinein_percent)
    TextView totalDineinPercent;
    @BindView(R.id.total_takeaway_percent)
    TextView totalTakeawayPercent;
    @BindView(R.id.total_delivery_percent)
    TextView totalDeliveryPercent;
    @BindView(R.id.today_date)
    TextView todayDate;
    @BindView(R.id.top_seller_itemname1)
    TextView topSellerItemname1;
    @BindView(R.id.top_seller_itemname2)
    TextView topSellerItemname2;
    @BindView(R.id.top_seller_itemname3)
    TextView topSellerItemname3;
    @BindView(R.id.total_itemname_1_percent)
    TextView totalItemname1Percent;
    @BindView(R.id.total_itemname_2_percent)
    TextView totalItemname2Percent;
    @BindView(R.id.total_itemname_3_percent)
    TextView totalItemname3Percent;
    @BindView(R.id.topSellerCategoryname1)
    TextView topSellerCategoryname1;
    @BindView(R.id.total_categoryname_1_percent)
    TextView totalCategoryname1Percent;
    @BindView(R.id.topSellerCategoryname2)
    TextView topSellerCategoryname2;
    @BindView(R.id.total_categoryname_2_percent)
    TextView totalCategoryname2Percent;
    @BindView(R.id.topSellerCategoryname3)
    TextView topSellerCategoryname3;
    @BindView(R.id.total_categoryname_3_percent)
    TextView totalCategoryname3Percent;
    private Activity activity;
    String titleName = "";
    List<Realm_CheckoutData> checkoutDataList;
    private Double totalSalesAmt = 0.00, totalSalesCashAmt = 0.00, totalSalesCashPercentage = 0.00, totalSalesCardAmt = 0.00, totalSalesCardPercentage = 0.00, totalSalesCreditAmt = 0.00, totalSalesCreditPercentage = 0.00, totalTaxAmt = 0.00, avgSalesAmt = 0.00;
    private Double totalSalesDineinAmt = 0.00, totalSalesDineinPercent = 0.00, totalSalesTakeawayAmt = 0.00, totalSalesTakeawayPercent = 0.00, totalSalesDeliveryAmt = 0.00, totalSalesDeliveryPercent = 0.00;
    private int noOfBills = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        activity = this;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        RealmManager.open();


        UtilView.showLogCat("@Flow", "relam " + RealmManager.open().getPath());

        ActionBar actionBar = getSupportActionBar();

        Intent in = getIntent();
        titleName = in.getStringExtra("callingFor");
        if (titleName != null)
            //toolbar.setTitle(titleName);
            actionBar.setTitle(titleName);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }

        fabPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, "Print", Toast.LENGTH_SHORT).show();
            }
        });

        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
            String formattedDate = dateFormat.format(new Date()).toString();
            todayDate.setText(formattedDate + ",12:01 AM - " + formattedDate + ",11:59 PM");

            checkoutDataList = RealmManager.createRestaurentDao().getAllCheckoutData();
            if (checkoutDataList != null && checkoutDataList.size() > 0) {

                for (Realm_CheckoutData checkoutData : checkoutDataList) {
                    totalSalesAmt += checkoutData.getTotalCheckoutAmount();
                    totalTaxAmt += checkoutData.getTotalTaxAmount();
                    noOfBills += 1;
                    if (checkoutData != null && checkoutData.getPaymentType().equals("Cash")) {
                        totalSalesCashAmt += checkoutData.getTotalCheckoutAmount();
                        totalSalesCashPercentage = (totalSalesCashAmt / totalSalesAmt) * 100;
                    } else if (checkoutData != null && checkoutData.getPaymentType().equals("Card")) {
                        totalSalesCardAmt += checkoutData.getTotalCheckoutAmount();
                        totalSalesCardPercentage = (totalSalesCardAmt / totalSalesAmt) * 100;
                    } else {
                        totalSalesCreditAmt += checkoutData.getTotalCheckoutAmount();
                        totalSalesCreditPercentage = (totalSalesCreditAmt / totalSalesAmt) * 100;
                    }

                    if (checkoutData != null && checkoutData.getOrderType().equals("Dine-in")) {
                        totalSalesDineinAmt += checkoutData.getTotalCheckoutAmount();
                        totalSalesDineinPercent = (totalSalesDineinAmt / totalSalesAmt) * 100;
                    } else if (checkoutData != null && checkoutData.getOrderType().equals("TakeAway")) {
                        totalSalesTakeawayAmt += checkoutData.getTotalCheckoutAmount();
                        totalSalesTakeawayPercent = (totalSalesTakeawayAmt / totalSalesAmt) * 100;
                    } else {
                        totalSalesDeliveryAmt += checkoutData.getTotalCheckoutAmount();
                        totalSalesDeliveryPercent = (totalSalesDeliveryAmt / totalSalesAmt) * 100;
                    }

                    Gson gson = new Gson();
                    if (checkoutData != null) {
                        String itemData = checkoutData.getCheckOutData();

                        TypeToken<List<Realm_TableOrders>> token = new TypeToken<List<Realm_TableOrders>>() {
                        };
                        List<Realm_TableOrders> getCheckoutOrder = gson.fromJson(itemData, token.getType());

                    }


                }
            }

            avgSalesAmt = totalSalesAmt / noOfBills;
            totalSalesAmount.setText("" + totalSalesAmt.longValue());
            totalNoBills.setText("" + noOfBills);
            totalTaxAmount.setText("" + totalTaxAmt.longValue());
            totalAvgBill.setText("" + avgSalesAmt.longValue());
            totalCashPayment.setText("" + totalSalesCashAmt.longValue());
            totalCardPayment.setText("" + totalSalesCardAmt.longValue());
            totalCreditPayment.setText("" + totalSalesCreditAmt.longValue());
            totalCashPercent.setText(String.format("%.1f", totalSalesCashPercentage) + "%");
            totalCardPercent.setText(String.format("%.1f", totalSalesCardPercentage) + "%");
            totalCreditPercent.setText(String.format("%.1f", totalSalesCreditPercentage) + "%");
            totalDineinPayment.setText("" + totalSalesDineinAmt.longValue());
            totalTakeawayPayment.setText("" + totalSalesTakeawayAmt.longValue());
            totalDeliveryPayment.setText("" + totalSalesDeliveryAmt.longValue());
            totalDineinPercent.setText(String.format("%.1f", totalSalesDineinPercent) + "%");
            totalTakeawayPercent.setText(String.format("%.1f", totalSalesTakeawayPercent) + "%");
            totalDeliveryPercent.setText(String.format("%.1f", totalSalesDeliveryPercent) + "%");


            RealmResults<Realm_SalesItem_Details> itemNames = RealmManager.createRestaurentDao().getDistinctItems();
            Log.e("itemnameize", String.valueOf(itemNames.size()));
            if (itemNames != null && itemNames.size() > 0) {
                HashMap<String, Double> hashMap = new HashMap<String, Double>();

                for (Realm_SalesItem_Details details : itemNames) {
                    RealmQuery data = RealmManager.createRestaurentDao().getTop3Items(details);

                    Double value = Double.valueOf(String.valueOf(data.sum("totalPrice"))) + Double.valueOf(String.valueOf(data.sum("taxAmount")));
                    Log.e("itemname " + details.getItemName() + "  amount", String.valueOf(value));
                    hashMap.put(details.getItemName(), value);
                }

                Set<Map.Entry<String, Double>> set = hashMap.entrySet();
                List<Map.Entry<String, Double>> list = new ArrayList<Map.Entry<String, Double>>(set);
                Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
                    @Override
                    public int compare(Map.Entry<String, Double> o1,
                                       Map.Entry<String, Double> o2) {
                        return o2.getValue().compareTo(o1.getValue());
                    }

                });
                Log.e("reports daaa", String.valueOf(list.subList(0,list.size())));
                if (list != null && list.size() > 0) {

                    for (int i = 0; i < list.size(); i++) {
                        if (i == 0){
                            topSellerItemname1.setText(list.get(i).getKey());
                            totalItemname1.setText("" + list.get(i).getValue());
                            Double itemper1 = (list.get(i).getValue() / totalSalesAmt) * 100;
                            totalItemname1Percent.setText(String.format("%.1f", itemper1) + "%");
                        }
                        if (i == 1){
                            topSellerItemname2.setText(list.get(i).getKey());
                            totalItemname2.setText("" + list.get(i).getValue());
                            Double itemper2 = (list.get(1).getValue() / totalSalesAmt) * 100;
                            totalItemname2Percent.setText(String.format("%.1f", itemper2) + "%");
                        }
                        if (i == 2){
                            topSellerItemname3.setText(list.get(i).getKey());
                            totalItemname3.setText("" + list.get(i).getValue());
                            Double itemper3 = (list.get(2).getValue() / totalSalesAmt) * 100;
                            totalItemname3Percent.setText(String.format("%.1f", itemper3) + "%");
                        }












                    }
                }
            }

            RealmResults<Realm_SalesItem_Details> itemCategory = RealmManager.createRestaurentDao().getDistinctItems();
            Log.e("itemCategorysize", String.valueOf(itemCategory.size()));

            if (itemCategory != null && itemCategory.size() > 0) {
                HashMap<String, Double> hashMap = new HashMap<String, Double>();

                for (Realm_SalesItem_Details details : itemCategory) {
                    RealmQuery data = RealmManager.createRestaurentDao().getTop3Category(details);

                    Double value = Double.valueOf(String.valueOf(data.sum("totalPrice"))) + Double.valueOf(String.valueOf(data.sum("taxAmount")));
                    Log.e("itemcategoryname " + details.getItemCategoryName() + "amount", String.valueOf(value));
                    hashMap.put(details.getItemCategoryName(), value);
                }

                Set<Map.Entry<String, Double>> set = hashMap.entrySet();
                List<Map.Entry<String, Double>> list = new ArrayList<Map.Entry<String, Double>>(set);
                Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
                    @Override
                    public int compare(Map.Entry<String, Double> o1,
                                       Map.Entry<String, Double> o2) {
                        return o2.getValue().compareTo(o1.getValue());
                    }

                });

                if (list != null && list.size() > 0) {
                    Log.e("reportscategory daaa", String.valueOf(list.subList(0, list.size())));
                    for (int i = 0; i < list.size(); i++) {
                        if (i==0){
                            topSellerCategoryname1.setText(list.get(i).getKey());
                            totalCategoryname1.setText("" + list.get(i).getValue());
                            Double itemper1 =  (list.get(i).getValue()/totalSalesAmt)*100;
                            totalCategoryname1Percent.setText(String.format("%.1f", itemper1)+ "%");
                        }
                        if (i==1){
                            topSellerCategoryname2.setText(list.get(1).getKey());
                            totalCategoryname2.setText("" + list.get(1).getValue());
                            Double itemper2 =  (list.get(1).getValue()/totalSalesAmt)*100;
                            totalCategoryname2Percent.setText(String.format("%.1f", itemper2)+ "%");
                        }
                        if (i==2){
                            topSellerCategoryname3.setText(list.get(2).getKey());
                            totalCategoryname3.setText("" + list.get(2).getValue());
                            Double itemper3 =  (list.get(2).getValue()/totalSalesAmt)*100;
                            totalCategoryname3Percent.setText(String.format("%.1f", itemper3)+ "%");
                        }

                    }
                }
            }

        } catch (Exception e) {
            Log.e("reports", e.toString());
        }


    }

    @OnClick({R.id.total_sales_Amount, R.id.total_no_bills, R.id.total_tax_Amount, R.id.total_avg_bill})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.total_sales_Amount:
                break;
            case R.id.total_no_bills:
                break;
            case R.id.total_tax_Amount:
                break;
            case R.id.total_avg_bill:
                break;
        }
    }
}
