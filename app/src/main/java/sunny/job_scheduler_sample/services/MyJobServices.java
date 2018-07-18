package sunny.job_scheduler_sample.services;

/**
 * Created by Wayan-MECS on 7/18/2018.
 */

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import java.math.BigDecimal;
import java.util.Random;

import sunny.job_scheduler_sample.util.TinyDB;

@RequiresApi(api = Build.VERSION_CODES.O)
public class MyJobServices extends JobService {
    private static final String TAG = MyJobServices.class.getSimpleName();
    public TinyDB tinyDB;

    String toastTxt;

    @Override
    public boolean onStartJob(JobParameters params) {

        tinyDB = new TinyDB(getApplicationContext());
        toastTxt = tinyDB.getString("toast");
        Log.w(TAG, "onStartJob JobId: " + params.getJobId());

        Toast.makeText(this,toastTxt,Toast.LENGTH_SHORT).show();

        jobFinished(params, false);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.w(TAG, "onStopJob");
        Toast.makeText(this, "onStopJob JobId:" + params.getJobId(), Toast.LENGTH_SHORT).show();
        return true;
    }


}
