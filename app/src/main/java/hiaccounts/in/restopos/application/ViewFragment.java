package hiaccounts.in.restopos.application;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import hiaccounts.in.restopos.R;
import hiaccounts.in.restopos.application.activity.Activity_Home;
import hiaccounts.in.restopos.application.adapter.CategoryAdapter;
import hiaccounts.in.restopos.application.fragment.ViewFragmentTables;
import hiaccounts.in.restopos.application.model.pageloaddata.ItemCategory;
import hiaccounts.in.restopos.application.realm_model.data.Realm_ItemList;
import hiaccounts.in.restopos.application.realm_model.utils.DataGenerator;
import hiaccounts.in.restopos.application.realmdbmanager.RealmManager;
import hiaccounts.in.restopos.utility.Constant;
import hiaccounts.in.restopos.utility.SharedPreference;

/**
 * srinath
 */
public class ViewFragment extends Fragment implements CategoryAdapter.ItemListener {


    @BindView(R.id.recyclerviewCategory)
    RecyclerView recyclerviewCategory;
    Unbinder unbinder;
    List<Realm_ItemList> categoryItemList;
    @BindView(R.id.tabTables)
    TabLayout tabTables;
    @BindView(R.id.viewpagerTable)
    ViewPager viewpagerTable;
    private Activity activity;
    private String categoryId;
    ItemCategory cateList;
    int tabPostion, tablePosition;
    List<String> itemTableList;
     private String tableName="";
    private SharedPreference sharedPreference;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (Activity) context;
    }


    public static Fragment newInstance(int position) {

        ViewFragment fragmentFirst = new ViewFragment();
        Log.e("dgf", String.valueOf(position));
        Bundle args = new Bundle();
        args.putInt("itempos", position);
        fragmentFirst.setArguments(args);

        return fragmentFirst;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();

        return view;
    }


    public void initView() {

        sharedPreference = SharedPreference.getInstance(activity);
        int status;
        try{
            int tabPostion = getArguments().getInt("itempos", 0);
            Log.e("pos", String.valueOf(tabPostion));


            categoryItemList = new ArrayList<>();



            if (tabPostion == 0) {
                categoryItemList.clear();
                Long id = 1L;
                ItemCategory itemCateList = RealmManager.createRestaurentDao().getRestaCategoryItemList(id);
                List<Realm_ItemList> itemLists = RealmManager.createRestaurentDao().getRestaItemList(itemCateList);
                for (Realm_ItemList realmItemList : itemLists) {

                    categoryItemList.add(realmItemList);
                    //categoryItemList.add(new CategoryItem(realmItemList.getItemName(), R.drawable.masal_vada, getResources().getColor(R.color.colorBrown),realmItemList.getUnitPrice()));
                }
                CategoryAdapter adapter = new CategoryAdapter(activity, categoryItemList, this);
                recyclerviewCategory.setAdapter(adapter);
                GridLayoutManager manager = new GridLayoutManager(activity, 3, GridLayoutManager.VERTICAL, false);
                recyclerviewCategory.setLayoutManager(manager);
                tabPostion = tabPostion + 1;
            } else if (tabPostion == 1) {
                categoryItemList.clear();
                Long id = Long.valueOf(tabPostion + 1L);
                ItemCategory itemCateList = RealmManager.createRestaurentDao().getRestaCategoryItemList(id);
                List<Realm_ItemList> itemLists = RealmManager.createRestaurentDao().getRestaItemList(itemCateList);
                for (Realm_ItemList realmItemList : itemLists) {
                    categoryItemList.add(realmItemList);
                }
                CategoryAdapter adapter = new CategoryAdapter(activity, categoryItemList, this);
                recyclerviewCategory.setAdapter(adapter);
                GridLayoutManager manager = new GridLayoutManager(activity, 3, GridLayoutManager.VERTICAL, false);
                recyclerviewCategory.setLayoutManager(manager);

            } else if (tabPostion >= 2) {
                categoryItemList.clear();
                Long id = Long.valueOf(tabPostion + 1L);
                ItemCategory itemCateList = RealmManager.createRestaurentDao().getRestaCategoryItemList(id);
                List<Realm_ItemList> itemLists = RealmManager.createRestaurentDao().getRestaItemList(itemCateList);
                for (Realm_ItemList realmItemList : itemLists) {
                    categoryItemList.add(realmItemList);
                }
                CategoryAdapter adapter = new CategoryAdapter(activity, categoryItemList, this);
                recyclerviewCategory.setAdapter(adapter);
                GridLayoutManager manager = new GridLayoutManager(activity, 3, GridLayoutManager.VERTICAL, false);
                recyclerviewCategory.setLayoutManager(manager);
            }

        } catch(Exception ex){
            status = -1; //Or some error status //
        }




    }
    @Override
    public void onItemClick(Realm_ItemList item, View view) {
        tableName = sharedPreference.getData(Constant.TABLENAMEDATA);
        Log.e("tableName1",tableName);
        RealmManager.createRestaurentDao().saveRestuarentTableOrders(DataGenerator.generateRestatableOrders(item,tableName), item,tableName);

        ((Activity_Home) activity).checkTableOrderList(tableName);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
