package com.hrb.demo.controller;

import com.hrb.demo.domain.User;
import com.hrb.demo.repository.UserRepostiory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** Alt + enter 进行导入 */
/** Alt + 7 打开 Structure*/
@RestController
public class UserController {
    //    采用构造性的注入方式：
//    好处：不能修改；可以提早初始化；
    /** 注的方式很简单：1 首先 final 字段 */
    private final UserRepostiory userRepostiory;

    /** 3、同事加上 autowired ，可加可不加，为了阅读方便我还是加上*/
    @Autowired
    /** 2、以传参的方式传过去 参数是谁来传递呢？是spring框架来传递 */
    public UserController(UserRepostiory userRepostiory){
        this.userRepostiory=userRepostiory;
    }

    @PostMapping("/person/save")
    public User save(@RequestParam String name){
        User user = new User();
        user.setUsername(name);
        if(userRepostiory.save(user)) {
            System.out.printf("用户对象： %s 保存成功！\n ",user);
        };
        return user;
        /** 测试使用：http://127.0.0.1:8071/apiyml/person/save?name=testhrb  (testhrb是你给定的name的值) */
    }
}
