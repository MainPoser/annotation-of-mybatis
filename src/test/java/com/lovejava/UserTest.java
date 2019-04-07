package com.lovejava;

import com.lovejava.domain.Account;
import com.lovejava.domain.User;
import com.lovejava.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author:tianyao
 * @date:2019-04-06 20:19
 */
public class UserTest {
    private InputStream is;
    private SqlSession sqlSession;
    private UserMapper mapper;

    @Before
    public void before() {
        try {
            //将主配值文件加载成流
            is = Resources.getResourceAsStream("SqlMapConfig.xml");
            //创建工厂
            SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
            SqlSessionFactory build = sqlSessionFactoryBuilder.build(is);
            //创建自动提交事务的sqlsession对象
            sqlSession = build.openSession(true);
            //创建dialing对象
            mapper = sqlSession.getMapper(UserMapper.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询所有用户的方法
     */
    @Test
    public void finUser(){
        List<User> users = mapper.findUser();
        System.out.println(users);
    }

    /**
     * 查询用户信息以及账号信息和延迟加载
     */
    @Test
    public void findUserById(){
        //正常查询
        /*User users = mapper.findUserById(4);
        System.out.println(users);*/

        User user = mapper.findUserById(4);
        List<Account> accounts = user.getAccounts();
        for (Account account : accounts) {
            System.out.println(account);
        }

    }
    @After
    public void after() {
        try {
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        sqlSession.close();
    }
}
