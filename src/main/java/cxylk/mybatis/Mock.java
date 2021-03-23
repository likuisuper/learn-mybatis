package cxylk.mybatis;

import cxylk.mybatis.bean.User;

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
        user.setEmail("modk@coderead.cn");
        user.setPhoneNumber("888888");
        user.setSex("男");
        return user;
    }
}
