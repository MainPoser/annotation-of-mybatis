package com.lovejava;

import com.lovejava.domain.Account;
import com.lovejava.domain.User;
import com.lovejava.mapper.AccountMapper;
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
 * @date:2019-04-06 20:25
 */
public class AccountTest {

    private InputStream is;
    private SqlSession sqlSession;
    private AccountMapper mapper;

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
            mapper = sqlSession.getMapper(AccountMapper.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 根据uid查询用户的id信息
     */
    @Test
    public void findAccountByUid(){
        Integer uid = 4;
        List<Account> accounts = mapper.findAccountByUid(uid);
        System.out.println(accounts);

    }

    /**
     * 根据账号id查询账号信息和所属用户信息，并验证延迟加载
     */
    @Test
    public void findAccountByAid(){

        Integer aid = 2;
        //正常查询
       /*Account account = mapper.findAccountByAid(2);
        System.out.println(account);*/
        //延迟加载,只查询账户信息只执行一次数据库查询
        /*Account account = mapper.findAccountByAid(aid);
        Double money = account.getMoney();
        System.out.println(money);*/
        //查询用户信息相关，菜区数据库查询
        Account account = mapper.findAccountByAid(aid);
        User user = account.getUser();
        //查询到用户信息相关，执行两次sql进行查询
        System.out.println(user.getAddress());

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
