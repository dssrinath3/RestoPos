package hiaccounts.in.restopos.jobschedular;

import android.app.job.JobParameters;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import hiaccounts.in.restopos.jobschedular.schedulertask.AsyncTaskCompleteListener;
import hiaccounts.in.restopos.jobschedular.schedulertask.SchedulerPostTask;
import hiaccounts.in.restopos.utility.Constant;

public class JobSchedulerDao {
    JobParameters jobParameters;
    JobScheduleStatusListener listener;
    String BASE_URL= "http://cloud.hiaccounts.com:8090/";

    public JobSchedulerDao(JobParameters jobParameters, JobScheduleStatusListener listener) {
        this.jobParameters = jobParameters;
        this.listener = listener;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void executeTask() {

        SchedulerPostTask task;
        int serviceType = jobParameters.getExtras().getInt("servicetype");

        switch (serviceType) {
            case Constant.JOB_RESTRABILLPAYMENT:
                String restraCheckoutData = jobParameters.getExtras().getString("gsonData");
                Log.e("JobSchedulerService", "gsonData " + jobParameters.getJobId() + "\n" + restraCheckoutData);
                task = new SchedulerPostTask(new AsyncTaskCompleteListener<String>() {
                    @Override
                    public void onTaskComplete(String result) {
                        if (result != null) {
                            listener.onJobScheduleSuccess();
                        } else {
                            listener.onJobScheduleFailure();
                        }


                    }
                });
                task.execute(restraCheckoutData, BASE_URL+"restaurant/save?tableNo=11&tableName=WalkIn&waiterName=avinab&printVal=savePrint");
                break;


        }

    }
}
