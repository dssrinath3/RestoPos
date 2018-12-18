package hiaccounts.in.restopos.application.activity;

import android.app.Activity;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.cie.btp.CieBluetoothPrinter;
import com.nikoyuwono.realmbrowser.RealmBrowser;
import com.rey.material.widget.ProgressView;

import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hiaccounts.in.restopos.R;
import hiaccounts.in.restopos.application.ViewFragment;
import hiaccounts.in.restopos.application.adapter.TableOrdersSearchAdapter;
import hiaccounts.in.restopos.application.bluetoothconnection.ListActivity;
import hiaccounts.in.restopos.application.bluetoothconnection.PrintReceipt;
import hiaccounts.in.restopos.application.controlpanel.ControlPanel_Activity;
import hiaccounts.in.restopos.application.model.pageloaddata.ItemCategory;
import hiaccounts.in.restopos.application.realm_model.data.Realm_CheckoutData;
import hiaccounts.in.restopos.application.realm_model.data.Realm_Customer;
import hiaccounts.in.restopos.application.realm_model.data.Realm_ItemList;
import hiaccounts.in.restopos.application.realm_model.data.Realm_TableName;
import hiaccounts.in.restopos.application.realm_model.data.Realm_TableOrders;
import hiaccounts.in.restopos.application.realm_model.data.Realm_Temp_TableOrders;
import hiaccounts.in.restopos.application.realm_model.utils.DataGenerator;
import hiaccounts.in.restopos.application.realmdbmanager.RealmManager;
import hiaccounts.in.restopos.customer.activity.Activity_Customer;
import hiaccounts.in.restopos.product_and_tax.adapter.OrdersAdapter;
import hiaccounts.in.restopos.task.AsyncTaskCompleteListener;
import hiaccounts.in.restopos.task.PostDataTask;
import hiaccounts.in.restopos.utility.Constant;
import hiaccounts.in.restopos.utility.ServiceHandler;
import hiaccounts.in.restopos.utility.SharedPreference;
import hiaccounts.in.restopos.utility.UtilView;

public class Activity_Home extends AppCompatActivity {
    private int currentScreenOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED;

