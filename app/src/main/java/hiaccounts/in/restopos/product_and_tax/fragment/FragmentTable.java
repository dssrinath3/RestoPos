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
import hiaccounts.in.restopos.application.realm_model.data.Realm_TableName;
import hiaccounts.in.restopos.application.realm_model.utils.DataGenerator;
import hiaccounts.in.restopos.application.realmdbmanager.RealmManager;
import hiaccounts.in.restopos.product_and_tax.adapter.TableListAdapter;

/**
 * srinath
 */
public class FragmentTable extends Fragment {


    @BindView(R.id.searchBar)
    MaterialSearchBar searchBar;
    @BindView(R.id.addFab)
    FloatingActionButton addFab;
    Unbinder unbinder;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    private Activity activity;
    private TableListAdapter tableListAdapter;
    TextInputLayout inputLayoutTableName;
    TextInputEditText edTableName;
    String tableName="";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (Activity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_table, container, false);
        unbinder = ButterKnife.bind(this, view);
        initComponentView(view);
        return view;
    }

    private void initComponentView(View view) {
        callTableAdapter();
        addFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callTableDialog();
            }
        });
    }

    private void callTableAdapter() {
        List<Realm_TableName> tableNames = RealmManager.createRestaurentDao().getRestaTableListFilter();
        final List<Realm_TableName> tableNameList = new ArrayList<>();
        if (tableNames != null && tableNames.size() > 0) {
            Log.e("tableNameslistsize", String.valueOf(tableNames.size()));


            for (Realm_TableName realmTableName : tableNames) {
                Realm_TableName data = new Realm_TableName();
                data.setId(realmTableName.getId());
                data.setTableName(realmTableName.getTableName());

                tableNameList.add(data);
            }


        }
        mRecyclerView.setLayoutManager(new LinearLayoutManager(activity));
        searchBar.setHint("Search..");
        searchBar.setSearchIcon(R.drawable.ic_action_search);
        if (tableNameList != null && tableNameList.size() > 0) {
             /*   tableListAdapter = new TableListAdapter(activity, tableNameList);
                mRecyclerView.setAdapter(tableListAdapter);*/
            mRecyclerView.setAdapter(tableListAdapter = new TableListAdapter(activity,tableNameList, new TableListAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(Realm_TableName item) {
                    callEditTableDialog(item);
                }

            }));
        }else{
            tableNameList.clear();
            mRecyclerView.setAdapter(tableListAdapter = new TableListAdapter(activity,tableNameList, new TableListAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(Realm_TableName item) {
                    callEditTableDialog(item);
                }

            }));
        }

        searchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                tableListAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void callEditTableDialog(final Realm_TableName item) {
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.product_edit_table_dialog);
        inputLayoutTableName = dialog.findViewById(R.id.input_table);
        edTableName = dialog.findViewById(R.id.table_name);
        edTableName.addTextChangedListener(new MyTextWatcher(edTableName));

        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        Button btnSubmit = dialog.findViewById(R.id.btnSubmit);
        Button btnDelete = dialog.findViewById(R.id.btnDelete);
        dialog.getWindow().setGravity(Gravity.RIGHT);
        dialog.show();


        if (item!=null){
            edTableName.setText(item.getTableName());
        }

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateTableName()) {
                    return;
                }else{
                    tableName = edTableName.getText().toString().trim();
                    if (tableName!=null && !tableName.equals("")){
                        RealmManager.createRestaurentDao().updateTableDetails(tableName,item);
                        callTableAdapter();
                        dialog.dismiss();
                        Toast.makeText(activity, "Table updated successfully.", Toast.LENGTH_SHORT).show();

                    }
                }

            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RealmManager.createRestaurentDao().deleteTable(item);
                callTableAdapter();
                Toast.makeText(activity, "table deleted.", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });


    }


    private void callTableDialog() {
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.product_add_table_dialog);
        inputLayoutTableName = dialog.findViewById(R.id.input_table);
        edTableName = dialog.findViewById(R.id.table_name);
        edTableName.addTextChangedListener(new MyTextWatcher(edTableName));
        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        Button btnSubmit = dialog.findViewById(R.id.btnSubmit);
        dialog.getWindow().setGravity(Gravity.RIGHT);
        dialog.show();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateTableName()) {
                    return;
                }else{
                     tableName = edTableName.getText().toString().trim();
                     if (tableName!=null && !tableName.equals("")){
                         RealmManager.createRestaurentDao().saveRestuarentTableNameData(DataGenerator.generateRestaTable(tableName));
                         callTableAdapter();
                         dialog.dismiss();
                         Toast.makeText(activity, "Table save successfully.", Toast.LENGTH_SHORT).show();

                     }
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
    private boolean validateTableName() {
        if (edTableName.getText().toString().trim().isEmpty()) {
            inputLayoutTableName.setError(getString(R.string.err_validate_name));
            // requestFocus(edDiscountName);
            return false;
        } else {
            inputLayoutTableName.setErrorEnabled(false);
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
                case R.id.table_name:
                    validateTableName();
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
