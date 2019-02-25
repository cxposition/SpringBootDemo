package com.example.service;

import com.example.dao.UserDao;
import com.example.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by SunYi on 2016/2/1/0001.
 */
//service层用来写逻辑。
@Service
public class UserService {
    //自动注入一个userDao
    @Autowired
    private UserDao userDao;

    //用户注册逻辑
    public String register(User user) {
        //判断用户是否存在
        if (userDao.getOneUser(user.getUsername()) == null && user.getPassword().equals(user.getPassword2())) {
            userDao.setOneUser(user);
            return "注册成功";
        } else if(userDao.getOneUser(user.getUsername()) != null){
            return "用户已存在请更换用户名";
        }else{
            return "密码输入不一致";
        }
    }

    //用户删除逻辑
    public String delete(User user){
        //判断用户是否存在
        if(userDao.getOneUser(user.getUsername()) !=null) {
            userDao.deleteOneUser(user.getUsername());
            return "删除成功";
        }else return "用户不存在";
    }

    //用户登陆逻辑
    public String login(User user) {
        //通过用户名获取用户
        User dbUser = userDao.getOneUser(user.getUsername());
        //若获取失败
        if (dbUser == null) {
            return "该用户不存在";
        }
        //获取成功后，将获取用户的密码和传入密码对比
        else if (!dbUser.getPassword().equals(user.getPassword())) {
            return "密码错误";
        } else {
            //若密码也相同则登陆成功
            //让传入用户的属性和数据库保持一致
            user.setId(dbUser.getId());
            user.setCreateTime(dbUser.getCreateTime());
            return "登陆成功";
        }
    }
}
