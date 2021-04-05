package cxylk.mybatis;

import cxylk.mybatis.bean.Blog;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * @Classname LazyLoadDemo
 * @Description 懒加载测试
 * @Author likui
 * @Date 2021/4/4 11:24
 **/
public class LazyLoadDemo {
    private static Configuration configuration;
    private static final SqlSessionFactory factory;

    static {
        SqlSessionFactoryBuilder factoryBuilder = new SqlSessionFactoryBuilder();
        factory = factoryBuilder.build(LazyLoadDemo.class.getResourceAsStream("/mybatis-config.xml"));
        configuration = factory.getConfiguration();
        configuration.setLazyLoadTriggerMethods(new HashSet<>());
    }

    @Test
    public void lazyTest(){
        SqlSession sqlSession=factory.openSession();
        Blog blog = sqlSession.selectOne("selectBlogById", 1);
        blog.getComments();
    }

    @Test
    public void lazySetTest(){
        SqlSession sqlSession=factory.openSession();
        Blog blog = sqlSession.selectOne("selectBlogById", 1);
        blog.setComments(new ArrayList<>());
        System.out.println(blog.getComments().size());
    }

    private static byte[] writeObject(Object obj) throws IOException, IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream outputStream = new ObjectOutputStream(out);
        outputStream.writeObject(obj);
        return out.toByteArray();
    }

    private static Object readObject(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        ObjectInputStream inputStream = new ObjectInputStream(in);
        return inputStream.readObject();
    }

    // 序列化--》字节码   -》反序列化成对象


    /***
     *
     * 序列化之后依然可以进行
     * 注：需要设置configurationFactory 指定configuration构建器
     */
    @Test
    public void lazySerializableTest() throws IOException, ClassNotFoundException {
        SqlSession sqlSession = factory.openSession();
        Blog blog = sqlSession.selectOne("selectBlogById", 1);
        byte[] bytes = writeObject(blog);
        Blog blog2 = (Blog) readObject(bytes);
        System.out.println("--------反列化完成-------");
        blog2.getComments();
    }
}
