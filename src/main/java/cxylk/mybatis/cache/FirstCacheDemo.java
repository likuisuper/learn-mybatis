package cxylk.mybatis.cache;

import cxylk.mybatis.UserMapper;
import cxylk.mybatis.bean.User;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * @Classname FirstCacheDemo
 * @Description 一级缓存
 * @Author likui
 * @Date 2021/3/18 20:15
 **/
public class FirstCacheDemo {
    private SqlSessionFactory sessionFactory;
    private SqlSession sqlSession;

    @Before
    public void init(){
        SqlSessionFactoryBuilder builder=new SqlSessionFactoryBuilder();
        sessionFactory=builder.build(FirstCacheDemo.class.getResourceAsStream("/mybatis-config.xml"));
        sqlSession=sessionFactory.openSession();
    }

    /**
     * 一级缓存命中条件：
     * 1.sql和参数必须相同
     * 2.mapperStatement(ms) id必须相同
     * 3.必须是同一个会话，这是证明了一级缓存是sqlsession级别的
     */
    @Test
    public void test1(){
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        //ms:cxylk.mybatis.UserMapper.selectById
        //基于动态代理
        User user1 = mapper.selectById(1);
        //ms:cxylk.mybatis.UserMapper.selectById2
//        User user2 = mapper.selectById2(1);
        //重新构建一个会话
        User user2 = sessionFactory.openSession().getMapper(UserMapper.class).selectById(1);
        //虽然调用方式不一样，但底层都是一样的，满足上面三个条件，仍然能命中缓存
        //设置分页，此时返回结果不一样，不能命中。但是将rowBounds设置为默认分页，就可以命中，因为当不传第三个参数就是默认分页
//        RowBounds rowBounds=new RowBounds(1,2);
        RowBounds rowBounds=RowBounds.DEFAULT;
        List user3 = sqlSession.selectList("cxylk.mybatis.UserMapper.selectById", 1,rowBounds);
        System.out.println(user1==user3.get(0));//true
    }

    /**
     * 什么情况下不会清空缓存
     * 1.未手动清空，即没有调用clearCache
     * 2.没有调用flushCache=true的查询
     * 3.没有执行DML操作
     * 4.没有执行commit,rollback操作
     * 怎么关闭一级缓存？在configuration中有个localCacheScope，是个枚举类型，通过xml配置文件将它修改为STATEMENT,但这不意味一级缓存不存在了
     */
    @Test
    public void test2(){
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user1 = mapper.selectById2(1);
//        sqlSession.clearCache();//清空缓存
        //执行dml操作，将不能命中缓存，因为要保证数据的一致性，避免出现脏读
//        mapper.setName(1,"lkk");
        sqlSession.commit();//执行commit,rollback也会清除缓存
        //将以及缓存的作用域在配置文件中修改为STATEMENT，就不会命中一级缓存
        User user2 = mapper.selectById2(1);
        System.out.println(user1==user2);
    }

    @Test
    public void test3(){
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user1 = mapper.selectById2(1);
        User user2 = mapper.selectById2(1);
        System.out.println(user1==user2);
    }
}
