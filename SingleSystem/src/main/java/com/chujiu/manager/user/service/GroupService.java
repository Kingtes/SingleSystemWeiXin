package com.chujiu.manager.user.service;

import com.chujiu.manager.user.model.WGroup;
import com.chujiu.model.WeiXinFinalValue;

import java.util.List;

/**
 * Created by tianci on 2017/6/1.
 */
public class GroupService implements IGroupService {
    @Override
    public void add(WGroup group) {
//        String url = WeiXinFinalValue.
    }

    @Override
    public List<WGroup> queryAll() {
        return null;
    }

    @Override
    public WGroup queryUserGroup(String openId) {
        return null;
    }

    @Override
    public void updateGroupName(int gid, String name) {

    }

    @Override
    public void moveUserToGroup(String openId, int gid) {

    }

    @Override
    public void moveUsersToGroup(List<String> openIds, int gid) {

    }

    @Override
    public void deleteGroup(int gid) {

    }
}
