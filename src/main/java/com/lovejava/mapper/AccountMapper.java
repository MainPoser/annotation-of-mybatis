package com.lovejava.mapper;

import com.lovejava.domain.Account;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author:tianyao
 * @date:2019-04-06 20:18
 */
//开启二级缓存
@CacheNamespace(blocking = true)
public interface AccountMapper {
    /**
     * 根据用户id查询账号信息
     */
    @Select("select * from account where uid = #{id}")
    List<Account> findAccountByUid(Integer uid);
    /**
     * 根据AID查询到账号信息和用户信息
     */
    @Results({
            @Result(property = "aid",column = "aid"),
            @Result(property = "uid",column = "uid"),
            @Result(property = "money",column = "money"),
            @Result(property = "user",column = "uid",many =@Many(select = "com.lovejava.mapper.UserMapper.findUserById"))
    })
    @Select("select * from account where aid = #{aid}")
    Account findAccountByAid(Integer aid);
}
