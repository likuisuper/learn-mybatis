package cxylk.mybatis;

import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 * @Classname ConfigurationFactory
 * @Description TODO
 * @Author likui
 * @Date 2021/4/4 12:06
 **/
public class ConfigurationFactory {
    public static Configuration getConfiguration() {
        SqlSessionFactoryBuilder factoryBuilder = new SqlSessionFactoryBuilder();

        SqlSessionFactory factory = factoryBuilder.build(ConfigurationFactory.class.getResourceAsStream("/mybatis-config.xml"));

        return factory.getConfiguration();
    }
}
