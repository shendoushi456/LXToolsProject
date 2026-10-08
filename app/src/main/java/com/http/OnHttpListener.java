package com.http;

public interface OnHttpListener {
    void onSuccess();
    void onFail(Exception e);
}