    public static final String TAG = Activity_Home.class.getSimpleName();
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @BindView(R.id.fragment_layout)
    FrameLayout fragmentLayout;
    @BindView(R.id.progress_bar)
    ProgressView progressBar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.tvTotalAmount)
    TextView tvTotalAmount;
    @BindView(R.id.totalModule)
    LinearLayout totalModule;
    @BindView(R.id.tvSubTotal)
    TextView tvSubTotal;
    @BindView(R.id.tvSubAmount)
    TextView tvSubAmount;
    @BindView(R.id.tvRoundVal)
    TextView tvRoundVal;
    @BindView(R.id.subModule)
    LinearLayout subModule;
    @BindView(R.id.subtotalModule)
    LinearLayout subtotalModule;
    @BindView(R.id.tvTotal)
    TextView tvTotal;
    @BindView(R.id.tvRs)
    TextView tvRs;
    @BindView(R.id.tvSeRs)
    TextView tvSeRs;
    @BindView(R.id.tvSubTotalAmount)
    TextView tvSubTotalAmount;
    @BindView(R.id.tvSubRs)
    TextView tvSubRs;
    @BindView(R.id.tvRounded)
    TextView tvRounded;
    @BindView(R.id.tvro)
    TextView tvro;
    @BindView(R.id.tvbarCode)
    ImageView tvbarCode;
    @BindView(R.id.tb_no)
    TextView tbNo;
    @BindView(R.id.tb_name)
    TextView tbName;
    @BindView(R.id.tb_price)
    TextView tbPrice;
    @BindView(R.id.tb_totalPrice)
    TextView tbTotalPrice;
    @BindView(R.id.tableRow1)
    TableRow tableRow1;
    @BindView(R.id.itemModule)
    LinearLayout itemModule;
    @BindView(R.id.idSplitBill)
    ImageButton idSplitBill;
    @BindView(R.id.idClear)
    Button idClear;
    @BindView(R.id.idPrint)
    Button idPrint;
    @BindView(R.id.no_of_items)
    TextView noOfItems;
    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    @BindView(R.id.viewpagerTable)
    ViewPager viewpagerTable;
    @BindView(R.id.tabTables)
    TabLayout tabTables;
    @BindView(R.id.listview_items)
    ListView listviewItems;
    @BindView(R.id.kotPrint)
    ImageView kotPrint;
    @BindView(R.id.paymentBtn)
    RelativeLayout paymentBtn;
    @BindView(R.id.orders_layout)
    TextView ordersLayout;
    @BindView(R.id.products_layout)
    TextView productsLayout;
    @BindView(R.id.orders)
    LinearLayout orders;
    @BindView(R.id.topLayout)
    LinearLayout topLayout;
    @BindView(R.id.products)
    LinearLayout products;
    private RealmBrowser realmBrowser;
    private int currentAPIVersion = Build.VERSION.SDK_INT;
    private android.app.AlertDialog.Builder alertDlgBuilder;
    private BluetoothAdapter mBluetoothAdapter = null;
    public static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 123;
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;
    public static CieBluetoothPrinter mPrinter = CieBluetoothPrinter.INSTANCE;
    private static BluetoothDevice device;

    List<Realm_TableName> itemTableList;
    private ServiceHandler serviceHandler;
    private Boolean isInternetPresent = false;
    private SharedPreference sharedPreference;
    private Activity activity;
    private String tablename = "", itemCodeSearch = "", printMsg = "";
    TableOrdersSearchAdapter searchAdapter;
    private List<Realm_ItemList> searchItemList;
    private List<Realm_ItemList> mItemNameSearch;
    private boolean printerConnected = false;
    private RadioButton btnTakeaway, btnDinein, btnHomedelivery, btncash, btncard, btn_splitt_bill_two, btn_splitt_bill_three;
    private RelativeLayout billamount;
    private LinearLayout billtotal, payandtotal, cashpayment, cardpayment, billnumber, detail_customer, carddetailcust, check_splitt_two, check_splitt_three;
    private TextView finaltotal, customer_name, cardAmountTendered, customer_name_card, splittotalvalue, splitclear, split22, split33;
    private Double finalTotalAmount = 0.00;
    private Realm_Customer payCustomer;
    private Button button1, button2, button5, button10, button20, button50, button100, button500, button2000, buttonPaymentDone, buttonCardPayment;
    private ImageButton buttonbksp;
    private EditText edTenderedAmount, cardNo, split21, split31, split32;
    private Double cashAmt = 0.00, roundingVal = 0.00;
    private String paymentType = "", orderType = "", customerName = "", changeTableName = "", splitType = "";
    private Long customerId, dataFirst, dataSecond;
    String totalAmounToBePay = "", printType = "";
    List<String> splitByOrderList = new ArrayList<>();
    private Boolean isSplitOrder = false, isBillBy2 = false, isBillBy3 = false;
    private boolean orientatonChange= false;

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "onStart");
        Log.e(TAG, "++ ON START ++");


    }

    @Override
    protected void onResume() {
        mPrinter.onActivityResume();
        super.onResume();
       // getScreenOrientation();
        //activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Log.e(TAG, "onResume");

        realmBrowser = new RealmBrowser();
        realmBrowser.start();
        realmBrowser.showServerAddress(activity);


    }


    @Override
    protected void onPause() {
        mPrinter.onActivityPause();
        super.onPause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(TAG, "onReStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        UtilView.showLogCat(TAG, "onStop");
        if (realmBrowser != null) {
            realmBrowser.stop();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        initView();
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
                setContentView(R.layout.activity_home);
                ButterKnife.bind(this);
                initView();
                //callPortrait();
              Log.e("config4","config");
        }else  if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                setContentView(R.layout.activity_home);
                ButterKnife.bind(this);
                initView();
              //callLandscape();
              Log.e("config5","config");
        }
    }

    private void callLandscape() {
       // callViewPagerTable();
       // callViewPagerItems();

        ordersLayout.setEnabled(false);
        productsLayout.setEnabled(false);
        ordersLayout.setOnClickListener(null);
        productsLayout.setOnClickListener(null);
        ordersLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orders.setVisibility(View.GONE);
                products.setVisibility(View.GONE);
            }
        });

        productsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orders.setVisibility(View.GONE);
                products.setVisibility(View.VISIBLE);
            }
        });

       // Toast.makeText(activity, "land", Toast.LENGTH_SHORT).show();

        totalModule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subModule.setVisibility(View.VISIBLE);
                totalModule.setVisibility(View.GONE);
            }
        });
        subtotalModule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subModule.setVisibility(View.GONE);
                totalModule.setVisibility(View.VISIBLE);

            }
        });



        List<Realm_Customer> customers = RealmManager.createRestaurentDao().getCustomerData();
        if (customers != null && customers.size() > 0) {
            customerId = customers.get(0).getCustomerId();
            customerName = customers.get(0).getCustomerName();

        }
    }

    private void callPortrait() {
        //callViewPagerTable();
        //callViewPagerItems();

        products.setVisibility(View.VISIBLE);
        productsLayout.setTextColor(getResources().getColor(R.color.colorRed));
        ordersLayout.setTextColor(getResources().getColor(R.color.light_gray));
        ordersLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(activity, "ordersLayout", Toast.LENGTH_SHORT).show();
                productsLayout.setTextColor(getResources().getColor(R.color.light_gray));
                ordersLayout.setTextColor(getResources().getColor(R.color.colorRed));
                orders.setVisibility(View.VISIBLE);
                products.setVisibility(View.GONE);
            }
        });

        productsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Toast.makeText(activity, "productsLayout", Toast.LENGTH_SHORT).show();
                productsLayout.setTextColor(getResources().getColor(R.color.colorRed));
                ordersLayout.setTextColor(getResources().getColor(R.color.light_gray));
                orders.setVisibility(View.GONE);
                products.setVisibility(View.VISIBLE);
            }
        });

        //Toast.makeText(activity, "port", Toast.LENGTH_SHORT).show();

        totalModule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subModule.setVisibility(View.VISIBLE);
                totalModule.setVisibility(View.GONE);
            }
        });
        subtotalModule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subModule.setVisibility(View.GONE);
                totalModule.setVisibility(View.VISIBLE);

            }
        });



        List<Realm_Customer> customers = RealmManager.createRestaurentDao().getCustomerData();
        if (customers != null && customers.size() > 0) {
            customerId = customers.get(0).getCustomerId();
            customerName = customers.get(0).getCustomerName();

        }
    }


    private void initView() {
        activity = this;
        sharedPreference = SharedPreference.getInstance(activity);
        serviceHandler = new ServiceHandler(activity);
        RealmManager.open();
        UtilView.showLogCat(TAG, "relam " + RealmManager.open().getPath());

        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        toolbar.setTitle(getResources().getString(R.string.app_name));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ControlPanel_Activity.class);
                startActivity(intent);
            }
        });

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }
        callViewPagerTable();
        callViewPagerItems();

        getScreenOrientation();


    }
    public int getScreenOrientation()
    {
        Display getOrient = activity.getWindowManager().getDefaultDisplay();
        int orientation = Configuration.ORIENTATION_UNDEFINED;
        if(getOrient.getWidth()==getOrient.getHeight()){
            orientation = Configuration.ORIENTATION_SQUARE;
        } else{
            if(getOrient.getWidth() < getOrient.getHeight()){
                orientation = Configuration.ORIENTATION_PORTRAIT;
                Log.e("config1", "port");
                callPortrait();
            }else {
                orientation = Configuration.ORIENTATION_LANDSCAPE;
                Log.e("config2", "Land");
                callLandscape();
            }
        }
        return orientation;
    }

    private void callViewPagerItems() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewpager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewpager);
        adapter.notifyDataSetChanged();
    }

    @OnClick({R.id.idSplitBill, R.id.idClear, R.id.idPrint, R.id.kotPrint, R.id.paymentBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.idSplitBill:
                callMergeTable();
                break;
            case R.id.idClear:
                clearAll();
                break;
            case R.id.idPrint:
                if (!printerConnected) {
                    Toast.makeText(activity, "Printer not connected.", Toast.LENGTH_SHORT).show();
                } else {
                    callCustomerPrint();
                }

                break;
            case R.id.kotPrint:
                if (!printerConnected) {
                    final Dialog dialog = new Dialog(activity);
                    dialog.setContentView(R.layout.dialog_printer_conn_error_kot);
                    Button btnOk = dialog.findViewById(R.id.ok);
                    dialog.show();
                    btnOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                } else {
                    callKotPrint(tablename);
                }

                break;
            case R.id.paymentBtn:
                List<Realm_TableOrders> ordersList = RealmManager.createRestaurentDao().getRestaTableOrderList(tablename);

                if (ordersList != null && ordersList.size() > 0) {
                    callPaymentMethod();
                } else {
                    Toast.makeText(activity, "No items in cart.", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    private void callMergeTable() {
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.dialog_merge_table);
        Button mergerCancel = dialog.findViewById(R.id.merge_cancel);
        Button mergeApplay = dialog.findViewById(R.id.merge_apply);

        TextView current_tablename = dialog.findViewById(R.id.current_tablename);
        Spinner change_tablename = dialog.findViewById(R.id.change_table_spinner);
        dialog.show();
        current_tablename.setText("" + tablename);

        List<Realm_TableName> realmTableName = RealmManager.createRestaurentDao().getRestaMergeTableList(tablename);
        List<String> tables = new ArrayList<String>();
        tables.clear();
        if (realmTableName != null && realmTableName.size() > 0) {
            for (Realm_TableName realm_tableName : realmTableName) {
                tables.add(realm_tableName.getTableName());
            }

        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tables);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        change_tablename.setAdapter(dataAdapter);


        change_tablename.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                changeTableName = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        mergerCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        mergeApplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RealmManager.createRestaurentDao().updateChangeTableName(tablename, changeTableName);
                checkTableOrderList(tablename);
                dialog.dismiss();
            }
        });
    }

    private void callPaymentMethod() {
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.dialog_payment_details);
        btnTakeaway = dialog.findViewById(R.id.btnTakeaway);
        btnDinein = dialog.findViewById(R.id.btnDinein);
        btnHomedelivery = dialog.findViewById(R.id.btnHomedelivery);
        billamount = dialog.findViewById(R.id.billamount);
        billtotal = dialog.findViewById(R.id.billtotal);
        billnumber = dialog.findViewById(R.id.billnumber);
        payandtotal = dialog.findViewById(R.id.payandtotal);
        cashpayment = dialog.findViewById(R.id.cashpayment);
        cardpayment = dialog.findViewById(R.id.cardpayment);
        cardAmountTendered = dialog.findViewById(R.id.cardpay);
        cardNo = dialog.findViewById(R.id.cardnumber);
        detail_customer = dialog.findViewById(R.id.detail_customer);
        carddetailcust = dialog.findViewById(R.id.carddetailcust);

        finaltotal = dialog.findViewById(R.id.finaltotal);
        final TextView dueamount = dialog.findViewById(R.id.dueamount);
        final TextView duetext = dialog.findViewById(R.id.duetext);
        final TextView rupeesym = dialog.findViewById(R.id.rupeesym);
        final TextView split_bill_order = dialog.findViewById(R.id.split_bill);

        customer_name = dialog.findViewById(R.id.customer_name);
        customer_name_card = dialog.findViewById(R.id.customer_name_card);

        customer_name_card.setText(customerName);
        customer_name.setText(customerName);
        edTenderedAmount = dialog.findViewById(R.id.edTenderedAmount);

        button1 = dialog.findViewById(R.id.button1);
        button2 = dialog.findViewById(R.id.button2);
        button5 = dialog.findViewById(R.id.button5);
        button10 = dialog.findViewById(R.id.button10);
        button20 = dialog.findViewById(R.id.button20);
        button50 = dialog.findViewById(R.id.button50);
        button100 = dialog.findViewById(R.id.button100);
        button500 = dialog.findViewById(R.id.button500);
        button2000 = dialog.findViewById(R.id.button2000);
        buttonbksp = dialog.findViewById(R.id.buttonbksp);
        buttonPaymentDone = dialog.findViewById(R.id.buttonPaymentDone);
        buttonCardPayment = dialog.findViewById(R.id.buttonCardPayment);


        Button tenderAmountExact = dialog.findViewById(R.id.tenderAmount);


        ImageView close = dialog.findViewById(R.id.closetext);
        dialog.show();
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finalTotalAmount = 0.00;
            }
        });

        split_bill_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialogSplittBill = new Dialog(activity);
                dialogSplittBill.setContentView(R.layout.dialog_splitbill_details);
                dialogSplittBill.show();

                ImageView close_splitt = dialogSplittBill.findViewById(R.id.close_splitt);
                Button split_cancel = dialogSplittBill.findViewById(R.id.split_cancel);
                Button split_applay = dialogSplittBill.findViewById(R.id.split_apply);
                btn_splitt_bill_two = dialogSplittBill.findViewById(R.id.btn_splitt_bill_two);
                btn_splitt_bill_three = dialogSplittBill.findViewById(R.id.btn_splitt_bill_three);
                check_splitt_two = dialogSplittBill.findViewById(R.id.check_splitt_two);
                check_splitt_three = dialogSplittBill.findViewById(R.id.check_splitt_three);
                splittotalvalue = dialogSplittBill.findViewById(R.id.splittotalvalue);
                splitclear = dialogSplittBill.findViewById(R.id.splitclear);
                split21 = dialogSplittBill.findViewById(R.id.split21);
                split22 = dialogSplittBill.findViewById(R.id.split22);
                split31 = dialogSplittBill.findViewById(R.id.split31);
                split32 = dialogSplittBill.findViewById(R.id.split32);
                split33 = dialogSplittBill.findViewById(R.id.split33);

                splittotalvalue.setText("" + Double.valueOf(totalAmounToBePay).longValue());


                split_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogSplittBill.dismiss();
                    }
                });
                close_splitt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogSplittBill.dismiss();
                    }
                });
                splitByOrderList.clear();
                split_applay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isSplitOrder = true;

                        if (isBillBy2) {
                            final String spit21 = split21.getText().toString();
                            final String spit22 = split22.getText().toString();
                            splitByOrderList.add(spit21);
                            splitByOrderList.add(spit22);
                        } else if (isBillBy3) {
                            final String spit31 = split31.getText().toString();
                            final String spit32 = split31.getText().toString();
                            final String spit33 = split33.getText().toString();
                            splitByOrderList.add(spit31);
                            splitByOrderList.add(spit32);
                            splitByOrderList.add(spit33);
                        }

                        dialogSplittBill.dismiss();
                    }
                });


            }
        });

        detail_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, Activity_Customer.class);
                intent.putExtra("callingFor", "customer");
                intent.putExtra("customerPay", "customerData");
                startActivityForResult(intent, Constant.CUSTOMER_PAYMENT);
            }
        });

        carddetailcust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, Activity_Customer.class);
                intent.putExtra("callingFor", "customer");
                intent.putExtra("customerPay", "customerData");
                startActivityForResult(intent, Constant.CUSTOMER_PAYMENT);
            }
        });


        try {
            Double totalTaxAmount = 0.00;
            List<Realm_TableOrders> ordersList = RealmManager.createRestaurentDao().getRestaTableOrderList(tablename);

            if (ordersList != null && ordersList.size() > 0) {
                for (Realm_TableOrders orders : ordersList) {
                    totalTaxAmount += (orders.getUnitPrice() * orders.getQty() * orders.getTaxAmount()) / 100;
                    finalTotalAmount += orders.getUnitPrice() * orders.getQty() + totalTaxAmount;

                }
            }

            Log.e("dddddnormal", String.valueOf(finalTotalAmount));
            double number = finalTotalAmount;
            int decimal = (int) number;
            double fractional = number - decimal;
            Log.e("ddddddigits", String.valueOf(fractional));
            BigDecimal bd;
            if (fractional < 0.50) {
                totalAmounToBePay = String.format("%.2f", Math.floor(finalTotalAmount));
                bd = new BigDecimal(finalTotalAmount - Math.floor(finalTotalAmount));
                bd = bd.setScale(2, RoundingMode.HALF_UP);
                roundingVal = Double.valueOf(String.valueOf(bd));
            } else {
                totalAmounToBePay = String.format("%.2f", Math.ceil(finalTotalAmount));
                bd = new BigDecimal(finalTotalAmount - Math.ceil(finalTotalAmount));
                bd = bd.setScale(2, RoundingMode.HALF_DOWN);
                roundingVal = Double.valueOf(String.valueOf(bd));
            }

            cardAmountTendered.setText("" + Double.valueOf(totalAmounToBePay).longValue());
            finaltotal.setText("" + "" + Double.valueOf(totalAmounToBePay).longValue());
            dueamount.setText("-" + "" + Double.valueOf(totalAmounToBePay).longValue());
            tenderAmountExact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    edTenderedAmount.setText("" + Double.valueOf(totalAmounToBePay).longValue());
                    dueamount.setText("0");
                    dueamount.setTextColor(getResources().getColor(R.color.colorPrimary));
                    duetext.setTextColor(getResources().getColor(R.color.colorPrimary));
                    rupeesym.setTextColor(getResources().getColor(R.color.colorPrimary));
                    cashAmt = 0.00;
                }
            });


            buttonbksp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cashAmt = 0.00;
                    edTenderedAmount.setText("");
                }
            });

            buttonPaymentDone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (edTenderedAmount.getText().toString().equals("")) {
                        edTenderedAmount.setError("Field not empty");
                    } else {
                        edTenderedAmount.setError(null);

                        final Dialog dialogpay = new Dialog(activity);
                        dialogpay.setContentView(R.layout.dialog_printer_conn_error_payment);
                        final Button btnYes = dialogpay.findViewById(R.id.payYes);
                        final Button btnNo = dialogpay.findViewById(R.id.payNo);
                        dialogpay.show();
                        btnYes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (!printerConnected) {
                                    Toast.makeText(Activity_Home.this, "Printer not connected.", Toast.LENGTH_SHORT).show();
                                    dialogpay.dismiss();
                                    dialog.dismiss();
                                } else {


                                    if (isSplitOrder) {

                                        callSplittOrder();
                                    } else {
                                        callBillPrint();
                                    }
                                    dialogpay.dismiss();
                                    dialog.dismiss();
                                }
                            }
                        });
                        btnNo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                callOrderSave();
                                Toast.makeText(Activity_Home.this, "Payment save successfully.", Toast.LENGTH_SHORT).show();
                                dialogpay.dismiss();
                                dialog.dismiss();

                            }
                        });
                    }


                }
            });


            buttonCardPayment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog dialogpay = new Dialog(activity);
                    dialogpay.setContentView(R.layout.dialog_printer_conn_error_payment);
                    final Button btnYes = dialogpay.findViewById(R.id.payYes);
                    final Button btnNo = dialogpay.findViewById(R.id.payNo);
                    dialogpay.show();
                    btnYes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!printerConnected) {
                                Toast.makeText(Activity_Home.this, "Printer not connected.", Toast.LENGTH_SHORT).show();
                                dialogpay.dismiss();
                                dialog.dismiss();
                            } else {
                                dialogpay.dismiss();
                                callBillPrint();

                                dialog.dismiss();
                            }

                        }
                    });
                    btnNo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            callOrderSave();
                            Toast.makeText(Activity_Home.this, "Payment save successfully.", Toast.LENGTH_SHORT).show();
                            dialogpay.dismiss();
                            dialog.dismiss();


                        }
                    });
                }
            });

        } catch (Exception e) {
            Log.e("Payment Exception", e.toString());
        }

    }

    private void callSplittOrder() {
        Toast.makeText(activity, "splitt.. " + splitByOrderList.size(), Toast.LENGTH_SHORT).show();
        try {


            List<Realm_TableOrders> ordersList = RealmManager.createRestaurentDao().getRestaTableOrderList(tablename);
            if (ordersList != null && ordersList.size() > 0 && splitByOrderList != null && splitByOrderList.size() > 0) {
                RealmManager.createRestaurentDao().saveRestuarentCheckoutData(DataGenerator.generateRestaCheckoutOrders(ordersList, tablename, orderType, paymentType, customerId, customerName, roundingVal));

                for (int i = 0; i < splitByOrderList.size(); i++) {
                    Log.e("splitt." + i + " ", splitByOrderList.get(i));
                    if (printType != null && printType.equals(getResources().getString(R.string.printtype_3))) {
                        PrintReceipt.printPaymentSplittBillPrint3Inch(activity, mPrinter, ordersList, tablename, orderType, paymentType, roundingVal, splitByOrderList.get(i), i);
                    } else {
                        PrintReceipt.printPaymentSplittBillPrint2Inch(activity, mPrinter, ordersList, tablename, orderType, paymentType, roundingVal, splitByOrderList.get(i), i);
                    }
                }
                RealmManager.createRestaurentDao().deleteOrdersOfTable(tablename);
                RealmManager.createRestaurentDao().deleteOrdersOfTempTable(tablename);
                checkTableOrderList(tablename);
            }


        } catch (Exception e) {
            Log.e("splitt Bill print", e.toString());
        }
    }

    private void callOrderSave() {
        try {
            List<Realm_TableOrders> ordersList = RealmManager.createRestaurentDao().getRestaTableOrderList(tablename);

            if (ordersList != null && ordersList.size() > 0) {
                RealmManager.createRestaurentDao().saveRestuarentCheckoutData(DataGenerator.generateRestaCheckoutOrders(ordersList, tablename, orderType, paymentType, customerId, customerName, roundingVal));
                Long invoiceData = RealmManager.createRestaurentDao().getLastIndexCheckoutData();
                Log.e("invoiceData", String.valueOf(invoiceData));
                for (Realm_TableOrders orders : ordersList){
                    RealmManager.createRestaurentDao().saveRestuarentSalesItemDetails(DataGenerator.generateRestaSalesItemDetails(orders, tablename, orderType, paymentType, customerId, customerName, roundingVal,invoiceData));
                }
                RealmManager.createRestaurentDao().deleteOrdersOfTable(tablename);
                RealmManager.createRestaurentDao().deleteOrdersOfTempTable(tablename);
                checkTableOrderList(tablename);
            }
        } catch (Exception e) {
            Log.e("Bill print", e.toString());
        }
    }

    private void callBillPrint() {
        try {
            List<Realm_TableOrders> ordersList = RealmManager.createRestaurentDao().getRestaTableOrderList(tablename);

            if (ordersList != null && ordersList.size() > 0) {
                RealmManager.createRestaurentDao().saveRestuarentCheckoutData(DataGenerator.generateRestaCheckoutOrders(ordersList, tablename, orderType, paymentType, customerId, customerName, roundingVal));
                Long invoiceData = RealmManager.createRestaurentDao().getLastIndexCheckoutData();
                Log.e("invoiceData", String.valueOf(invoiceData));
               for (Realm_TableOrders orders : ordersList){
                   RealmManager.createRestaurentDao().saveRestuarentSalesItemDetails(DataGenerator.generateRestaSalesItemDetails(orders, tablename, orderType, paymentType, customerId, customerName, roundingVal,invoiceData));
               }

                if (printType != null && printType.equals(getResources().getString(R.string.printtype_3))) {
                    PrintReceipt.printPaymentBillPrint3Inch(activity, mPrinter, ordersList, tablename, orderType, paymentType, roundingVal);
                } else {
                    PrintReceipt.printPaymentBillPrint2Inch(activity, mPrinter, ordersList, tablename, orderType, paymentType, roundingVal);
                }

                RealmManager.createRestaurentDao().deleteOrdersOfTable(tablename);
                RealmManager.createRestaurentDao().deleteOrdersOfTempTable(tablename);
                checkTableOrderList(tablename);
            }
        } catch (Exception e) {
            Log.e("Bill print", e.toString());
        }
    }

    public void onButtonClicked(View view) {
        switch (view.getId()) {
            case R.id.button1:
                updateCashAmount(1.00);
                break;
            case R.id.button2:
                updateCashAmount(2.00);

                break;
            case R.id.button5:
                updateCashAmount(5.00);
                break;
            case R.id.button10:
                updateCashAmount(10.00);
                break;
            case R.id.button20:
                updateCashAmount(20.00);
                break;
            case R.id.button50:
                updateCashAmount(50.00);
                break;
            case R.id.button100:
                updateCashAmount(100.00);
                break;
            case R.id.button500:
                updateCashAmount(500.00);
                break;
            case R.id.button2000:
                updateCashAmount(2000.00);
                break;

        }

    }

    private void updateCashAmount(double cash) {
        cashAmt += cash;
        edTenderedAmount.setText("" + Double.valueOf(cashAmt).longValue());
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.btnTakeaway:
                if (checked)
                    orderType = "TakeAway";
                billtotal.setVisibility(View.VISIBLE);
                billnumber.setVisibility(View.VISIBLE);
                payandtotal.setVisibility(View.VISIBLE);
                break;
            case R.id.btnDinein:
                if (checked)
                    orderType = "Dine-in";
                billtotal.setVisibility(View.VISIBLE);
                billnumber.setVisibility(View.VISIBLE);
                payandtotal.setVisibility(View.VISIBLE);
                break;
            case R.id.btnHomedelivery:
                if (checked)
                    orderType = "Home Delivery";
                billtotal.setVisibility(View.VISIBLE);
                billnumber.setVisibility(View.VISIBLE);
                payandtotal.setVisibility(View.VISIBLE);
                break;
            case R.id.btncash:
                if (checked)
                    paymentType = "Cash";
                billtotal.setVisibility(View.VISIBLE);
                billnumber.setVisibility(View.VISIBLE);
                payandtotal.setVisibility(View.VISIBLE);
                cashpayment.setVisibility(View.VISIBLE);
                cardpayment.setVisibility(View.GONE);
                break;
            case R.id.btncard:
                if (checked)
                    paymentType = "Card";
                billtotal.setVisibility(View.VISIBLE);
                billnumber.setVisibility(View.VISIBLE);
                payandtotal.setVisibility(View.VISIBLE);
                cardpayment.setVisibility(View.VISIBLE);
                cashpayment.setVisibility(View.GONE);
                break;
            case R.id.btn_splitt_bill_two:
                if (checked)
                    splitType = "2";
                callSpliBillAmount();
                btn_splitt_bill_two.setChecked(true);
                check_splitt_two.setVisibility(View.VISIBLE);
                splitclear.setVisibility(View.VISIBLE);
                check_splitt_three.setVisibility(View.GONE);

                splitclear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btn_splitt_bill_two.setChecked(false);
                        check_splitt_three.setVisibility(View.GONE);
                        split21.setText("");
                        split22.setText("");
                        check_splitt_two.setVisibility(View.GONE);
                    }
                });
                isBillBy2 = true;
                break;
            case R.id.btn_splitt_bill_three:
                if (checked)
                    splitType = "3";
                callSplitBillAmount3();
                btn_splitt_bill_three.setChecked(true);
                check_splitt_three.setVisibility(View.VISIBLE);
                splitclear.setVisibility(View.VISIBLE);
                check_splitt_two.setVisibility(View.GONE);

                splitclear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btn_splitt_bill_three.setChecked(false);
                        check_splitt_two.setVisibility(View.GONE);
                        split31.setText("");
                        split32.setText("");
                        split33.setText("");
                        check_splitt_three.setVisibility(View.GONE);
                    }
                });
                isBillBy3 = true;

                break;

        }

    }

    private void callSpliBillAmount() {
        Long splitaAmount = Double.valueOf(totalAmounToBePay).longValue();
        Log.e("sdf123", String.valueOf(splitaAmount));
        if (splitaAmount % 2 == 0) {
            splitaAmount = splitaAmount / 2;
            split21.setText("" + splitaAmount);
            split22.setText("" + splitaAmount);
        } else {
            splitaAmount = splitaAmount / 2;
            split21.setText("" + splitaAmount);
            Log.e("sdf", String.valueOf(splitaAmount));
            Long two = splitaAmount + 1;
            Log.e("sdf1", String.valueOf(two));
            split22.setText("" + two);
        }


        split21.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    if (split21.getText().toString().trim().equals("") && s.length() == 0) {
                        split21.setError("Field not empty");
                    } else {
                        Long splitaAmount = Double.valueOf(totalAmounToBePay).longValue();
                        String splitAmt = split21.getText().toString().trim();
                        Long Amount = Long.valueOf(splitAmt);
                        Long two = splitaAmount - Amount;
                        split22.setText("" + two);

                    }
                } catch (Exception e) {
                    Log.e("sdfg", e.toString());
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        split21.setText("" + split21.getText().toString().trim());

    }

    private void callSplitBillAmount3() {
        Long splitaAmount = Double.valueOf(totalAmounToBePay).longValue();
        Log.e("splitAmount ", String.valueOf(splitaAmount));

        if (splitaAmount % 2 == 0) {
            splitaAmount = splitaAmount / 3;
            Log.e("splitAnt ", String.valueOf(splitaAmount));
            split31.setText("" + splitaAmount);
            split32.setText("" + splitaAmount);
            split33.setText("" + splitaAmount);
        } else {
            splitaAmount = splitaAmount / 3;
            split31.setText("" + splitaAmount);
            Long two = splitaAmount + 1;
            split32.setText("" + two);
            Long three = splitaAmount + 1;
            split33.setText("" + three);
        }

        split31.setText("" + split31.getText().toString().trim());
        split32.setText("" + split32.getText().toString().trim());
        split33.setText("" + split33.getText().toString().trim());

        split31.addTextChangedListener(new MyTextWatcher(split31));
        split32.addTextChangedListener(new MyTextWatcher(split32));

       /* split31.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    if (split31.getText().toString().trim().equals("") && s.length() == 0){
                        split31.setError("Field not empty");
                    }else{
                        split32.addTextChangedListener(null);

                        Long splitaAmount = Double.valueOf(totalAmounToBePay).longValue();
                        String splitAmt1 = split31.getText().toString().trim();
                        String splitAmt2 = split32.getText().toString().trim();
                        Long Amount31 = Long.valueOf(splitAmt1);
                        Long Amount32 = Long.valueOf(splitAmt2);
                      Long  dataFirst=  splitaAmount - Amount31 ;
                        if (dataFirst!=null){
                            if(dataFirst % 2 == 0){
                                dataFirst = dataFirst/2;
                                if (dataFirst!=null){
                                    split32.setText(""+dataFirst);
                                    split33.setText(""+dataFirst);
                                }

                            }else{
                                dataFirst = dataFirst/2;
                                if (dataFirst!=null){
                                    Long two = dataFirst;
                                    split32.setText(""+two);
                                    Long three = dataFirst+1;
                                    split33.setText(""+three);
                                }

                            }
                        }



                    }
                }catch (Exception e){
                    Log.e("sdfg",e.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                split32.addTextChangedListener(this);
            }
        });
        split32.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    if (split32.getText().toString().trim().equals("") && s.length() == 0){
                        split32.setError("Field not empty");
                    }else{

                        //split31.addTextChangedListener(null);

                        Long splitaAmount = Double.valueOf(totalAmounToBePay).longValue();
                        String splitAmt1 = split32.getText().toString().trim();
                        String splitAmt2 = split31.getText().toString().trim();
                        Long Amount31 = Long.valueOf(splitAmt1);
                        Long Amount32 = Long.valueOf(splitAmt2);
                      Long  dataSecond =  splitaAmount - Amount31 ;
                        Log.e("data1", String.valueOf(dataSecond));
                        Log.e("dasss", s.toString());
                        if (dataSecond!=null){
                            if(dataSecond % 2 == 0){
                                dataSecond = dataSecond/2;
                                split33.setText(""+dataSecond);
                                if (splitAmt2!=null && splitAmt2.equals("") ){
                                    split31.setText(""+dataSecond);
                                }


                            }else{
                                dataSecond = dataSecond/2;
                                Long two = dataSecond;
                                split31.setText(""+two);
                                Long three = dataSecond+1;
                                split33.setText(""+three);
                            }
                        }
                    }
                }catch (Exception e){
                    Log.e("sdfg",e.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                //split31.addTextChangedListener(this);
            }
        });*/


    }


    private void callCustomerPrint() {
        try {
            List<Realm_TableOrders> ordersList = RealmManager.createRestaurentDao().getRestaTableOrderList(tablename);

            if (ordersList != null && ordersList.size() > 0) {
                if (printType != null && printType.equals(getResources().getString(R.string.printtype_3))) {
                    PrintReceipt.printCustomerPrint3Inch(activity, mPrinter, ordersList, tablename);
                } else {
                    PrintReceipt.printCustomerPrint2Inch(activity, mPrinter, ordersList, tablename);
                }

            }
        } catch (Exception e) {
            Log.e("Customer order print", e.toString());
        }
    }

    private void callKotPrint(String tablename) {
        boolean printStatus = false;
        try {
            List<Realm_TableOrders> ordersList = RealmManager.createRestaurentDao().getRestaTableOrderList(tablename);

            if (ordersList != null && ordersList.size() > 0) {
                callKotOrder(ordersList);
            }
        } catch (Exception e) {
            Log.e("Kot order exe", e.toString());
        }
    }

    private void callKotOrder(List<Realm_TableOrders> ordersList) {
        List<Realm_TableOrders> kotOrderList = new ArrayList<>();
        kotOrderList.clear();
        boolean found = false;
        for (Realm_TableOrders norders : ordersList) {
            Realm_TableOrders kotOrders = new Realm_TableOrders();
            Realm_Temp_TableOrders tempOrder = RealmManager.createRestaurentDao().getRestaTempTableOrderData(tablename, norders);

            if (tempOrder != null) {
                if (tempOrder.getItemName().equals(norders.getItemName()) && tempOrder.getTableName().equals(norders.getTableName()) && tempOrder.getQty() == norders.getQty()) {
                    found = true;
                    kotOrders.setQty(0.00);
                }
                if (!found) {
                    if (norders.getQty() > tempOrder.getQty()) {
                        kotOrders.setQty(norders.getQty() - tempOrder.getQty());

                        //Log.e("itemname1", norders.getItemName() + "Qty" + kotOrders.getQty());
                    } else if (norders.getQty() < tempOrder.getQty()) {
                        kotOrders.setQty(norders.getQty() - tempOrder.getQty());
                        //Log.e("itemname2", norders.getItemName() + "Qty" + kotOrders.getQty());
                    }
                }
            } else {

                  /*  if (norders.getQty() != tempOrder.getQty() && norders.getQty() > tempOrder.getQty()) {
                        kotOrders.setQty(norders.getQty() - tempOrder.getQty());
                    } else {
                        kotOrders.setQty(norders.getQty());
                    }*/

                if (norders.getQty() != 0.00) {
                    kotOrders.setQty(norders.getQty());
                }

            }
            kotOrders.setId(norders.getId());
            kotOrders.setItemName(norders.getItemName());
            kotOrders.setItemCode(norders.getItemCode());
            kotOrders.setUnitPrice(norders.getUnitPrice());
            if (kotOrders.getQty() != null && kotOrders.getQty() != 0.00) {
                kotOrderList.add(kotOrders);
            }
        }
        if (kotOrderList != null && kotOrderList.size() > 0) {
            for (Realm_TableOrders kkk : kotOrderList) {
                Log.e("itemname3", kkk.getItemName() + " Qty" + kkk.getQty());
            }
            if (printType != null && printType.equals(getResources().getString(R.string.printtype_3))) {
                PrintReceipt.printKot3Inch(activity, mPrinter, kotOrderList, tablename);
            } else {
                PrintReceipt.printKot2Inch(activity, mPrinter, kotOrderList, tablename);
            }

            for (Realm_TableOrders tableOrders : ordersList) {
                RealmManager.createRestaurentDao().updateKotStatus(tableOrders);
                RealmManager.createRestaurentDao().saveRestuarentTempTableOrders(DataGenerator.generateRestaTemptableOrders(tableOrders, tablename), tableOrders, tablename);
                checkTableOrderList(tablename);
            }
        } else {
            Toast.makeText(activity, "No changes in order", Toast.LENGTH_SHORT).show();
        }
    }


    private void clearAll() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_clear_all_items);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        ImageView btnClose = dialog.findViewById(R.id.close);

        Button btnSubmit = dialog.findViewById(R.id.btnClear);
        dialog.show();
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RealmManager.createRestaurentDao().deleteOrdersOfTable(tablename);
                checkTableOrderList(tablename);
                dialog.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }


    private void callViewPagerTable() {

        ViewPagerTableAdapter adapterTable = new ViewPagerTableAdapter(getSupportFragmentManager());
        viewpagerTable.setAdapter(adapterTable);
        tabTables.setupWithViewPager(viewpagerTable);

        callDefaultTable(viewpagerTable.getCurrentItem());

        viewpagerTable.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int positions) {
                if (itemTableList != null && itemTableList.size() > 0) {
                    //Toast.makeText(activity,  itemTableList.get(positions)+" selected.", Toast.LENGTH_SHORT).show();
                    tablename = itemTableList.get(positions).getTableName();
                    sharedPreference.saveData(Constant.TABLENAMEDATA, tablename);
                    checkTableOrderList(tablename);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    public class ViewPagerTableAdapter extends FragmentPagerAdapter {


        public ViewPagerTableAdapter(FragmentManager manager) {
            super(manager);
            itemTableList = RealmManager.createRestaurentDao().getRestaTableList();
        }


        @Override
        public Fragment getItem(int position) {
            return new ViewFragment();
        }

        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public int getCount() {
            return itemTableList.size();
        }


        @Override
        public CharSequence getPageTitle(int position) {
            String title = null;
            title = itemTableList.get(position).getTableName();
            return title;
        }
    }

    private void callDefaultTable(int pos) {
        if (pos == 0) {
            if (itemTableList != null && itemTableList.size() > 0) {
                // Toast.makeText(activity,  itemTableList.get(pos)+" selected.", Toast.LENGTH_SHORT).show();
                tablename = itemTableList.get(pos).getTableName();
                sharedPreference.saveData(Constant.TABLENAMEDATA, tablename);
                checkTableOrderList(tablename);
            }


        }
    }


    public class ViewPagerAdapter extends FragmentPagerAdapter {
        List<ItemCategory> itemCateList;

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
            itemCateList = RealmManager.createRestaurentDao().getRestaCategoryList();
        }

        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public Fragment getItem(int position) {
            return ViewFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return itemCateList.size();
        }


        @Override
        public CharSequence getPageTitle(int position) {
            String title = null;
            title = itemCateList.get(position).getItemCategoryName();
            return title;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_search:
                loadToolBarSearch();
                return true;
            case R.id.action_bluetooth_off:
                printerSetup();
                return true;
            case R.id.action_logout:
                logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (Build.VERSION.SDK_INT > 11) {
            invalidateOptionsMenu();

            if (printerConnected) {
                MenuItem settingsItems = menu.findItem(R.id.action_printer);
                settingsItems.setIcon(getResources().getDrawable(R.drawable.ic_printer_connected_black_24dp));

                MenuItem settingsItem = menu.findItem(R.id.action_bluetooth_off);
                settingsItem.setIcon(getResources().getDrawable(R.drawable.ic_on_status_24dp));

            } else {
                MenuItem settingsItems = menu.findItem(R.id.action_printer);
                settingsItems.setIcon(getResources().getDrawable(R.drawable.ic_printer_not_connected_black_24dp));

                MenuItem settingsItem = menu.findItem(R.id.action_bluetooth_off);
                settingsItem.setIcon(getResources().getDrawable(R.drawable.ic_off_status_24dp));

            }

        }
        return super.onPrepareOptionsMenu(menu);
    }

    private void printerSetup() {

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // If the adapter is null, then Bluetooth is not available in your device
        if (mBluetoothAdapter == null) {
            UtilView.showToast(activity, "Bluetooth is not available");
        }

        if (mBluetoothAdapter != null) {
            if (!mBluetoothAdapter.isEnabled()) {
                try {
                    Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
                    // Otherwise, setup the chat session
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    mPrinter.disconnectFromPrinter();
                    mPrinter.onActivityDestroy();
                    sharedPreference.setRemovePrefrence(Constant.BLUETOOTHADDRESS);
                    sharedPreference.setRemovePrefrence(Constant.BLUETOOTHTYPE);
                    Intent serverIntent = new Intent(activity, ListActivity.class);
                    startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //mPrinter.onActivityResult(requestCode, resultCode, this);
        switch (requestCode) {
            case REQUEST_CONNECT_DEVICE:
                if (resultCode == Activity.RESULT_OK) {
                    mPrinter.initService(Activity_Home.this, mMessenger);
                    String address = data.getExtras().getString(ListActivity.EXTRA_DEVICE_ADDRESS);
                    printType = data.getExtras().getString(ListActivity.EXTRA_PRINTER_TYPE);

                    mPrinter.connectToPrinter(address);
                    Log.e("address", address);
                    Log.e("printerType", printType);
                    // Get the BLuetoothDevice object
                    //sharedPreference.saveData(Constant.BLUETOOTHADDRESS, address);
                    //sharedPreference.saveData(Constant.BLUETOOTHTYPE, printerType);
                    device = mBluetoothAdapter.getRemoteDevice(address);


                }
                break;
            case REQUEST_ENABLE_BT:
                // When the request to enable Bluetooth returns
                if (resultCode == Activity.RESULT_OK) {
                    // Bluetooth is now enabled, so set up a chat session
                    initializeBluetoothDevice();
                } else {
                    // User did not enable Bluetooth or an error occured
                    Log.d(TAG, "BT not enabled");

                }

                break;
            case Constant.CUSTOMER_PAYMENT:
                if (resultCode == Activity.RESULT_OK) {
                    payCustomer = (Realm_Customer) data.getSerializableExtra("custData");
                    if (payCustomer != null && payCustomer.getCustomerName() != null && !payCustomer.getCustomerName().equals("")) {
                        customer_name.setText(payCustomer.getCustomerName());
                        customer_name_card.setText(payCustomer.getCustomerName());
                        customerName = payCustomer.getCustomerName();
                        customerId = payCustomer.getCustomerId();
                        // Toast.makeText(activity, ""+payCustomer.getCustomerName(), Toast.LENGTH_SHORT).show();

                    } else {
                        customer_name.setText("Customer");

                    }

                }

                break;

        }
    }


    private void initializeBluetoothDevice() {
        Log.e(TAG, "setupChat()");

        mPrinter.initService(Activity_Home.this, mMessenger);


    }

    @Override
    protected void onDestroy() {
        mPrinter.disconnectFromPrinter();
        mPrinter.onActivityDestroy();
        super.onDestroy();
    }

    final Messenger mMessenger = new Messenger(new PrintSrvMsgHandler());
    private String mConnectedDeviceName = "";

    private class PrintSrvMsgHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CieBluetoothPrinter.MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case CieBluetoothPrinter.STATE_CONNECTED:
                            setStatusMsg("connected: " + mConnectedDeviceName);
                            break;
                        case CieBluetoothPrinter.STATE_CONNECTING:
                            setStatusMsg("connecting...");
                            break;
                        case CieBluetoothPrinter.STATE_LISTEN:
                            setStatusMsg("listening..." + mConnectedDeviceName);
                            break;
                        case CieBluetoothPrinter.STATE_NONE:
                            setStatusMsg("not connected");
                            break;
                    }
                    break;
                case CieBluetoothPrinter.MESSAGE_DEVICE_NAME:
                    mConnectedDeviceName = msg.getData().getString(
                            CieBluetoothPrinter.DEVICE_NAME);
                    break;
                case CieBluetoothPrinter.MESSAGE_STATUS:
                    setStatusMsg(msg.getData().getString(
                            CieBluetoothPrinter.STATUS_TEXT));
                    break;
                case CieBluetoothPrinter.PRINT_COMPLETE:
                    setStatusMsg("PRINT OK");
                    break;
                case CieBluetoothPrinter.PRINTER_CONNECTION_CLOSED:
                    setStatusMsg("Printer Connection closed");
                    break;
                case CieBluetoothPrinter.PRINTER_DISCONNECTED:
                    setStatusMsg("Printer Connection failed");
                    break;
                default:
                    setStatusMsg("Some un handled message : " + msg.what);
                    super.handleMessage(msg);
                    break;
            }
        }
    }

    private void setStatusMsg(String msg) {
        Log.e("msg", msg);
        boolean isFound = msg.indexOf("connected:") != -1 ? true : false;
        if (isFound) {
            printerConnected = true;
            String substr = msg.substring(11, msg.length() - 1);
            Log.e("substr", substr);
            Toast.makeText(activity, "" + substr + " connected.", Toast.LENGTH_SHORT).show();
        }
    }

    public void logout() {

        isInternetPresent = serviceHandler.isConnectingToInternet();
        if (isInternetPresent) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(Constant.ACCESSTOKEN, sharedPreference.getData(Constant.ACCESSTOKEN));
                UtilView.showLogCat(TAG, sharedPreference.getData(Constant.ACCESSTOKEN));

                String url = UtilView.getUrl(activity);
                if (url != null) {

                    UtilView.showProgessBar(activity, progressBar);
                    PostDataTask postDataTask = new PostDataTask(activity, new AsyncTaskCompleteListener<String>() {
                        @Override
                        public void onTaskComplete(String result) {
                            UtilView.hideProgessBar(activity, progressBar);
                            if (result != null) {
                                if (result.equals("invalid")) {
                                    UtilView.showToast(activity, getResources().getString(R.string.accesstoken_error));
                                    Intent intent = new Intent(activity, Activity_Login.class);
                                    startActivity(intent);
                                    activity.finish();
                                } else {
                                    try {
                                        JSONObject jsonObject = new JSONObject(result.toString());
                                        if (jsonObject.getString("message").equals("success")) {
                                            UtilView.gotToLogin(activity);
                                        } else {
                                            UtilView.showToast(activity, "Some error in logout.");
                                        }
                                    } catch (Exception e) {
                                        UtilView.showLogCat(TAG, "DataTaskListener Exception " + e.toString());
                                    }
                                }
                            } else {
                                Toast.makeText(activity, getResources().getString(R.string.response_error), Toast.LENGTH_SHORT).show();

                            }
                        }

                    }, false);
                    postDataTask.execute(jsonObject.toString(), url + "/hipos/" + Constant.FUNTION_LOGOUT, "");

                } else {
                    UtilView.gotToLogin(activity);
                }
            } catch (Exception e) {

            }
        } else {

            Toast.makeText(activity, getResources().getString(R.string.intertnet_status), Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to Exit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    finishAffinity();
                }
                System.exit(0);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }


    public void checkTableOrderList(String tableName) {

        final List<Realm_TableOrders> tableOrdersList = RealmManager.createRestaurentDao().getRestaTableOrderList(tableName);

        Log.e("tableOrdersListsize", String.valueOf(tableOrdersList.size()));
        if (tableOrdersList != null && tableOrdersList.size() > 0) {
            OrdersAdapter adapter = new OrdersAdapter(activity, tableOrdersList);
            listviewItems.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            listviewItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //Toast.makeText(activity, ""+tableOrdersList.get(position).getItemName(), Toast.LENGTH_SHORT).show();
                    callEditItemDailog(tableOrdersList.get(position));
                }
            });

            Double total = 0.00, taotalQty = 0.00;


            tablename = tableOrdersList.get(0).getTableName();
            for (int i = 0; i < tableOrdersList.size(); i++) {

                total += tableOrdersList.get(i).getTotalPrice();
                taotalQty += tableOrdersList.get(i).getQty();
            }

            try {

                tvTotalAmount.setText("" + total.longValue());
                tvSubTotalAmount.setText("" + total.longValue());
                tvSubAmount.setText("" + total.longValue());
                noOfItems.setText("" + taotalQty.longValue());
            } catch (Exception e) {

            }

        } else if (tableOrdersList != null && tableOrdersList.size() == 0) {
            //listviewItems.setVisibility(View.GONE);
            OrdersAdapter adapter = new OrdersAdapter(activity, tableOrdersList);
            listviewItems.setAdapter(adapter);
            adapter.notifyDataSetChanged();

            Double total = 0.00, taotalQty = 0.00;
            for (int i = 0; i < tableOrdersList.size(); i++) {
                total += tableOrdersList.get(i).getTotalPrice();
                taotalQty += tableOrdersList.get(i).getQty();
            }

            try {

                tvTotalAmount.setText("" + total.longValue());
                tvSubTotalAmount.setText("" + total.longValue());
                tvSubAmount.setText("" + total.longValue());
                noOfItems.setText("" + taotalQty.longValue());
            } catch (Exception e) {

            }
        }


    }

    private void callEditItemDailog(final Realm_TableOrders realm_tableOrders) {

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.edit_table_order_items_dialog);
        final TextInputEditText edQty = dialog.findViewById(R.id.edit_item_qty);
        TextView edItemName = dialog.findViewById(R.id.edit_item_name);
        final TextView edTotalPrice = dialog.findViewById(R.id.edit_item_total_price);

        ImageButton btnIncQty = dialog.findViewById(R.id.btn_inc_qty);
        ImageButton btnRemQty = dialog.findViewById(R.id.btn_rem_qty);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        Button btnSubmit = dialog.findViewById(R.id.btnSubmit);
        dialog.show();

        if (realm_tableOrders != null) {
            edItemName.setText(realm_tableOrders.getItemName());
            edQty.setText("" + realm_tableOrders.getQty());
            edTotalPrice.setText("" + realm_tableOrders.getUnitPrice());
            btnIncQty.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Double qty = 0.00, price = 0.00;
                    if (edQty.getText().toString().trim().equals("")) {

                    } else {
                        qty = Double.valueOf(edQty.getText().toString().trim());
                        qty = qty + 1;
                        edQty.setText("" + qty);
                        if (realm_tableOrders.getUnitPrice() != null) {
                            price = realm_tableOrders.getUnitPrice();
                            price = price * qty;
                            edTotalPrice.setText("" + price);
                        }

                    }


                }
            });
            btnRemQty.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Double qty = 0.00, price = 0.00;
                    if (edQty.getText().toString().trim().equals("")) {

                    } else if (Double.valueOf(edQty.getText().toString().trim()) > 0.00) {
                        qty = Double.valueOf(edQty.getText().toString().trim());
                        qty = qty - 1;
                        edQty.setText("" + qty);

                        if (realm_tableOrders.getUnitPrice() != null) {
                            price = realm_tableOrders.getUnitPrice();
                            price = price * qty;
                            edTotalPrice.setText("" + price);
                        }
                    }

                }
            });
        }


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Realm_TableOrders tableOrders = new Realm_TableOrders();
                    tableOrders.setUnitPrice(realm_tableOrders.getUnitPrice());
                    tableOrders.setQty(Double.valueOf(edQty.getText().toString().trim()));
                    tableOrders.setSalesPrice(tableOrders.getUnitPrice());
                    tableOrders.setTotalPrice(Double.valueOf(edTotalPrice.getText().toString().trim()));
                    tableOrders.setTableName(realm_tableOrders.getTableName());
                    RealmManager.createRestaurentDao().updateItemQty(tableOrders, realm_tableOrders);
                    checkTableOrderList(tableOrders.getTableName());
                    dialog.dismiss();
                } catch (Exception e) {
                    Toast.makeText(activity, "Table Order Update failes.", Toast.LENGTH_SHORT).show();
                }


            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }


    public void loadToolBarSearch() {

        View view = Activity_Home.this.getLayoutInflater().inflate(R.layout.view_toolbar_search, null);
        LinearLayout parentToolbarSearch = (LinearLayout) view.findViewById(R.id.parent_toolbar_search);
        ImageView imgToolBack = (ImageView) view.findViewById(R.id.img_tool_back);
        final EditText edtToolSearch = (EditText) view.findViewById(R.id.edt_tool_search);
        final ListView listSearch = (ListView) view.findViewById(R.id.list_search);
        final TextView txtEmpty = (TextView) view.findViewById(R.id.txt_empty);

        UtilView.setListViewHeightBasedOnChildren(listSearch);

        edtToolSearch.setHint("Search your ItemCode or ItemName");

        final Dialog toolbarSearchDialog = new Dialog(Activity_Home.this, R.style.MaterialSearch);
        toolbarSearchDialog.setContentView(view);
        toolbarSearchDialog.setCancelable(false);
        toolbarSearchDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        toolbarSearchDialog.getWindow().setGravity(Gravity.BOTTOM);
        toolbarSearchDialog.show();

        toolbarSearchDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        searchItemList = RealmManager.createRestaurentDao().getRestaAllTableOrderList();

        if (searchItemList != null && searchItemList.size() > 0) {
            searchAdapter = new TableOrdersSearchAdapter(Activity_Home.this, searchItemList, false);
            listSearch.setVisibility(View.VISIBLE);
            listSearch.setAdapter(searchAdapter);
        }


        listSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                String itemname = String.valueOf(adapterView.getItemAtPosition(position));
                SharedPreference.addList(Activity_Home.this, UtilView.PREFS_NAME, UtilView.KEY_ITEMNAME, itemname);
                edtToolSearch.setText(itemname);
                listSearch.setVisibility(View.GONE);
                itemCodeSearch = edtToolSearch.getText().toString().trim();
                //Toast.makeText(Activity_Home.this, ""+itemCodeSearch, Toast.LENGTH_SHORT).show();
                callSearchItemName(itemCodeSearch);
                toolbarSearchDialog.dismiss();
            }
        });
        edtToolSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                mItemNameSearch = searchItemList;
                listSearch.setVisibility(View.VISIBLE);
                searchAdapter.updateList(mItemNameSearch, true);


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                List<Realm_ItemList> filterList = new ArrayList<Realm_ItemList>();
                boolean isNodata = false;
                if (s.length() > 0) {
                    for (int i = 0; i < mItemNameSearch.size(); i++) {


                        if (mItemNameSearch.get(i).getItemName().toLowerCase().startsWith(s.toString().trim().toLowerCase()) || mItemNameSearch.get(i).getItemCode().toLowerCase().startsWith(s.toString().trim().toLowerCase())) {

                            filterList.add(mItemNameSearch.get(i));

                            listSearch.setVisibility(View.VISIBLE);
                            searchAdapter.updateList(filterList, true);
                            isNodata = true;
                        }
                    }
                    if (!isNodata) {
                        listSearch.setVisibility(View.GONE);
                        txtEmpty.setVisibility(View.VISIBLE);
                        txtEmpty.setText("No data found");
                    }
                } else {
                    listSearch.setVisibility(View.GONE);
                    txtEmpty.setVisibility(View.GONE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        imgToolBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toolbarSearchDialog.dismiss();
            }
        });


    }

    private void callSearchItemName(String itemCodeSearch) {

        if (searchItemList != null && searchItemList.size() > 0) {
            List<Realm_ItemList> filterItemName = new ArrayList<>();
            List<Realm_ItemList> filterItemCode = new ArrayList<>();

            for (Realm_ItemList itemsList : searchItemList) {
                if (itemsList.getItemName().equalsIgnoreCase(itemCodeSearch)) {
                    filterItemName.add(itemsList);
                }
            }

            for (Realm_ItemList itemsList : searchItemList) {
                if (itemsList.getItemCode().equalsIgnoreCase(itemCodeSearch)) {
                    filterItemCode.add(itemsList);
                }
            }

            if (filterItemName != null && filterItemName.size() > 0 || filterItemCode != null && filterItemCode.size() > 0) {

                if (filterItemName != null && filterItemName.size() > 0) {
                    for (int i = 0; i < filterItemName.size(); i++) {
                        RealmManager.createRestaurentDao().saveRestuarentTableOrders(DataGenerator.generateRestatableOrders(filterItemName.get(i), tablename), filterItemName.get(i), tablename);
                        Toast.makeText(Activity_Home.this, filterItemName.get(i) + "  added.", Toast.LENGTH_SHORT).show();
                    }


                } else if (filterItemCode != null && filterItemCode.size() > 0) {
                    for (int ic = 0; ic < filterItemName.size(); ic++) {
                        RealmManager.createRestaurentDao().saveRestuarentTableOrders(DataGenerator.generateRestatableOrders(filterItemCode.get(ic), tablename), filterItemCode.get(ic), tablename);
                        Toast.makeText(Activity_Home.this, filterItemCode.get(ic) + "  added.", Toast.LENGTH_SHORT).show();
                    }

                }

                checkTableOrderList(tablename);
            }


        }
    }


    private class MyTextWatcher implements TextWatcher {
        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            switch (view.getId()) {
                case R.id.split31:
                    call31(s);
                    break;
                case R.id.split32:
                    call32(s);
                    break;

            }
        }
    }


    private void call31(Editable str) {

        try {
            if (split31.getText().toString().trim().equals("")) {
                split31.setError("Field not empty");
            } else {
                // split32.addTextChangedListener(null);


                //split32.setText(""+split32.getText().toString().trim());
                //split33.setText(""+split33.getText().toString().trim());

                Long splitaAmount = Double.valueOf(totalAmounToBePay).longValue();
                String splitAmt1 = split31.getText().toString().trim();
                String splitAmt2 = split32.getText().toString().trim();
                Long Amount31 = Long.valueOf(splitAmt1);
                Long Amount32 = Long.valueOf(splitAmt2);
                Long dataFirst = splitaAmount - Amount31;
                if (dataFirst != null) {
                    if (dataFirst % 2 == 0) {
                        dataFirst = dataFirst / 2;
                        if (dataFirst != null) {
                            split32.setText("" + dataFirst);
                            split33.setText("" + dataFirst);
                        }

                    } else {
                        dataFirst = dataFirst / 2;
                        if (dataFirst != null) {
                            Long two = dataFirst;
                            split32.setText("" + two);
                            Long three = dataFirst + 1;
                            split33.setText("" + three);
                        }

                    }
                }

            }


        } catch (Exception e) {
            Log.e("sdfg", e.toString());
        }


    }

    private void call32(Editable str2) {
        try {
            if (split32.getText().toString().trim().equals("")) {
                split32.setError("Field not empty");
            } else {
                //split31.setText(""+split31.getText().toString().trim());
//                    split32.setText(""+split32.getText().toString().trim());
//                    split33.setText(""+split33.getText().toString().trim());

                Long splitaAmount = Double.valueOf(totalAmounToBePay).longValue();
                String splitAmt1 = str2.toString();
                String splitAmt2 = split31.getText().toString().trim();
                String splitAmt3 = split33.getText().toString().trim();
                Log.e("splitAmt1", String.valueOf(splitAmt1));
                Log.e("splitAmt2", String.valueOf(splitAmt2));
                //split31.setText(""+splitAmt2);
                Long Amount31 = Long.valueOf(splitAmt1);
                Long dataSecond = splitaAmount - (Long.valueOf(splitAmt2) + Amount31);
                Log.e("data1", String.valueOf(dataSecond));
                split33.setText("" + dataSecond);
      /*          if (dataSecond!=null){
                    if(dataSecond % 2 == 0){
                        dataSecond = dataSecond/2;
                        split33.setText(""+dataSecond);
                        //split31.setText(""+dataSecond);
                    }else{
                        dataSecond = dataSecond/2;
                        Long two = dataSecond;
                        //split31.setText(""+two);
                        Long three = dataSecond+1;
                        split33.setText(""+three);
                    }
                }*/

            }

        } catch (Exception e) {
            Log.e("sdfg", e.toString());
        }
    }
}
