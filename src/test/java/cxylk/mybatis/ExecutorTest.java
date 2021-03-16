package cxylk.mybatis;

import org.apache.ibatis.executor.*;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.*;
import org.apache.ibatis.transaction.jdbc.JdbcTransaction;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Classname ExecutorTest
 * @Description TODO
 * @Author likui
 * @Date 2021/3/11 23:50
 **/
public class ExecutorTest {
    private Configuration configuration;
    private MappedStatement ms;
    private JdbcTransaction jdbcTransaction;
    private SqlSessionFactory sqlSessionFactory;

    @Before
    public void init(){
        //获取构建器
        SqlSessionFactoryBuilder builder=new SqlSessionFactoryBuilder();
        //解析xml并构建会话工厂
        sqlSessionFactory = builder.build(ExecutorTest.class.getResourceAsStream("/mybatis-config.xml"));
        configuration = sqlSessionFactory.getConfiguration();
        jdbcTransaction=new JdbcTransaction(sqlSessionFactory.openSession().getConnection());
        //获取sql映射
        ms=configuration.getMappedStatement("cxylk.mybatis.UserMapper.selectById");
    }

    //简单执行器测试。同样的sql语句会进行两次编译
    @Test
    public void simpleTest() throws SQLException {
        SimpleExecutor executor=new SimpleExecutor(configuration,jdbcTransaction);
        List<Object> list=executor.doQuery(ms,1, RowBounds.DEFAULT,SimpleExecutor.NO_RESULT_HANDLER,
                ms.getBoundSql(1));
        executor.doQuery(ms,1, RowBounds.DEFAULT,SimpleExecutor.NO_RESULT_HANDLER,
                ms.getBoundSql(1));
        System.out.println(list.get(0));
    }

    //可重用执行器。相同的sql，会缓存对应的PrepareStatement。缓存周期：会话。设置一次参数执行一次查询结果
    @Test
    public void reuseTest() throws SQLException {
        ReuseExecutor executor=new ReuseExecutor(configuration,jdbcTransaction);
        List<Object> list = executor.doQuery(ms, 1, RowBounds.DEFAULT, executor.NO_RESULT_HANDLER,
                ms.getBoundSql(1));
        executor.doQuery(ms, 1, RowBounds.DEFAULT, executor.NO_RESULT_HANDLER,
                ms.getBoundSql(1));
        System.out.println(list.get(0));
    }

    //批处理执行器。一次性执行所有sql语句
    @Test
    public void batchTest() throws SQLException {
        BatchExecutor executor=new BatchExecutor(configuration,jdbcTransaction);
        MappedStatement setName = configuration.getMappedStatement("cxylk.mybatis.UserMapper.setName");
        Map<String,Object> param=new HashMap<String, Object>();
        param.put("arg0",1);
        param.put("arg1","lk12");
        executor.doUpdate(setName,param);
        executor.doUpdate(setName,param);
        //必须执行doFlushStatements才会生效
        executor.flushStatements(false);
    }

    @Test
    public void cacheExecutorTest() throws SQLException {
        //BaseExecutor
        Executor executor=new BatchExecutor(configuration,jdbcTransaction);
        //装饰器模式
        Executor cacheExecutor=new CachingExecutor(executor);
        //mapper文件加上CacheNamespace注解
        cacheExecutor.query(ms,1,RowBounds.DEFAULT,Executor.NO_RESULT_HANDLER);
        cacheExecutor.commit(true);
        //二级缓存必须要提交才会生效
        cacheExecutor.query(ms,1,RowBounds.DEFAULT,Executor.NO_RESULT_HANDLER);
    }

    @Test
    public void sessionTest(){
        SqlSession sqlSession=sqlSessionFactory.openSession(ExecutorType.REUSE,true);
        //降低调用复杂性
        List<Object> list = sqlSession.selectList("cxylk.mybatis.UserMapper.selectById", 1);
        System.out.println(list.get(0));
    }
}
