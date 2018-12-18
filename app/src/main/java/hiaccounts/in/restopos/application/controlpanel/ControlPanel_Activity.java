package hiaccounts.in.restopos.application.controlpanel;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hiaccounts.in.restopos.R;
import hiaccounts.in.restopos.customer.activity.Activity_Customer;
import hiaccounts.in.restopos.product_and_tax.activity.Activity_Products;
import hiaccounts.in.restopos.product_and_tax.activity.Activity_Reports;
import hiaccounts.in.restopos.product_and_tax.activity.Activity_SyncData;

public class ControlPanel_Activity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.card_view1)
    CardView cardView1;
    @BindView(R.id.imageCustomer)
    ImageView imageCustomer;
    @BindView(R.id.tvCustomer)
    TextView tvCustomer;
    @BindView(R.id.card_view2)
    CardView cardView2;
    @BindView(R.id.imageReports)
    ImageView imageReports;
    @BindView(R.id.tvReports)
    TextView tvReports;
    @BindView(R.id.card_view3)
    CardView cardView3;
    @BindView(R.id.imageIngredienats)
    ImageView imageIngredienats;
    @BindView(R.id.tvIngredienats)
    TextView tvIngredienats;
    @BindView(R.id.card_view4)
    CardView cardView4;
    @BindView(R.id.imageSettings)
    ImageView imageSettings;
    @BindView(R.id.textViewSettings)
    TextView textViewSettings;
    @BindView(R.id.card_view5)
    CardView cardView5;
    @BindView(R.id.imageSync)
    ImageView imageSync;
    @BindView(R.id.tvSyncData)
    TextView tvSyncData;
    @BindView(R.id.card_view6)
    CardView cardView6;
    @BindView(R.id.card_view7)
    CardView cardView7;
    @BindView(R.id.card_view8)
    CardView cardView8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_panel);
        ButterKnife.bind(this);
        initComponent();
    }

    private void initComponent() {
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setTitle(getResources().getString(R.string.app_name));
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

    }

    @OnClick({R.id.card_view1, R.id.card_view2, R.id.card_view3, R.id.card_view4,R.id.card_view5,R.id.card_view6})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.card_view1:
                Intent in = new Intent(ControlPanel_Activity.this, Activity_Products.class);
                in.putExtra("callingFor", "Product");
                startActivity(in);
                break;
            case R.id.card_view2:
                Intent in1 = new Intent(ControlPanel_Activity.this, Activity_Customer.class);
                in1.putExtra("callingFor", "Customer");
                startActivity(in1);
                break;
            case R.id.card_view3:
                Intent in3 = new Intent(ControlPanel_Activity.this, Activity_Reports.class);
                in3.putExtra("callingFor", "Reports");
                startActivity(in3);
                break;
            case R.id.card_view4:
                break;
            case R.id.card_view5:

                break;
            case R.id.card_view6:
                Intent in5 = new Intent(ControlPanel_Activity.this, Activity_SyncData.class);
                in5.putExtra("callingFor", "Syncdata");
                startActivity(in5);
                break;

        }
    }
}
