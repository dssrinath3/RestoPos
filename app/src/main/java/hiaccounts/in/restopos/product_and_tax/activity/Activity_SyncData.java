package hiaccounts.in.restopos.product_and_tax.activity;

import android.app.Activity;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.os.PersistableBundle;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hiaccounts.in.restopos.BuildConfig;
import hiaccounts.in.restopos.R;
import hiaccounts.in.restopos.application.realmdbmanager.RealmManager;
import hiaccounts.in.restopos.jobschedular.JobSchedulerService;
import hiaccounts.in.restopos.utility.Constant;
import hiaccounts.in.restopos.utility.UtilView;

import static hiaccounts.in.restopos.utility.Constant.MSG_COLOR_START;
import static hiaccounts.in.restopos.utility.Constant.MSG_COLOR_STOP;

public class Activity_SyncData extends AppCompatActivity {
    private static final String TAG = Activity_SyncData.class.getSimpleName();
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @BindView(R.id.circle)
    ImageView circle;
    @BindView(R.id.tick)
    ImageView tick;
    @BindView(R.id.error)
    ImageView error;
    @BindView(R.id.greenglow)
    RelativeLayout greenglow;
    @BindView(R.id.app_settingsdata)
    CheckBox appSettingsdata;
    @BindView(R.id.salesdata)
    CheckBox salesdata;
    @BindView(R.id.btnSyncData)
    Button btnSyncData;
    private Activity activity;
    String titleName = "";
    public static final String MESSENGER_INTENT_KEY
            = BuildConfig.APPLICATION_ID + ".MESSENGER_INTENT_KEY";
    public static final String WORK_DURATION_KEY =
            BuildConfig.APPLICATION_ID + ".WORK_DURATION_KEY";
    private ComponentName mServiceComponent;

    private int mJobId = 0;
    private String gsonDataa="{\"selectedItemsList\":[{\"id\":0,\"itemCode\":\"001\",\"itemId\":1,\"itemName\":\"SWEET LASSI\",\"itemCategoryId\":2,\"itemCategoryName\":\"Lassi\",\"inputTaxId\":16,\"outputTaxId\":6,\"itemTypeId\":1,\"itemTypeName\":\"Service\",\"unitPrice\":\"30.00\",\"unitPriceIn\":\"30.00\",\"gstItemTax\":\"3.00\",\"taxamt\":\"3.00\",\"amtinclusivetax\":\"63.00\",\"qty\":2,\"discountAmt\":0,\"discPercent\":0,\"discountConfigAmt\":0,\"type\":null,\"taxid\":6,\"amtexclusivetax\":\"60.00\",\"inclusiveJSON\":\"{\\\"purchases\\\":false,\\\"sales\\\":false}\",\"uom\":7,\"uomConvertorDTOList\":[],\"convertedQuantity\":0,\"uomValue\":0,\"savedOrder\":true},{\"id\":1,\"itemCode\":\"002\",\"itemId\":2,\"itemName\":\"BANANA LASSI\",\"itemCategoryId\":2,\"itemCategoryName\":\"Lassi\",\"inputTaxId\":16,\"outputTaxId\":6,\"itemTypeId\":1,\"itemTypeName\":\"Service\",\"unitPrice\":\"30.00\",\"unitPriceIn\":\"30.00\",\"gstItemTax\":\"3.00\",\"taxamt\":\"3.00\",\"amtinclusivetax\":\"63.00\",\"qty\":2,\"discountAmt\":0,\"discPercent\":0,\"discountConfigAmt\":0,\"type\":null,\"taxid\":6,\"amtexclusivetax\":\"60.00\",\"inclusiveJSON\":\"{\\\"purchases\\\":false,\\\"sales\\\":false}\",\"uom\":7,\"uomConvertorDTOList\":[],\"convertedQuantity\":0,\"uomValue\":0,\"savedOrder\":true}],\"cashPayment\":{\"multiCashPaymentList\":[{\"paymentType\":1,\"cashAmt\":126}]},\"creditPayment\":{\"cardPaymentList\":[]},\"bankPayment\":{\"multiBankPaymentList\":[]},\"voucherPayment\":{\"multiVoucherPayments\":[]},\"cmpyLocation\":1,\"totalRemaininBalance\":0,\"cutomerName\":\"Cash Customer\",\"hiPosServiceCharge\":0,\"hiposServiceChargeAmt\":0,\"totalCheckOutamt\":126,\"paymentType\":\"multiPayment\",\"totalTaxAmt\":\"6.00\",\"totalTenderedAmount\":\"126.00\",\"taxType\":\"CGST:SGST/UGST\",\"customerId\":2,\"roundingOffValue\":\"0.00\",\"agentIdOfInvoice\":\"\",\"discountType\":\"itemWise\",\"discountAmount\":\"0.00\",\"discountAmtInPercentage\":0,\"returnReason\":\"[]\"}";

