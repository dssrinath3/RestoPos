package hiaccounts.in.restopos.product_and_tax.fragment;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.mancj.materialsearchbar.MaterialSearchBar;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import hiaccounts.in.restopos.R;
import hiaccounts.in.restopos.application.model.pageloaddata.ItemCategory;
import hiaccounts.in.restopos.application.realm_model.data.Realm_ItemCategoryList;
import hiaccounts.in.restopos.application.realm_model.data.Realm_ItemList;
import hiaccounts.in.restopos.application.realm_model.data.Realm_Tax;
import hiaccounts.in.restopos.application.realm_model.utils.DataGenerator;
import hiaccounts.in.restopos.application.realmdbmanager.RealmManager;
import hiaccounts.in.restopos.product_and_tax.activity.Activity_Products;
import hiaccounts.in.restopos.product_and_tax.adapter.CategoryAdapter;
import hiaccounts.in.restopos.product_and_tax.adapter.ItemListAdapter;
import hiaccounts.in.restopos.product_and_tax.adapter.ItemTaxListAdapter;

import static android.app.Activity.RESULT_CANCELED;

/**
 * srinath.
 */
public class FragmentItem extends Fragment {

    public  static final String TAG = FragmentItem.class.getSimpleName();
    private int STORAGE_PERMISSION_CODE_READ = 23;
    private int STORAGE_PERMISSION_CODE_WRITE = 24;
    private   File file=null;
    private static final String IMAGE_DIRECTORY = "/demonuts";
    private int GALLERY = 4, CAMERA = 2;
    @BindView(R.id.searchBar)
    MaterialSearchBar searchBar;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    Unbinder unbinder;
    @BindView(R.id.addFab)
    FloatingActionButton addFab;
    private ItemListAdapter itemListAdapter;
    private Activity activity;
    private Long categoryid;
    private String categoryname="";
    TextInputLayout inputLayoutItemName, inputLayoutQty, inputLayoutPrice,inputLayoutCategory;
    TextInputEditText edItemName,edQty,edPrice,edCategory,edItemtax;
    private String path="",taxName="",hsnCode;
    private Long taxId;
    private Double taxAmount=0.00,totalGstTaxAmt=0.00,editTaxAmount=0.00;
    //private ImageButton btnImage;
    private CircleImageView  btnImage;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (Activity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item, container, false);
        unbinder = ButterKnife.bind(this, view);
        initComponentView(view);
        return view;
    }

    private void initComponentView(View view) {
        callItemMethod();
        addFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callItemDialog();
            }
        });
    }

    private void callItemMethod() {
        List<Realm_ItemList> itemLists = RealmManager.createRestaurentDao().getRestaItemListFilter();

        if (itemLists != null && itemLists.size() > 0) {
            Log.e("itemListssize", String.valueOf(itemLists.size()));
            final List<Realm_ItemList> item = new ArrayList<>();
            item.clear();
            for (Realm_ItemList items : itemLists) {
                Realm_ItemList data = new Realm_ItemList();
                data.setId(items.getId());
                data.setItemCode(items.getItemCode());
                data.setItemName(items.getItemName());
                data.setUnitPrice(items.getUnitPrice());
                data.setQty(items.getQty());
                data.setSalesPrice(items.getSalesPrice());
                data.setItemDesc(items.getItemDesc());
                data.setItemCategoryId(items.getItemCategoryId());
                data.setItemCategoryName(items.getItemCategoryName());
                data.setImageLocation(items.getImageLocation());
                data.setTaxId(items.getTaxId());
                data.setTaxName(items.getTaxName());
                data.setTaxAmount(items.getTaxAmount());
                data.setHsnCode(items.getHsnCode());
                item.add(data);
            }

            mRecyclerView.setLayoutManager(new LinearLayoutManager(activity));
            searchBar.setHint("Search..");
            searchBar.setSearchIcon(R.drawable.ic_action_search);
            if (item != null && item.size() > 0) {
               /* itemListAdapter = new ItemListAdapter(activity, item, new ItemListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Realm_ItemList item) {
                        Toast.makeText(activity, ""+item.getItemName(), Toast.LENGTH_SHORT).show();
                    }


                });
                mRecyclerView.setAdapter(itemListAdapter);*/

                mRecyclerView.setAdapter(itemListAdapter = new ItemListAdapter(activity,item, new ItemListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Realm_ItemList item) {
                     //   Toast.makeText(activity, ""+item.getItemName(), Toast.LENGTH_SHORT).show();
                        callEditItemDialog(item);
                    }
                }));


            }

            searchBar.addTextChangeListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    itemListAdapter.getFilter().filter(charSequence);
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

        }
    }

    private void callEditItemDialog(final Realm_ItemList item) {
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.product_edit_item_dialog);
        inputLayoutItemName = (TextInputLayout) dialog.findViewById(R.id.input_item_name);
        inputLayoutQty = (TextInputLayout) dialog.findViewById(R.id.input_qty);
        inputLayoutPrice = (TextInputLayout) dialog.findViewById(R.id.input_price);
        inputLayoutCategory = (TextInputLayout) dialog.findViewById(R.id.input_category);
        edItemName = dialog.findViewById(R.id.itemname);
        edPrice = dialog.findViewById(R.id.price);
        edQty = dialog.findViewById(R.id.qty);
        edItemtax = dialog.findViewById(R.id.itemtax);
        edCategory = dialog.findViewById(R.id.id_category);
        //final TextInputEditText edBarcode = dialog.findViewById(R.id.barcode);
       // TextInputEditText edDiscount = dialog.findViewById(R.id.discount);
        //btnImage = dialog.findViewById(R.id.imageItemUpload);
        btnImage = dialog.findViewById(R.id.imageItemUpload1);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        Button btnSubmit = dialog.findViewById(R.id.btnSubmit);
        Button btnDelete = dialog.findViewById(R.id.btnDelete);


        if (item!=null){
            try {
                edItemName.setText(item.getItemName());
                edPrice.setText(""+item.getUnitPrice());
                edQty.setText(""+item.getQty());
                edCategory.setText(item.getItemCategoryName());
                categoryid = item.getItemCategoryId();
                categoryname = item.getItemCategoryName();
                edItemtax.setText(item.getTaxName());
                taxId = item.getTaxId();
                taxName = item.getTaxName();
                editTaxAmount =item.getTaxAmount();


                // Log.e("imagalca",item.getImageLocation());
                btnImage.setImageBitmap(BitmapFactory.decodeFile(item.getImageLocation()));
                path = item.getImageLocation();
            }catch (Exception e){
                Log.e("item",e.toString());
            }

        }

        edItemName.addTextChangedListener(new MyTextWatcher(edItemName));
        edPrice.addTextChangedListener(new MyTextWatcher(edPrice));
        edQty.addTextChangedListener(new MyTextWatcher(edQty));
        edCategory.addTextChangedListener(new MyTextWatcher(edCategory));
        dialog.getWindow().setGravity(Gravity.RIGHT);
        dialog.show();



        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RealmManager.createRestaurentDao().deleteItem(item);
                callItemMethod();
                Toast.makeText(activity, "item deleted.", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });



        btnImage.setBackgroundResource(R.drawable.white_click_shape_circle);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.hide();
            }
        });

        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isReadStoragePermissionGranted() && isWriteStoragePermissionGranted()){

                    showPictureDialog();
                    return;
                }else{
                    isReadStoragePermissionGranted();
                    isWriteStoragePermissionGranted();
                }


            }
        });


        edCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(activity);
                dialog.setContentView(R.layout.product_category_item_dialog);
                EditText edInputSearch = dialog.findViewById(R.id.inputSearch);
                ListView list_view_category = dialog.findViewById(R.id.list_view_category);
                dialog.show();


                final List<ItemCategory> itemCategoryList = RealmManager.createRestaurentDao().getRestaCategoryList();

                if (itemCategoryList!=null && itemCategoryList.size()>0){
                    Log.e("categorysize", String.valueOf(itemCategoryList.size()));
                    final CategoryAdapter adapter = new CategoryAdapter(itemCategoryList,activity);
                    list_view_category.setAdapter(adapter);
                    list_view_category.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            //Toast.makeText(Activity_Products.this, ""+itemCategoryList.get(position).getItemCategoryName(), Toast.LENGTH_SHORT).show();
                            edCategory.setText(itemCategoryList.get(position).getItemCategoryName());
                            categoryid =  itemCategoryList.get(position).getItemCategoryId();
                            categoryname = itemCategoryList.get(position).getItemCategoryName();
                            dialog.dismiss();
                        }
                    });
                    /**
                     * Enabling Search Filter
                     * */
                    edInputSearch.addTextChangedListener(new TextWatcher() {

                        @Override
                        public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                            // When user changed the Text
                            adapter.getFilter().filter(cs);
                        }

                        @Override
                        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                                      int arg3) {

                        }

                        @Override
                        public void afterTextChanged(Editable arg0) {
                        }
                    });

                }



            }
        });
        edItemtax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callTaxDialog();
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateItemName() && !validateItemQty() && !validateItemPrice() && !validateItemCategory()) {
                    return;
                }else{

                    String itemName = edItemName.getText().toString().trim();
                    String itemPrice = edPrice.getText().toString().trim();
                    String itemQty = edQty.getText().toString().trim();



                    callEditItemSubmit(itemName,itemPrice,itemQty ,item);
                    dialog.dismiss();
                }

            }
        });
    }

    private void callEditItemSubmit(String edItemName, String edPrice, String edQty, Realm_ItemList item) {
        try {
            if (taxAmount !=0.00){
                totalGstTaxAmt = (Double.valueOf(edQty)*Double.valueOf(edPrice)*taxAmount)/100;
            }else{
                totalGstTaxAmt = editTaxAmount;
            }
            RealmManager.createRestaurentDao().updateItemDetails(edItemName, Double.valueOf(edPrice), Double.valueOf(edQty),categoryid,categoryname,path,taxName,taxId,totalGstTaxAmt,hsnCode,item);
            callItemMethod();
            Toast.makeText(activity, "Item Updated Successfully.", Toast.LENGTH_SHORT).show();

        }catch (Exception e){
            Log.e("Item exception",e.toString());
        }

    }

    private void callItemDialog() {

        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.product_add_item_dialog);
        inputLayoutItemName = (TextInputLayout) dialog.findViewById(R.id.input_item_name);
        inputLayoutQty = (TextInputLayout) dialog.findViewById(R.id.input_qty);
        inputLayoutPrice = (TextInputLayout) dialog.findViewById(R.id.input_price);
        inputLayoutCategory = (TextInputLayout) dialog.findViewById(R.id.input_category);
        edItemName = dialog.findViewById(R.id.itemname);
        edPrice = dialog.findViewById(R.id.price);
        edQty = dialog.findViewById(R.id.qty);
        edItemtax = dialog.findViewById(R.id.itemtax);
        edCategory = dialog.findViewById(R.id.id_category);
        TextInputEditText edDiscount = dialog.findViewById(R.id.discount);
        btnImage = dialog.findViewById(R.id.imageItemUpload1);

        //btnImage = dialog.findViewById(R.id.imageItemUpload);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        Button btnSubmit = dialog.findViewById(R.id.btnSubmit);

        edQty.setText("1");
        //btnImage.setImageBitmap(BitmapFactory.decodeFile("/storage/emulated/0/demonuts/1540012108933.jpg"));
        edItemName.addTextChangedListener(new MyTextWatcher(edItemName));
        edPrice.addTextChangedListener(new MyTextWatcher(edPrice));
        edQty.addTextChangedListener(new MyTextWatcher(edQty));
        edCategory.addTextChangedListener(new MyTextWatcher(edCategory));
        dialog.getWindow().setGravity(Gravity.RIGHT);
        dialog.show();

        try {
            List<Realm_ItemCategoryList> categoryLists = RealmManager.createRestaurentDao().getRestaCategoryListFilter();

            if (categoryLists != null && categoryLists.size() > 0) {
                edCategory.setText(categoryLists.get(0).getItemCategoryName());
                categoryname = categoryLists.get(0).getItemCategoryName();
                categoryid = categoryLists.get(0).getItemCategoryId();
            }
        }catch (Exception e){

        }



        edItemtax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callTaxDialog();
            }
        });



        btnImage.setBackgroundResource(R.drawable.white_click_shape_circle);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.hide();
            }
        });

        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isReadStoragePermissionGranted() && isWriteStoragePermissionGranted()){

                    showPictureDialog();
                    return;
                }else{
                    isReadStoragePermissionGranted();
                    isWriteStoragePermissionGranted();
                }



            }
        });


        edCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(activity);
                dialog.setContentView(R.layout.product_category_item_dialog);
                EditText edInputSearch = dialog.findViewById(R.id.inputSearch);
                ListView list_view_category = dialog.findViewById(R.id.list_view_category);
                dialog.show();


                final List<ItemCategory> itemCategoryList = RealmManager.createRestaurentDao().getRestaCategoryList();

                if (itemCategoryList!=null && itemCategoryList.size()>0){
                    Log.e("categorysize", String.valueOf(itemCategoryList.size()));
                    final CategoryAdapter adapter = new CategoryAdapter(itemCategoryList,activity);
                    list_view_category.setAdapter(adapter);
                    list_view_category.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            //Toast.makeText(Activity_Products.this, ""+itemCategoryList.get(position).getItemCategoryName(), Toast.LENGTH_SHORT).show();
                            edCategory.setText(itemCategoryList.get(position).getItemCategoryName());
                            categoryid =  itemCategoryList.get(position).getItemCategoryId();
                            categoryname = itemCategoryList.get(position).getItemCategoryName();
                            dialog.dismiss();
                        }
                    });
                    /**
                     * Enabling Search Filter
                     * */
                    edInputSearch.addTextChangedListener(new TextWatcher() {

                        @Override
                        public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                            // When user changed the Text
                            adapter.getFilter().filter(cs);
                        }

                        @Override
                        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                                      int arg3) {

                        }

                        @Override
                        public void afterTextChanged(Editable arg0) {
                        }
                    });

                }



            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateItemName() && !validateItemQty() && !validateItemPrice() && !validateItemCategory()) {

                }else{
                    String itemName = edItemName.getText().toString().trim();
                    String itemPrice = edPrice.getText().toString().trim();
                    String itemQty = edQty.getText().toString().trim();
                    String itemCategory = edCategory.getText().toString().trim();
                    String itemTax = edItemtax.getText().toString().trim();
                    callItemSubmit(itemName,itemPrice,itemQty,path,itemTax,itemCategory);
                    dialog.dismiss();
                }

            }
        });

    }

    private void callTaxDialog() {
        final List<Realm_Tax> taxList = new ArrayList<>();
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.item_tax_dialog);
        ListView  listViewTax = dialog.findViewById(R.id.listview_tax);


        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        Button btnSubmit = dialog.findViewById(R.id.btnSubmit);
        dialog.show();

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        final List<Realm_Tax> itemTaxList = RealmManager.createRestaurentDao().getRestaItemTaxList();

        if (itemTaxList!=null && itemTaxList.size()>0){
            for (Realm_Tax realm :itemTaxList){
                Realm_Tax realmTax = new Realm_Tax();
                realmTax.setTaxId(realm.getTaxId());
                realmTax.setTaxName(realm.getTaxName());
                realmTax.setTaxAmount(realm.getTaxAmount());
                realmTax.setChecked(realm.isChecked());
                taxList.add(new Realm_Tax(realmTax.getTaxId(),realmTax.getTaxName(),realmTax.getTaxAmount(), realmTax.isChecked()));

            }
            final ItemTaxListAdapter   adapter = new ItemTaxListAdapter(taxList, activity);
            listViewTax.setAdapter(adapter);
            listViewTax.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Realm_Tax dataModel= taxList.get(position);
                    dataModel.checked = !dataModel.isChecked();
                    adapter.notifyDataSetChanged();
                    if (dataModel.checked){
                        edItemtax.setText(dataModel.getTaxName());
                        taxId = dataModel.getTaxId();
                        taxName = dataModel.getTaxName();
                        hsnCode = dataModel.getHsnCode();
                        taxAmount = dataModel.getTaxAmount();


                    }

                    dialog.dismiss();
                }
            });


        }


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(activity);
        pictureDialog.setTitle("Select Image");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), contentURI);
                     path = saveImage(bitmap);
                    Log.e("path",path);
                    Toast.makeText(activity, "Image Saved!", Toast.LENGTH_SHORT).show();
                    btnImage.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(activity, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            btnImage.setImageBitmap(thumbnail);
            saveImage(thumbnail);
            Toast.makeText(activity, "Image Saved!", Toast.LENGTH_SHORT).show();
        }
    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            file = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            file.createNewFile();
            FileOutputStream fo = new FileOutputStream(file);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(activity,
                    new String[]{file.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + file.getPath());

            return file.getPath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }



    private boolean validateItemCategory() {
        if (edCategory.getText().toString().trim().isEmpty()) {
            inputLayoutCategory.setError(getString(R.string.err_validate_name));
            requestFocus(edCategory);
            return false;
        } else {
            inputLayoutCategory.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateItemPrice() {
        if (edPrice.getText().toString().trim().isEmpty()) {
            inputLayoutPrice.setError(getString(R.string.err_validate_name));
            requestFocus(edPrice);
            return false;
        } else {
            inputLayoutPrice.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateItemQty() {
        if (edQty.getText().toString().trim().isEmpty()) {
            inputLayoutQty.setError(getString(R.string.err_validate_name));
            requestFocus(edQty);
            return false;
        } else {
            inputLayoutQty.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateItemName() {
        if (edItemName.getText().toString().trim().isEmpty()) {
            inputLayoutItemName.setError(getString(R.string.err_validate_name));
            requestFocus(edItemName);
            return false;
        } else {
            inputLayoutItemName.setErrorEnabled(false);
        }

        return true;
    }

    private void callItemSubmit(String edItemName, String edPrice, String edQty, String imagePath, String taxName, String edCategoryname) {
        //Toast.makeText(Activity_Products.this, "add", Toast.LENGTH_SHORT).show();
        try {

            if (taxAmount !=0.00){
                totalGstTaxAmt = (Double.valueOf(edQty)*Double.valueOf(edPrice)*taxAmount)/100;
            }else{
                totalGstTaxAmt = 0.00;
            }
            RealmManager.createRestaurentDao().saveRestuarentItemData(DataGenerator.generateRestaItemData( edItemName, Double.valueOf(edPrice), Double.valueOf(edQty),categoryid,edCategoryname,imagePath,taxName,taxId,totalGstTaxAmt,hsnCode));
            callItemMethod();
            Toast.makeText(activity, "Item Save Successfully.", Toast.LENGTH_SHORT).show();

        }catch (Exception e){
            Log.e("Item exception",e.toString());
        }

    }
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
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
                case R.id.itemname:
                    validateItemName();
                    break;
                case R.id.qty:
                    validateItemQty();
                    break;
                case R.id.price:
                    validateItemPrice();
                    break;
                case R.id.id_category:
                    validateItemCategory();
                    break;
            }
        }
    }

public  boolean isReadStoragePermissionGranted() {
    if (Build.VERSION.SDK_INT >= 23) {
        if (activity.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            Log.v(TAG,"Permission is granted1");
            return true;
        } else {

            Log.v(TAG,"Permission is revoked1");
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3);
            return false;
        }
    }
    else { //permission is automatically granted on sdk<23 upon installation
        Log.v(TAG,"Permission is granted1");
        return true;
    }
}

    public  boolean isWriteStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (activity.checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted2");
                return true;
            } else {

                Log.v(TAG,"Permission is revoked2");
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted2");
            return true;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 2:
                Log.d(TAG, "External storage2");
                if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    Log.v(TAG,"Permission: "+permissions[0]+ "was "+grantResults[0]);
                    //resume tasks needing this permission
                    showPictureDialog();
                }
                break;

            case 3:
                Log.d(TAG, "External storage1");
                if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    Log.v(TAG,"Permission: "+permissions[0]+ "was "+grantResults[0]);
                    //resume tasks needing this permission
                    showPictureDialog();
                }
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
