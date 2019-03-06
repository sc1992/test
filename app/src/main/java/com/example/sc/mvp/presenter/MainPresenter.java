package com.example.sc.mvp.presenter;

import com.example.sc.mvp.model.user;
import com.example.sc.mvp.view.IBaseView;

/**
 * class description
 *
 * @author
 * @date
 */
public interface MainPresenter extends BasePresenter<IBaseView> {
    void login(user user);
}
