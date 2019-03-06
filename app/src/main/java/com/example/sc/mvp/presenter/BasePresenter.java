package com.example.sc.mvp.presenter;

import com.example.sc.mvp.model.user;
import com.example.sc.mvp.view.IBaseView;

/**
 * class description
 *
 * @author
 * @date
 */
public interface BasePresenter<T> {

    void attachView(T view);
    void detachView();

}
