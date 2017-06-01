package com.chujiu.manager.user.service;

import com.chujiu.manager.user.model.WGroup;

import java.util.List;

/**
 * Created by tianci on 2017/6/1.
 */
public interface IGroupService {
    void add(WGroup group);

    List<WGroup> queryAll();

    WGroup queryUserGroup(String openId);

    void updateGroupName(int gid, String name);

    void moveUserToGroup(String openId, int gid);

    void moveUsersToGroup(List<String> openIds, int gid);

    void deleteGroup(int gid);

}
