package com.wangyuming.mysql.dao;

import com.wangyuming.mysql.entity.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserDao{
    @Select("select * from user")
    public List<User> findAll();
}
