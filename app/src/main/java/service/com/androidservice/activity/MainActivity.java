package service.com.androidservice.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import service.com.androidservice.R;

/**
 * Created by wangchengcheng on 2017/2/6.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button mBtnLife;
    private Button mBtnLocal;
    private Button mBtnRemote;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnLife = (Button)findViewById(R.id.activity_main_life);
        mBtnLocal = (Button)findViewById(R.id.activity_main_local);
        mBtnRemote = (Button)findViewById(R.id.activity_main_remote);

        mBtnLife.setOnClickListener(this);
        mBtnLocal.setOnClickListener(this);
        mBtnRemote.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_main_life:
                startActivity(LifeCircleActivity.class);
                break;
            case R.id.activity_main_local:
                startActivity(LocalServiceActivity.class);
                break;
            case R.id.activity_main_remote:
                startActivity(RemoteServiceActivity.class);
                break;
            default:
                break;
        }
    }
    
    private void startActivity(Class clazz){
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }
}
