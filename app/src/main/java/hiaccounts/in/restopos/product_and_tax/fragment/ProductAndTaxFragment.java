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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import hiaccounts.in.restopos.R;
import hiaccounts.in.restopos.application.realm_model.data.Realm_Tax;
import hiaccounts.in.restopos.application.realm_model.utils.DataGenerator;
import hiaccounts.in.restopos.application.realmdbmanager.RealmManager;
import hiaccounts.in.restopos.product_and_tax.adapter.TaxListAdapter;

/**
 * srinath
 */
public class ProductAndTaxFragment extends Fragment {


    @BindView(R.id.searchBar)
    MaterialSearchBar searchBar;
    @BindView(R.id.id)
    TextView id;
    @BindView(R.id.tax_name)
    TextView taxName;
    @BindView(R.id.header)
    LinearLayout header;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    Unbinder unbinder;
    @BindView(R.id.addFab)
    FloatingActionButton addFab;
    private TaxListAdapter taxListAdapter;
    private Activity activity;
    TextInputLayout inputLayoutTaxName, inputLayoutVal,inputLayouthsncode;
    TextInputEditText edTaxname,edTaxvalue,edHsnCode;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (Activity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_and_tax, container, false);
        unbinder = ButterKnife.bind(this, view);
        initComponentView();

        return view;
    }

    private void initComponentView() {
        callTaxAdapter();

        addFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callTaxDialog();
            }
        });
    }

    private void callTaxAdapter() {
        List<Realm_Tax> taxList = RealmManager.createRestaurentDao().getRestaTaxListFilter();
        final List<Realm_Tax> taxList1 = new ArrayList<>();

        if (taxList != null && taxList.size() > 0) {
            Log.e("taxList1size", String.valueOf(taxList.size()));

            for (Realm_Tax realmTax : taxList) {
                Realm_Tax data = new Realm_Tax(0l, "", 0l, false);
                data.setId(realmTax.getId());
                data.setTaxId(realmTax.getTaxId());
                data.setTaxName(realmTax.getTaxName());
                data.setTaxAmount(realmTax.getTaxAmount());
                data.setHsnCode(realmTax.getHsnCode());
                taxList1.add(data);
            }

        }


        mRecyclerView.setLayoutManager(new LinearLayoutManager(activity));
        searchBar.setHint("Search..");
        searchBar.setSearchIcon(R.drawable.ic_action_search);
        if (taxList1 != null && taxList1.size() > 0) {
          /*  taxListAdapter = new TaxListAdapter(activity, taxList1, listener);
            mRecyclerView.setAdapter(taxListAdapter);*/
            mRecyclerView.setAdapter(taxListAdapter = new TaxListAdapter(activity,taxList1, new TaxListAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(Realm_Tax item) {
                    callEditTaxDailog(item);
                }


            }));
        }else {
            taxList1.clear();
            mRecyclerView.setAdapter(taxListAdapter = new TaxListAdapter(activity,taxList1, new TaxListAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(Realm_Tax item) {
                    callEditTaxDailog(item);
                }


            }));
        }

        searchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                taxListAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void callEditTaxDailog(final Realm_Tax item) {
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.product_edit_tax_dialog);
        inputLayoutTaxName = (TextInputLayout) dialog.findViewById(R.id.input_name);
        inputLayoutVal = (TextInputLayout) dialog.findViewById(R.id.input_val);
        inputLayouthsncode = (TextInputLayout) dialog.findViewById(R.id.input_hsncode);
        edTaxname = dialog.findViewById(R.id.taxname);
        edTaxvalue = dialog.findViewById(R.id.taxvalue);
        edHsnCode = dialog.findViewById(R.id.hsncode);
        edTaxname.addTextChangedListener(new MyTextWatcher(edTaxname));
        edTaxvalue.addTextChangedListener(new MyTextWatcher(edTaxvalue));

        Button btnDelete = dialog.findViewById(R.id.btnDelete);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        Button btnSubmit = dialog.findViewById(R.id.btnSubmit);
        dialog.getWindow().setGravity(Gravity.RIGHT);
        dialog.show();

        if (item!=null){
            if (item.getTaxName()!=null){
              edTaxname.setText(item.getTaxName());
            }
            if (item.getTaxAmount()!=null){
                edTaxvalue.setText(""+item.getTaxAmount());
            }
            if (item.getHsnCode()!=null){
                edHsnCode.setText(item.getHsnCode());
            }
        }

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String taxname = edTaxname.getText().toString().trim();
                String hsncode= edHsnCode.getText().toString().trim();
                if (!validateTableName() && !validateTabVal()) {
                    return;
                }else{
                    Double taxAmount = 0.00;
                    if (edTaxvalue.getText().toString().trim() == null && edTaxvalue.getText().toString().trim().equals("")) {
                        edTaxvalue.setError(null);
                    } else {
                        taxAmount = Double.valueOf(edTaxvalue.getText().toString());
                    }
                    RealmManager.createRestaurentDao().updateTaxDetails(taxname,hsncode,item);

                    callTaxAdapter();
                    dialog.dismiss();
                    Toast.makeText(activity, "Tax updated successfully.", Toast.LENGTH_SHORT).show();

                }

            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.hide();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RealmManager.createRestaurentDao().deleteTax(item);
                callTaxAdapter();
                Toast.makeText(activity, "table deleted.", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }

    private void callTaxDialog() {
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.product_add_tax_dialog);
        inputLayoutTaxName = (TextInputLayout) dialog.findViewById(R.id.input_name);
        inputLayoutVal = (TextInputLayout) dialog.findViewById(R.id.input_val);
        inputLayouthsncode = (TextInputLayout) dialog.findViewById(R.id.input_hsncode);
         edTaxname = dialog.findViewById(R.id.taxname);
        edTaxvalue = dialog.findViewById(R.id.taxvalue);
        edHsnCode = dialog.findViewById(R.id.hsncode);
        edTaxname.addTextChangedListener(new MyTextWatcher(edTaxname));
        edTaxvalue.addTextChangedListener(new MyTextWatcher(edTaxvalue));

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

                String taxname = edTaxname.getText().toString().trim();
                String hsncode= edHsnCode.getText().toString().trim();


                if (!validateTableName() && !validateTabVal()) {
                    return;
                }else{
                    Double taxAmount = 0.00;
                    if (edTaxvalue.getText().toString().trim() == null && edTaxvalue.getText().toString().trim().equals("")) {
                        edTaxvalue.setError(null);
                    } else {
                        taxAmount = Double.valueOf(edTaxvalue.getText().toString());
                    }
                    RealmManager.createRestaurentDao().saveRestuarentTaxData(DataGenerator.generateRestaTaxItem(taxname, taxAmount,hsncode));
                    callTaxAdapter();
                    dialog.dismiss();
                    Toast.makeText(activity, "Tax Save Successfully.", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }
    private boolean validateTabVal() {
        if (edTaxvalue.getText().toString().trim().isEmpty()) {
            inputLayoutVal.setError(getString(R.string.err_validate_name));
            // requestFocus(edDiscvalue);
            return false;
        } else {
            inputLayoutVal.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateTableName() {
        if (edTaxname.getText().toString().trim().isEmpty()) {
            inputLayoutTaxName.setError(getString(R.string.err_validate_name));
            // requestFocus(edDiscountName);
            return false;
        } else {
            inputLayoutTaxName.setErrorEnabled(false);
        }

        return true;
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
                    validateTableName();
                    break;
                case R.id.discvalue:
                    validateTabVal();
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
