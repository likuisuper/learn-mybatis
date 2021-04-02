package cxylk.mybatis;

import cxylk.mybatis.bean.Blog;
import cxylk.mybatis.bean.Comment;
import cxylk.mybatis.bean.User;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Classname Mock
 * @Description TODO
 * @Author likui
 * @Date 2021/3/21 18:40
 **/
public class Mock {
    public static User newUser() {
        User user = new User();
        String s = System.currentTimeMillis() + "";
        s = s.substring(s.length() - 5, s.length());
        user.setName("mock_" + s);
        user.setAge(19);
        user.setEmail("modk@xcxxx.cn");
        user.setPhoneNumber("888888");
        user.setSex("男");
        return user;
    }

    public static Blog newBlog() {
        Blog blog=new Blog();
        blog.setId(88);
        blog.setAuthor(newUser());
        blog.setBody("test blog body");
        blog.setTitle("test blog title");
        blog.setComments(Stream.iterate(newComment() ,
                i->newComment()).limit(10).collect(Collectors.toList()));
        return blog;
    }

    public static Comment newComment(){
        Comment comment=new Comment();
        comment.setBlogId(88);
        comment.setUser(newUser());
        comment.setBody("test blog comment body");

        return comment;
    }
}
