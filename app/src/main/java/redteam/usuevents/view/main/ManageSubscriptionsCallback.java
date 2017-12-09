package redteam.usuevents.view.main;

/**
 * Created by carso on 11/22/2017.
 */

public interface ManageSubscriptionsCallback {
    void updateManageViews();
    void saveManageState();
    void updateUnsavedChangeState(boolean hasChanged);
    void updateNotificationPeriod(int newPeriodString, String newText);
}
