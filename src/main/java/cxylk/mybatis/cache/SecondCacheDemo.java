package cxylk.mybatis.cache;

import cxylk.mybatis.BlogMapper;
import cxylk.mybatis.Mock;
import cxylk.mybatis.UserMapper;
import cxylk.mybatis.bean.User;
import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Classname SecondCacheDemo
 * @Description 二级缓存演示。
 * @Author likui
 * @Date 2021/3/21 20:41
 **/
public class SecondCacheDemo {
    private SqlSessionFactory sessionFactory;
    private Configuration configuration;

    @Before
    public void init() throws IOException {
        InputStream in = FirstCacheDemo.class.getResourceAsStream("/mybatis-config.xml");
        SqlSessionFactoryBuilder builder=new SqlSessionFactoryBuilder();
        sessionFactory=builder.build(in);
        configuration = sessionFactory.getConfiguration();
    }

    @Test
    public void cacheTest1(){
        //cacheId就是添加了@cacheNamespace注解的类
        Cache cache = configuration.getCache("cxylk.mybatis.UserMapper");
        User user = Mock.newUser();
        //设置缓存
        cache.putObject("lk",user);
        //从缓存中获取
        cache.getObject("lk");
    }

    /**
     * 缓存实现扩展
     *
     * @CacheNamespace(implementation = DiskCache.class,properties = {@Property(name = "cachePath", value ="D:\workspace\learn-mybatis\target\cache" )})
     */
    @Test
    public void cacheTest2() {
        Cache cache = configuration.getCache("cxylk.mybatis.UserMapper");
        User user = Mock.newUser();
        cache.putObject("lk", user);// 设置缓存
        cache.getObject("lk");
    }

    /**
     * 溢出淘汰 FIFO
     * @CacheNamespace(eviction = FifoCache.class, size = 10)
     */
    @Test
    public void cacheTest3() {
        Cache cache = configuration.getCache("cxylk.mybatis.UserMapper");
        User user = Mock.newUser();
        for (int i = 0; i < 12; i++) {
            cache.putObject("lk:" + i, user);// 设置缓存
        }
        System.out.println(cache);
    }

    /**
     * @CacheNamespace(readWrite =false) true 序列化 false 非序列化
     */
    @Test
    public void cacheTest4() {
        Cache cache = configuration.getCache("cxylk.mybatis.UserMapper");
        User user = Mock.newUser();
        cache.putObject("lk", user);// 设置缓存
        // 线程1
        Object lk = cache.getObject("lk");
        // 线程2
        Object lk1 = cache.getObject("lk");
        System.out.println(lk==lk1);
    }

    /**
     * 测试二级缓存。必须提交才会生效
     */
    @Test
    public void cacheTest5() {
        SqlSession sqlSession1 = sessionFactory.openSession(true);
        UserMapper mapper1 = sqlSession1.getMapper(UserMapper.class);
        User user=Mock.newUser();
        mapper1.selectById(1);
        mapper1.selectByUser(user);
//        mapper1.selectById(1);
        sqlSession1.commit();
        SqlSession sqlSession2 = sessionFactory.openSession();
        UserMapper mapper2 = sqlSession2.getMapper(UserMapper.class);
        mapper2.selectByUser(user);
    }

    /**
     * 缓存管理器、暂存区和缓存区关系，1:n:1
     */
    @Test
    public void cacheTest6(){
        SqlSession sqlSession=sessionFactory.openSession();
        sqlSession.getMapper(UserMapper.class).selectById(1);
        sqlSession.getMapper(BlogMapper.class).selectById(1);
        System.out.println(sqlSession);
    }

    /**
     * 暂存区存取流程
     */
    @Test
    public void cacheTest7() {
        SqlSession sqlSession1 = sessionFactory.openSession(true);
        UserMapper mapper1 = sqlSession1.getMapper(UserMapper.class);
        mapper1.selectById(1);
        sqlSession1.commit();
        SqlSession sqlSession2 = sessionFactory.openSession();
        UserMapper mapper2 = sqlSession2.getMapper(UserMapper.class);
        mapper2.selectById(1);
    }
}
