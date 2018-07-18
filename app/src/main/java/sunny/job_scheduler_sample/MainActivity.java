package sunny.job_scheduler_sample;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import java.util.Random;

import sunny.job_scheduler_sample.services.MyJobServices;
import sunny.job_scheduler_sample.util.TinyDB;

public class MainActivity extends AppCompatActivity {

    EditText etToast;
    TinyDB tinyDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tinyDB = new TinyDB(getApplicationContext());
        etToast = findViewById(R.id.et_toast);

        scheduler();
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d("==", "onPause");
        String s = etToast.getText().toString();
        tinyDB.putString("toast",s);
    }


    public int randomInt() {
        Random r = new Random();
        return r.nextInt(100 - 1) + 1;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void scheduler() {
        Log.d("==","scheduler");

        int s = randomInt();

        ComponentName serviceComponent = new ComponentName(getApplicationContext(), MyJobServices.class);
        JobInfo.Builder builder = new JobInfo.Builder(s, serviceComponent);
        builder.setRequiresCharging(false);
        builder.setRequiresDeviceIdle(false);

        //interval

        if (Build.VERSION.SDK_INT >= 26) {
            builder.setPeriodic(15 * 60 * 1000);
        } else if (Build.VERSION.SDK_INT >= 24) {
            builder.setPeriodic(10 * 60 * 1000);
        } else {
            builder.setPeriodic(10 * 1000);
        }

        JobScheduler jobScheduler = (JobScheduler) getApplicationContext().getSystemService(Context.JOB_SCHEDULER_SERVICE);
        assert jobScheduler != null;
        jobScheduler.schedule(builder.build());
    }}