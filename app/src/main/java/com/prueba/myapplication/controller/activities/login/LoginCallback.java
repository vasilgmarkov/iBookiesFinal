package com.prueba.myapplication.controller.activities.login;

import com.prueba.myapplication.model.UserToken;

public interface LoginCallback {
    void onSuccess(UserToken userToken);
    void onFailure(Throwable t);
}
