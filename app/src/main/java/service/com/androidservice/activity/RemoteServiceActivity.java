package service.com.androidservice.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import service.com.androidservice.R;
import service.com.androidservice.service.CountService;
import service.com.androidservice.service.ICountCallback;
import service.com.androidservice.service.ICountService;

public class RemoteServiceActivity extends AppCompatActivity implements View.OnClickListener, ICountCallback {

    private TextView mCountLabel;
    private Button mStartBtn;
    private Button mStopBtn;
    private ICountService mCountService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_remote);

        mCountLabel = (TextView) findViewById(R.id.text_counter);

        mStartBtn = (Button) findViewById(R.id.btn_start);
        mStopBtn = (Button) findViewById(R.id.btn_stop);

        mStartBtn.setOnClickListener(this);
        mStopBtn.setOnClickListener(this);

        Intent intent = new Intent(this, CountService.class);
        bindService(intent, mServiceConn, Context.BIND_AUTO_CREATE);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                if (mCountService != null) {
                    mCountService.startCount(0, this);
                }

                break;
            case R.id.btn_stop:
                if (mCountService != null) {
                    mCountService.stopCount();
                }
                break;
            default:
                break;
        }
    }

    private final ServiceConnection mServiceConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            mCountService = ((CountService.CountBinder) service).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mCountService = null;
        }
    };

    @Override
    public void countCallback(int val) {
        mCountLabel.setText(String.valueOf(val));
    }
}
