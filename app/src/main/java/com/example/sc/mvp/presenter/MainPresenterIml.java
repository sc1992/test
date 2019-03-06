package com.example.sc.mvp.presenter;

import com.example.sc.mvp.model.user;
import com.example.sc.mvp.view.IBaseView;

/**
 * class description
 *
 * @author
 * @date
 */
public class MainPresenterIml implements MainPresenter{

    private IBaseView mIBaseView;


    @Override
    public void attachView(IBaseView view) {

    }

    @Override
    public void detachView() {
        mIBaseView = null;
    }

    @Override
    public void login(user user) {
        if(user.getUserName().equals("shichuan")&& user.getPassword().equals("123456")){
            mIBaseView.showSuccess();
        }else{
            mIBaseView.showFailure();
        }
    }
}
