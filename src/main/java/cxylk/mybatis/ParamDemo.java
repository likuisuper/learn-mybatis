package cxylk.mybatis;

import org.apache.ibatis.session.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Classname ParamDemo
 * @Description 参数解析测试
 * @Author likui
 * @Date 2021/3/25 20:58
 **/
public class ParamDemo {
    private SqlSession sqlSession;
    private UserMapper mapper;

    @Before
    public void init() throws SQLException {
        SqlSessionFactoryBuilder factoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory factory = factoryBuilder.build(ParamDemo.class.getResourceAsStream("/mybatis-config.xml"));
        sqlSession = factory.openSession();
        mapper = sqlSession.getMapper(UserMapper.class);
    }

    @After
    public void over(){
        sqlSession.close();
    }

    @Test
    public void test1(){
        mapper.selectById(1);
    }

    @Test
    public void test2(){
        mapper.selectByNameOrAge("lk",12);
    }

    @Test
    public void testResultContext(){
        List<Object> list=new ArrayList<>();
        ResultHandler handler=new ResultHandler() {
            @Override
            public void handleResult(ResultContext resultContext) {
                //当记录=1的时候就stop，所以结果只有1条
                if(resultContext.getResultCount()==1){
                    resultContext.stop();
                }
                list.add(resultContext.getResultObject());
            }
        };
        sqlSession.select("cxylk.mybatis.UserMapper.selectById3",handler);
        System.out.println(list.size());
    }
}
