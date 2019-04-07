package com.lovejava.mapper;

import com.lovejava.domain.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author:tianyao
 * @date:2019-04-06 20:18
 */
//开启二级缓存
@CacheNamespace(blocking = true)
public interface UserMapper {
    /**
     * 查询所有用户
     */
    @Select("select * from user")
    List<User> findUser();
    /**
     * 查询某个用户的信息和他的账户信息
     */
    @Results(id = "findUserById",value = {
            @Result(property = "id",column = "id"),
            @Result(property = "username",column = "username"),
            @Result(property = "sex",column = "sex"),
            @Result(property = "address",column = "address"),
            @Result(property = "accounts",column = "id",one=@One(select = "com.lovejava.mapper.AccountMapper.findAccountByUid"))
    })
    @Select("select * from user where id = #{id}")
    User findUserById(Integer id);
}
