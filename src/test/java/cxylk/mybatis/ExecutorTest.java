package cxylk.mybatis;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransaction;
import org.junit.Before;

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
        //获取构建起
        SqlSessionFactoryBuilder builder=new SqlSessionFactoryBuilder();
        //解析xml并构建会话工厂
        sqlSessionFactory = builder.build(ExecutorTest.class.getResourceAsStream("/mybatis-config.xml"));
        configuration = sqlSessionFactory.getConfiguration();
        jdbcTransaction=new JdbcTransaction(sqlSessionFactory.openSession().getConnection());
        //获取sql映射
        ms=configuration.getMappedStatement("cxylk.mybatis.UserMapper.selectById");

    }
}
