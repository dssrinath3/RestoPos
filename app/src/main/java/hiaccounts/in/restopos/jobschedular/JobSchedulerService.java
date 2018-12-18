package hiaccounts.in.restopos.jobschedular;

import android.annotation.TargetApi;
import android.app.Service;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import hiaccounts.in.restopos.utility.Constant;
import hiaccounts.in.restopos.utility.UtilView;

import static hiaccounts.in.restopos.product_and_tax.activity.Activity_SyncData.MESSENGER_INTENT_KEY;
import static hiaccounts.in.restopos.product_and_tax.activity.Activity_SyncData.WORK_DURATION_KEY;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class JobSchedulerService extends JobService {
    private static final String TAG = JobSchedulerService.class.getSimpleName();

    private Messenger mActivityMessenger;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "Service created");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "Service destroyed");
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mActivityMessenger = intent.getParcelableExtra(MESSENGER_INTENT_KEY);
        return START_NOT_STICKY;
    }

    @Override
    public boolean onStartJob(final JobParameters params) {
        // The work that this service "does" is simply wait for a certain duration and finish
        // the job (on another thread).

        sendMessage(Constant.MSG_COLOR_START, params.getJobId());

        long duration = params.getExtras().getLong(WORK_DURATION_KEY);

        // Uses a handler to delay the execution of jobFinished().
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                sendMessage(Constant.MSG_COLOR_STOP, params.getJobId());
               // jobFinished(params, false);
                doWork(params);
            }
        }, duration);
        Log.e(TAG, "on start job: " + params.getJobId());

        // Return true as there's more work to be done with this job.
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        // Stop tracking these job parameters, as we've 'finished' executing.
        sendMessage(Constant.MSG_COLOR_STOP, params.getJobId());
        Log.e(TAG, "on stop job: " + params.getJobId());

        // Return false to drop the job.
        return false;
    }

    private void sendMessage(int messageID, @Nullable Object params) {
        // If this service is launched by the JobScheduler, there's no callback Messenger. It
        // only exists when the MainActivity calls startService() with the callback in the Intent.
        if (mActivityMessenger == null) {
            Log.e(TAG, "Service is bound, not started. There's no callback to send a message to.");
            return;
        }
        Message m = Message.obtain();
        m.what = messageID;
        m.obj = params;
        try {
            mActivityMessenger.send(m);
        } catch (RemoteException e) {
            Log.e(TAG, "Error passing service object back to activity.");
        }
    }

    private void doWork(final JobParameters jobParameters) {
        Log.e(TAG, "doWork  Call " + jobParameters.getJobId());
        JobSchedulerDao dao = new JobSchedulerDao(jobParameters, new JobScheduleStatusListener() {
            @Override
            public void onJobScheduleSuccess() {
                UtilView.showLogCat(TAG, "onJobScheduleSuccess");
                jobFinished(jobParameters, false);
            }

            @Override
            public void onJobScheduleFailure() {
                jobFinished(jobParameters, true);
                UtilView.showLogCat(TAG, "onJobScheduleFailure");
            }
        });
        dao.executeTask();


    }
}
