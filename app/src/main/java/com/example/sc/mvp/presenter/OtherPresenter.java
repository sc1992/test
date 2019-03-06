package com.example.sc.mvp.presenter;

import com.example.sc.mvp.view.OtherBaseView;

/**
 * class description
 *
 * @author
 * @date
 */
public interface OtherPresenter extends BasePresenter<OtherBaseView>{
    void loadImage(String path);
}
