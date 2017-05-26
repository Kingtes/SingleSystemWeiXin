package com.chujiu.manager.quartz.controller;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Kingt on 2017/5/25.
 */
public class WeiXinQuartzJob{

    @Autowired
    private RefreshAccessTokenTask refreshAccessTokenTask;

    public void work(){
        refreshAccessTokenTask.refreshToken();
    }
}
