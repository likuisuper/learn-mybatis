package cxylk.mybatis;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.junit.Test;

/**
 * @Classname MetaObjectDemo
 * @Description MetaObject类测试
 * @Author likui
 * @Date 2021/4/1 11:22
 **/
public class MetaObjectDemo {
    @Test
    public void test1(){
        Object blog=Mock.newBlog();
        Configuration configuration=new Configuration();
        MetaObject metaObject=configuration.newMetaObject(blog);
        System.out.println(metaObject.getValue("comments[0].user.name"));
    }
}
