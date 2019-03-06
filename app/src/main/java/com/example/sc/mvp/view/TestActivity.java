package com.example.sc.mvp.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sc.R;
import com.example.sc.mvp.model.user;
import com.example.sc.mvp.presenter.MainPresenterIml;

/**
 * class description
 *
 * @author
 * @date
 */
public class TestActivity extends Activity implements IBaseView{

    private EditText et_userName,et_password;
    private Button btn_login;
    private MainPresenterIml mPresenterIml;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        initUI();
        registerListener();
        initData();
    }

    private void initData() {
        mPresenterIml = new MainPresenterIml();
        mPresenterIml.attachView(this);
    }

    private void registerListener() {
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               user user = new user(et_userName.getText().toString(),et_password.getText().toString());
               mPresenterIml.login(user);
            }
        });
    }

    private void initUI() {
        et_userName = (EditText) findViewById(R.id.et_userName);
        et_password = (EditText) findViewById(R.id.et_password);
        btn_login = (Button) findViewById(R.id.btn_login);
    }

    @Override
    public void showFailure() {
        Toast.makeText(this,"登录失败",Toast.LENGTH_LONG).show();
    }

    @Override
    public void showSuccess() {
        Toast.makeText(this,"登录成功",Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenterIml.detachView();
    }
}
