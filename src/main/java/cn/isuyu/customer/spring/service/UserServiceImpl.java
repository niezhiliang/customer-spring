package cn.isuyu.customer.spring.service;

import cn.isuyu.customer.spring.dao.UserDao;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @GitHub https://github.com/niezhiliang
 * @Date 2019/12/14 3:39 下午
 */
public class UserServiceImpl implements UserService {

    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }


    public void test() {
        System.out.print("userService-->");
        userDao.test();
    }
}