    // Handler for incoming messages from the service.
    private IncomingMessageHandler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync_data);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        activity = this;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        RealmManager.open();


        UtilView.showLogCat("@Flow", "relam " + RealmManager.open().getPath());

        ActionBar actionBar = getSupportActionBar();

        Intent in = getIntent();
        titleName = in.getStringExtra("callingFor");
        if (titleName != null)
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

        mServiceComponent = new ComponentName(this, JobSchedulerService.class);
        mHandler = new IncomingMessageHandler(this);

    }


    @OnClick({R.id.app_settingsdata, R.id.salesdata, R.id.btnSyncData})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.app_settingsdata:
                break;
            case R.id.salesdata:
                break;
            case R.id.btnSyncData:
                scheduleJob();
                break;
        }
    }

    /**
     * Executed when user clicks on SCHEDULE JOB.
     */
    public void scheduleJob() {
        JobInfo.Builder builder = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            builder = new JobInfo.Builder(mJobId++, mServiceComponent);
            builder.setMinimumLatency(1000);
            builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
            // Extras, work duration.
            PersistableBundle extras = new PersistableBundle();
            extras.putLong(WORK_DURATION_KEY, Long.valueOf(1) * 1000);
            extras.putString("gsonData", gsonDataa);
            extras.putInt("servicetype", 1);
            builder.setExtras(extras);
            // Schedule job
            Log.d(TAG, "Scheduling job");
            JobScheduler tm = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
            tm.schedule(builder.build());

        }



    }

    @Override
    protected void onStop() {
        // A service can be "started" and/or "bound". In this case, it's "started" by this Activity
        // and "bound" to the JobScheduler (also called "Scheduled" by the JobScheduler). This call
        // to stopService() won't prevent scheduled jobs to be processed. However, failing
        // to call stopService() would keep it alive indefinitely.
        stopService(new Intent(this, JobSchedulerService.class));
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Start service and provide it a way to communicate with this class.
        Intent startServiceIntent = new Intent(this, JobSchedulerService.class);
        Messenger messengerIncoming = new Messenger(mHandler);
        startServiceIntent.putExtra(MESSENGER_INTENT_KEY, messengerIncoming);
        startService(startServiceIntent);
    }

    /**
     * A {@link Handler} allows you to send messages associated with a thread. A {@link Messenger}
     * uses this handler to communicate from {@link JobSchedulerService}. It's also used to make
     * the start and stop views blink for a short period of time.
     */
    private static class IncomingMessageHandler extends Handler {

        // Prevent possible leaks with a weak reference.
        private WeakReference<Activity_SyncData> mActivity;

        IncomingMessageHandler(Activity_SyncData activity) {
            super(/* default looper */);
            this.mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            Activity_SyncData syncData = mActivity.get();
            if (syncData == null) {
                // Activity is no longer available, exit.
                return;
            }

            Message m;
            switch (msg.what) {
                /*
                 * Receives callback from the service when a job has landed
                 * on the app. Turns on indicator and sends a message to turn it off after
                 * a second.
                 */
                case Constant.MSG_COLOR_START:
                    // Start received.
                    updateParamsTextView(msg.obj, "started");

                    // Send message to turn it off after a second.
                    m = Message.obtain(this, Constant.MSG_UNCOLOR_START);
                    sendMessageDelayed(m, 1000L);
                    break;
                /*
                 * Receives callback from the service when a job that previously landed on the
                 * app must stop executing. Turns on indicator and sends a message to turn it
                 * off after two seconds.
                 */
                case Constant.MSG_COLOR_STOP:
                    // Stop received, turn on the indicator and show text.
                    updateParamsTextView(msg.obj, "stopped");

                    // Send message to turn it off after a second.
                    m = obtainMessage(Constant.MSG_UNCOLOR_STOP);
                    sendMessageDelayed(m, 2000L);
                    break;
                case Constant.MSG_UNCOLOR_START:
                    updateParamsTextView(null, "");
                    break;
                case Constant.MSG_UNCOLOR_STOP:
                    updateParamsTextView(null, "");
                    break;
            }
        }

        private void updateParamsTextView(@Nullable Object jobId, String action) {
            if (jobId == null) {
                return;
            }
            String jobIdText = String.valueOf(jobId);
            Log.e("Job",String.format("Job ID %s %s", jobIdText, action));

        }

        private int getColor(@ColorRes int color) {
            return mActivity.get().getResources().getColor(color);
        }
    }
}
