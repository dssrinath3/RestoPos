package hiaccounts.in.restopos.customer.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hiaccounts.in.restopos.R;
import hiaccounts.in.restopos.application.activity.Activity_Home;
import hiaccounts.in.restopos.application.realm_model.data.Realm_Customer;
import hiaccounts.in.restopos.application.realm_model.utils.DataGenerator;
import hiaccounts.in.restopos.application.realmdbmanager.RealmManager;
import hiaccounts.in.restopos.customer.adapter.CustomerListAdapter;
import hiaccounts.in.restopos.utility.UtilView;

public class Activity_Customer extends AppCompatActivity implements CustomerListAdapter.ItemListener {

    @BindView(R.id.searchBar)
    MaterialSearchBar searchBar;
    @BindView(R.id.header)
    LinearLayout header;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.addFab)
    FloatingActionButton addFab;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private Activity activity;
    TextInputLayout inputLayoutPhoneno, inputLayoutCustomerid, inputLayoutCustomername,inputLayoutEmailid,inputLayoutAddress,inputLayoutDate;
    TextInputEditText edPhoneno,edCustomerid,edCustomername,edEmailid,edAddress,edDate;
    String titleName="",customerName="";
    private CustomerListAdapter customerListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        activity =this;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        RealmManager.open();


        UtilView.showLogCat("@Flow", "relam " + RealmManager.open().getPath());

        ActionBar actionBar = getSupportActionBar();

        Intent in = getIntent();
        titleName =in.getStringExtra("callingFor");
        customerName = in.getStringExtra("customerPay");
        if (titleName!=null)
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

        callCustomerMethod();
        addFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCustomerDialog();
            }
        });
    }
    private void callCustomerMethod() {
        List<Realm_Customer> customerList = RealmManager.createRestaurentDao().getRestaCustomerListFilter();

        if (customerList != null && customerList.size() > 0) {
            Log.e("itemListssize", String.valueOf(customerList.size()));
            final List<Realm_Customer> customers = new ArrayList<>();
            for (Realm_Customer items : customerList) {
                Realm_Customer data = new Realm_Customer();
                Log.e("id", String.valueOf(items.getId()));
                Log.e("custid", String.valueOf(items.getCustomerId()));
                Log.e("custname", items.getCustomerName());
                Log.e("custphno", String.valueOf(items.getPhoneNumber()));
                Log.e("custAdd", items.getCustomerAddress());
                data.setId(items.getId());
                data.setCustomerId(items.getCustomerId());
                data.setCustomerName(items.getCustomerName());
                data.setPhoneNumber(items.getPhoneNumber());
                data.setCustomerAddress(items.getCustomerAddress());
                //data.setEmailId(items.getEmailId());
              //  data.setCustomerDate(items.getCustomerDate());
                customers.add(data);
            }

            mRecyclerView.setLayoutManager(new LinearLayoutManager(activity));
            searchBar.setHint("Search..");
            searchBar.setSearchIcon(R.drawable.ic_action_search);
            if (customers != null && customers.size() > 0) {
                customerListAdapter = new CustomerListAdapter(activity, customers,this);
                mRecyclerView.setAdapter(customerListAdapter);
            }

            searchBar.addTextChangeListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    customerListAdapter.getFilter().filter(charSequence);
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

        }
    }

    private void addCustomerDialog() {
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.add_customer_dialog);
        inputLayoutPhoneno = (TextInputLayout) dialog.findViewById(R.id.input_phone_no);
        inputLayoutCustomerid = (TextInputLayout) dialog.findViewById(R.id.input_customer_id);
        inputLayoutCustomername = (TextInputLayout) dialog.findViewById(R.id.input_customer_name);
        inputLayoutEmailid = (TextInputLayout) dialog.findViewById(R.id.input_email_id);
        inputLayoutAddress = (TextInputLayout) dialog.findViewById(R.id.input_address);
        inputLayoutDate = (TextInputLayout) dialog.findViewById(R.id.input_date);
        edPhoneno = dialog.findViewById(R.id.phone_no);
        edCustomerid = dialog.findViewById(R.id.customer_id);
        edCustomername = dialog.findViewById(R.id.customer_name);
        edEmailid = dialog.findViewById(R.id.email_id);
        edAddress = dialog.findViewById(R.id.customer_address);
        edDate  = dialog.findViewById(R.id.customer_date);

        edPhoneno.addTextChangedListener(new MyTextWatcher(edPhoneno));
        edCustomername.addTextChangedListener(new MyTextWatcher(edCustomername));
        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        Button btnSubmit = dialog.findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validatePhoneno() && !validateCustomername()) {
                    return;
                }else{
                    String phoneno = edPhoneno.getText().toString();
                    String customerId = edCustomerid.getText().toString();
                    String cusomerName = edCustomername.getText().toString();
                    String emailid = edEmailid.getText().toString();
                    String address = edAddress.getText().toString();
                    String custdate = edDate.getText().toString();
                    RealmManager.createRestaurentDao().saveRestuarentCustomerData(DataGenerator.generateRestaCustomer(phoneno, customerId,cusomerName,emailid,address,custdate));
                    callCustomerMethod();
                    dialog.dismiss();
                    Toast.makeText(activity, "Customer Save Successfully.", Toast.LENGTH_SHORT).show();

                }
            }
        });



        dialog.getWindow().setGravity(Gravity.RIGHT);
        dialog.show();

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.hide();
            }
        });
    }

    @Override
    public void onItemClick(Realm_Customer realmCustomer, View view) {
        if (customerName!=null && customerName.equals("customerData")){
            Intent in = new Intent(Activity_Customer.this, Activity_Home.class);
            in.putExtra("custData",realmCustomer);
            activity.setResult(Activity.RESULT_OK,in);
            finish();
        }
    }


    private class MyTextWatcher implements TextWatcher {
        private View view;
        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.phone_no:
                    validatePhoneno();
                    break;
                case R.id.customer_name:
                    validateCustomername();
                    break;

            }
        }
    }

    private boolean validateCustomername() {
        if (edCustomername.getText().toString().trim().isEmpty()) {
            inputLayoutCustomername.setError(getString(R.string.err_validate_name));
            // requestFocus(edCustomerName);
            return false;
        } else {
            inputLayoutCustomername.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePhoneno() {

        String MobilePattern = "[0-9]{10}";
        if (edPhoneno.getText().toString().trim().isEmpty()) {
            inputLayoutPhoneno.setError(getString(R.string.err_validate_name));
            // requestFocus(edPhoneno);
            return false;
        } else if (!edPhoneno.getText().toString().trim().matches(MobilePattern)){
            inputLayoutPhoneno.setError("Enter valid 10 digit phone no");
            return false;
        }else {
            inputLayoutPhoneno.setErrorEnabled(false);
        }

        return true;
    }
}
