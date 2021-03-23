package cxylk.mybatis.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @Classname Blog
 * @Description TODO
 * @Author likui
 * @Date 2021/3/22 21:37
 **/
public class Blog implements Serializable {
    private int id;
    private String title;
    private User author;
    private String body;
    private List<Comment> comments;

    public Blog() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getAuthor() {
        System.out.println("调用getAuthor");
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author=" + author +
                ", body='" + body + '\'' +
                ", comments=" + comments +
                '}';
    }
}
