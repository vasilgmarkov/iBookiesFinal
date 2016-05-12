package com.prueba.myapplication.controller.activities.master_detail;

import com.prueba.myapplication.model.User;

/**
 * Created by usu27 on 11/4/16.
 */
public interface UserCallBack {


    void onSuccess(User userInfo);

    void onFailure(Throwable t);
}
