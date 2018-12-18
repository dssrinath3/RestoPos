package hiaccounts.in.restopos.product_and_tax.fragment;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import hiaccounts.in.restopos.R;
import hiaccounts.in.restopos.application.realm_model.data.Realm_Discount;
import hiaccounts.in.restopos.application.realm_model.utils.DataGenerator;
import hiaccounts.in.restopos.application.realmdbmanager.RealmManager;
import hiaccounts.in.restopos.product_and_tax.activity.Activity_Products;
import hiaccounts.in.restopos.product_and_tax.adapter.DiscountListAdapter;

/**
 * srinath
 */
public class FragmentDiscount extends Fragment {


    @BindView(R.id.searchBar)
    MaterialSearchBar searchBar;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    Unbinder unbinder;
    @BindView(R.id.addFab)
    FloatingActionButton addFab;
    private DiscountListAdapter discountListAdapter;
    private Activity activity;
    TextInputLayout inputLayoutDiscName, inputLayoutVal;
    TextInputEditText edDiscountName,edDiscvalue;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (Activity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discount, container, false);
        unbinder = ButterKnife.bind(this, view);
        initComponetView(view);
        return view;
    }

    private void initComponetView(View view) {

        callDiscountAdapter();


        addFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callDiscountDialog();
            }
        });
    }

    private void callDiscountAdapter() {
        List<Realm_Discount> discountList = RealmManager.createRestaurentDao().getRestaDiscountListFilter();

        if (discountList != null && discountList.size() > 0) {
            Log.e("discountlistsize", String.valueOf(discountList.size()));
            final List<Realm_Discount> discountList1 = new ArrayList<>();

            for (Realm_Discount realmDiscount : discountList) {
                Realm_Discount data = new Realm_Discount();
                data.setId(realmDiscount.getId());
                data.setDiscountId(realmDiscount.getDiscountId());
                data.setDiscountName(realmDiscount.getDiscountName());
                data.setDiscountAmount(realmDiscount.getDiscountAmount());

                discountList1.add(data);
            }

            mRecyclerView.setLayoutManager(new LinearLayoutManager(activity));
            searchBar.setHint("Search..");
            searchBar.setSearchIcon(R.drawable.ic_action_search);
            if (discountList1 != null && discountList1.size() > 0) {
                discountListAdapter = new DiscountListAdapter(activity, discountList1);
                mRecyclerView.setAdapter(discountListAdapter);
            }

            searchBar.addTextChangeListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    discountListAdapter.getFilter().filter(charSequence);
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }
    }

    private boolean validateDiscVal() {
        if (edDiscvalue.getText().toString().trim().isEmpty()) {
            inputLayoutVal.setError(getString(R.string.err_validate_name));
           // requestFocus(edDiscvalue);
            return false;
        } else {
            inputLayoutVal.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateDiscName() {
        if (edDiscountName.getText().toString().trim().isEmpty()) {
            inputLayoutDiscName.setError(getString(R.string.err_validate_name));
           // requestFocus(edDiscountName);
            return false;
        } else {
            inputLayoutDiscName.setErrorEnabled(false);
        }

        return true;
    }
    private void callDiscountDialog() {
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.product_add_discount_dialog);
        inputLayoutDiscName = (TextInputLayout) dialog.findViewById(R.id.input_name);
        inputLayoutVal = (TextInputLayout) dialog.findViewById(R.id.input_val);
         edDiscountName = dialog.findViewById(R.id.discountname);
        edDiscvalue = dialog.findViewById(R.id.discvalue);

        edDiscountName.addTextChangedListener(new MyTextWatcher(edDiscountName));
        edDiscvalue.addTextChangedListener(new MyTextWatcher(edDiscvalue));
        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        Button btnSubmit = dialog.findViewById(R.id.btnSubmit);
        dialog.getWindow().setGravity(Gravity.RIGHT);
        dialog.show();


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.hide();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validateDiscName() && !validateDiscVal()) {
                    return;
                }else{
                    String discountName = edDiscountName.getText().toString().trim();
                    Double discountAmt = 0.00;
                    if (edDiscvalue.getText().toString().trim() == null && edDiscvalue.getText().toString().trim().equals("")) {
                        edDiscvalue.setError(null);
                    } else {
                        discountAmt = Double.valueOf(edDiscvalue.getText().toString());
                    }
                    RealmManager.createRestaurentDao().saveRestuarentDiscountData(DataGenerator.generateRestaDiscountItem(discountName, discountAmt));
                    callDiscountAdapter();
                    Toast.makeText(activity, "Discount Save Successfully.", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }




            }
        });
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
                case R.id.discountname:
                    validateDiscName();
                    break;
                case R.id.discvalue:
                    validateDiscVal();
                    break;

            }
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
