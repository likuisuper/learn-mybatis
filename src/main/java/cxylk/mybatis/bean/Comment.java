package cxylk.mybatis.bean;

import java.io.Serializable;

/**
 * @Classname Comment
 * @Description TODO
 * @Author likui
 * @Date 2021/3/22 21:38
 **/
public class Comment implements Serializable {
    private String id;
    private Integer blogId;
    private String body;
    private User user;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getBlogId() {
        return blogId;
    }

    public void setBlogId(Integer blogId) {
        this.blogId = blogId;
    }
}
