package com.example.sc.myapplication;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.ContentResolver;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sc.R;
import com.example.sc.adapter.AutoAdapter;
import com.example.sc.view.AutoGridView;

import java.util.ArrayList;


public class MainActivity extends Activity {
    private AutoGridView mGridView;

    private AutoAdapter mAdapter;

    private TextView mTextView;

    private Button mButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        registerListener();
        initData();
    }

    private void registerListener() {
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mTextView.scrollTo(-300,0);
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                mTextView.scrollBy(10,20);
//                            }
//                        });
//                    }
//                },500);
                Scroller scroller = new Scroller(MainActivity.this);

                ObjectAnimator.ofFloat(mTextView,"translationX",0,300).setDuration(100).start();

            }
        });

        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"你好啊",Toast.LENGTH_LONG).show();
            }
        });


    }

    class myHandler extends  Handler{
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
        }
    }



    private void initData() {
        ArrayList<String> list=new ArrayList<>();
        for(int i=0;i<55;i++){
            list.add(i+"");
        }
        mAdapter=new AutoAdapter(this);
        mAdapter.setDatas(list);
        mGridView.setAdapter(mAdapter);


    }

    private void initUI() {

        mGridView=(AutoGridView)findViewById(R.id.autoGrid);

        mTextView = (TextView) findViewById(R.id.tv_test);
        mButton = (Button) findViewById(R.id.btn_test);
    }


}
