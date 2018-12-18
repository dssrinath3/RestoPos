package hiaccounts.in.restopos.jobschedular;

public interface JobScheduleStatusListener {
    void onJobScheduleSuccess();

    void onJobScheduleFailure();
}
