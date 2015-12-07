package com.example.sc.myapplication;

import android.app.Activity;
import android.content.ContentResolver;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.sc.adapter.AutoAdapter;
import com.example.sc.view.AutoGridView;

import java.util.ArrayList;


public class MainActivity extends Activity {
    private AutoGridView mGridView;

    private AutoAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        initData();
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
    }


}
