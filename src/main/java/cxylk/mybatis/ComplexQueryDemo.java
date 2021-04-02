package cxylk.mybatis;

import cxylk.mybatis.bean.Blog;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @Classname ComplexQueryDemo
 * @Description 复杂查询，即嵌套子查询
 * @Author likui
 * @Date 2021/4/1 16:37
 **/
public class ComplexQueryDemo {
    private SqlSession sqlSession;

    @Before
    public void init(){
        SqlSessionFactoryBuilder factoryBuilder=new SqlSessionFactoryBuilder();
        SqlSessionFactory factory=factoryBuilder.build(ComplexQueryDemo.class.getResourceAsStream("/mybatis-config.xml"));
        sqlSession=factory.openSession();
    }

    @After
    public void over(){
        sqlSession.close();
    }

    @Test
    public void test(){
        Blog blog = sqlSession.selectOne("selectBlogById",1);
        System.out.println(blog);
    }
}
