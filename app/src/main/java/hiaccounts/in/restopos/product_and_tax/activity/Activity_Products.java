package hiaccounts.in.restopos.product_and_tax.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hiaccounts.in.restopos.R;
import hiaccounts.in.restopos.application.model.pageloaddata.ItemCategory;
import hiaccounts.in.restopos.application.realm_model.utils.DataGenerator;
import hiaccounts.in.restopos.application.realmdbmanager.RealmManager;
import hiaccounts.in.restopos.product_and_tax.adapter.CategoryAdapter;
import hiaccounts.in.restopos.product_and_tax.fragment.FragmentCategory;
import hiaccounts.in.restopos.product_and_tax.fragment.FragmentDiscount;
import hiaccounts.in.restopos.product_and_tax.fragment.FragmentItem;
import hiaccounts.in.restopos.product_and_tax.fragment.FragmentTable;
import hiaccounts.in.restopos.product_and_tax.fragment.ProductAndTaxFragment;

public class Activity_Products extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    private Activity activity;
    String titleName="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        ButterKnife.bind(this);
        initComponent();
    }

    private void initComponent() {
        setSupportActionBar(toolbar);
        activity =this;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ActionBar actionBar = getSupportActionBar();

        Intent in = getIntent();
        titleName =in.getStringExtra("callingFor");

        if (titleName!=null)
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

        callViewItems();


    }

    private void callViewItems() {
        tabs.setupWithViewPager(viewpager);
        createViewPager(viewpager);
        createTabIcons();
    }

    private void createViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new FragmentItem(), "Item");
        adapter.addFrag(new FragmentCategory(), "Category");
        adapter.addFrag(new ProductAndTaxFragment(), "Tax");
        adapter.addFrag(new FragmentDiscount(), "Discount");
        adapter.addFrag(new FragmentTable(), "Table");
        viewPager.setAdapter(adapter);
    }

    private void createTabIcons() {

        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_product_tab, null);
        tabOne.setText("Item");
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_item_black_24dp, 0, 0);
        tabs.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_product_tab, null);
        tabTwo.setText("Category");
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_category_black_24dp, 0, 0);
        tabs.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_product_tab, null);
        tabThree.setText("Tax");
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_tax_black_24dp, 0, 0);
        tabs.getTabAt(2).setCustomView(tabThree);

        TextView tabFour = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_product_tab, null);
        tabFour.setText("Discount");
        tabFour.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_local_offer_black_24dp, 0, 0);
        tabs.getTabAt(3).setCustomView(tabFour);

        TextView tabFive = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_product_tab, null);
        tabFive.setText("Table");
        tabFive.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_modifier_black_24dp, 0, 0);
        tabs.getTabAt(4).setCustomView(tabFive);
    }



    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


}
