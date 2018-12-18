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
import hiaccounts.in.restopos.application.realm_model.data.Realm_ItemCategoryList;
import hiaccounts.in.restopos.application.realm_model.utils.DataGenerator;
import hiaccounts.in.restopos.application.realmdbmanager.RealmManager;
import hiaccounts.in.restopos.product_and_tax.adapter.CategoryListAdapter;

/**
 * srinath
 */
public class FragmentCategory extends Fragment {
    @BindView(R.id.searchBar)
    MaterialSearchBar searchBar;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    Unbinder unbinder;
    @BindView(R.id.addFab)
    FloatingActionButton addFab;
    private CategoryListAdapter categoryListAdapter;
    private Activity activity;
    TextInputLayout inputLayoutCategoryName, inputLayoutVal;
    TextInputEditText edCategoryname,edpriority;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (Activity) context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        unbinder = ButterKnife.bind(this, view);
        initComponentView(view);
        return view;
    }

    private void initComponentView(View view) {

        callCategoryAdapter();





        addFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callCategoryDialog();
            }
        });
    }

    private void callCategoryAdapter() {
        List<Realm_ItemCategoryList> categoryLists = RealmManager.createRestaurentDao().getRestaCategoryListFilter();
        final List<Realm_ItemCategoryList> categoryLists1 = new ArrayList<>();
        if (categoryLists != null && categoryLists.size() > 0) {
            Log.e("categorylistsize", String.valueOf(categoryLists.size()));


            for (Realm_ItemCategoryList category : categoryLists) {
                Realm_ItemCategoryList data = new Realm_ItemCategoryList();
                data.setId(category.getId());
                data.setItemCategoryCode(category.getItemCategoryCode());
                data.setItemCategoryName(category.getItemCategoryName());
                data.setItemCategoryDesc(category.getItemCategoryDesc());
                data.setItemCategoryId(category.getItemCategoryId());
                data.setPriority(category.getPriority());
                categoryLists1.add(data);
            }
        }
        mRecyclerView.setLayoutManager(new LinearLayoutManager(activity));
        searchBar.setHint("Search..");
        searchBar.setSearchIcon(R.drawable.ic_action_search);
        if (categoryLists1 != null && categoryLists1.size() > 0) {
                /*categoryListAdapter = new CategoryListAdapter(activity, categoryLists1, listener);
                mRecyclerView.setAdapter(categoryListAdapter);*/
            mRecyclerView.setAdapter(categoryListAdapter = new CategoryListAdapter(activity,categoryLists1, new CategoryListAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(Realm_ItemCategoryList item) {
                    callEditCategoryDialog(item);
                }

            }));
        }else {
            categoryLists1.clear();
            mRecyclerView.setAdapter(categoryListAdapter = new CategoryListAdapter(activity,categoryLists1, new CategoryListAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(Realm_ItemCategoryList item) {
                    callEditCategoryDialog(item);
                }

            }));
        }

        searchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //SEARCH FILTER
                categoryListAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private void callEditCategoryDialog(final Realm_ItemCategoryList item) {
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.product_edit_category_dialog);
        inputLayoutCategoryName = (TextInputLayout) dialog.findViewById(R.id.input_name);
        inputLayoutVal = (TextInputLayout) dialog.findViewById(R.id.input_val);
        edCategoryname = dialog.findViewById(R.id.categoryname);
        edpriority = dialog.findViewById(R.id.priority);
        edCategoryname.addTextChangedListener(new MyTextWatcher(edCategoryname));
        edpriority.addTextChangedListener(new MyTextWatcher(edpriority));
        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        Button btnSubmit = dialog.findViewById(R.id.btnSubmit);
        Button btnDelete = dialog.findViewById(R.id.btnDelete);
        dialog.getWindow().setGravity(Gravity.RIGHT);
        dialog.show();


        if (item!=null){
            if (item.getItemCategoryName()!=null){
                edCategoryname.setText(item.getItemCategoryName());
            }

            if (item.getPriority()!=null){
                edpriority.setText(""+item.getPriority());
            }

        }

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.hide();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validateCategoryName()) {
                    return;
                }else{
                    String category = edCategoryname.getText().toString().trim();

                    Long priority =0l;
                    if (edpriority.getText().toString().trim() != null && !edpriority.getText().toString().trim().equals("")){
                        priority = Long.valueOf(edpriority.getText().toString());
                    }else {
                        priority = 0l;

                    }
                    RealmManager.createRestaurentDao().updateCategoryDetails(category,priority,item);
                    callCategoryAdapter();
                    dialog.dismiss();
                    Toast.makeText(activity, "Category updated successfully.", Toast.LENGTH_SHORT).show();

                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RealmManager.createRestaurentDao().deleteCategory(item);
                callCategoryAdapter();
                Toast.makeText(activity, "category deleted.", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }


    private void callCategoryDialog() {
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.product_add_category_dialog);
        inputLayoutCategoryName = (TextInputLayout) dialog.findViewById(R.id.input_name);
        inputLayoutVal = (TextInputLayout) dialog.findViewById(R.id.input_val);
        edCategoryname = dialog.findViewById(R.id.categoryname);
        edpriority = dialog.findViewById(R.id.priority);
        edCategoryname.addTextChangedListener(new MyTextWatcher(edCategoryname));
        edpriority.addTextChangedListener(new MyTextWatcher(edpriority));
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

                if (!validateCategoryName()) {
                    return;
                }else{
                    String category = edCategoryname.getText().toString().trim();

                    Long priority =0l;
                    if (edpriority.getText().toString().trim() != null && !edpriority.getText().toString().trim().equals("")){
                        priority = Long.valueOf(edpriority.getText().toString());
                    }else {
                        priority = 0l;

                    }
                    RealmManager.createRestaurentDao().saveRestuarentCategoryData(DataGenerator.generateRestaCategoryItem(category, priority), category);
                    callCategoryAdapter();
                    dialog.dismiss();
                    Toast.makeText(activity, "Category Save Successfully.", Toast.LENGTH_SHORT).show();

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
                case R.id.categoryname:
                    validateCategoryName();
                    break;
                case R.id.priority:
                    //validatePriority();
                    break;

            }
        }
    }

    private boolean validatePriority() {
        if (edpriority.getText().toString().trim().isEmpty()) {
            inputLayoutVal.setError(getString(R.string.err_validate_name));
            // requestFocus(edDiscvalue);
            return false;
        } else {
            inputLayoutVal.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateCategoryName() {
        if (edCategoryname.getText().toString().trim().isEmpty()) {
            inputLayoutCategoryName.setError(getString(R.string.err_validate_name));
            // requestFocus(edDiscountName);
            return false;
        } else {
            inputLayoutCategoryName.setErrorEnabled(false);
        }

        return true;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
