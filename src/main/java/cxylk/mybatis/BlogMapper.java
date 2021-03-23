package cxylk.mybatis;

import cxylk.mybatis.bean.Blog;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

/**
 * @Classname BlogMapper
 * @Description TODO
 * @Author likui
 * @Date 2021/3/22 21:43
 **/
@CacheNamespace
public interface BlogMapper {
    @Select("select * from blog where id=#{id}")
    Blog selectById(Integer id);

    void selectUserById();
}
