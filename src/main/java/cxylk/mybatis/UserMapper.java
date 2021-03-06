package cxylk.mybatis;

import cxylk.mybatis.bean.User;
import cxylk.mybatis.cache.DiskCache;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.cache.decorators.FifoCache;
import org.apache.ibatis.cache.decorators.LruCache;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;

/**
 * @Classname UserMapper
 * @Description TODO
 * @Author likui
 * @Date 2021/3/11 23:47
 **/
@CacheNamespace
public interface UserMapper {
    @Select({"select * from user where id=#{0}"})
    User selectById(Integer id);


    @Select({"select * from user where id=#{0}"})
    User selectById2(Integer id);

    @Select({"select * from user"})
    User selectById3();

    @Select({" select * from user where name='${name}'"})
    @Options(statementType = StatementType.PREPARED)
    List<User> selectByName(User user);

    @Select({"select * from user where name=#{name} or age=#{arg1}"})
    User selectByNameOrAge(@Param("name") String name, Integer age);

    List<User> selectByUser(User user);

    @Insert("INSERT INTO `user`( `name`, `age`, `sex`, `email`, `phone_number`) VALUES ( #{name}, #{age}, #{sex}, #{email}, #{phoneNumber})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int addUser(User user);

    int editUser(User user);

    @Update("update  user set name=#{arg1} where id=#{arg0}")
    int setName(Integer id, String name);

    @Delete("delete from user where id=#{id}")
    int deleteUser(Integer id);
}
