package cn.isuyu.customer.spring.service;

import cn.isuyu.customer.spring.dao.UserDao;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @GitHub https://github.com/niezhiliang
 * @Date 2019/12/14 4:35 下午
 */
public class UserServiceImpl2 implements UserService {

    private UserDao userDao;

    /** 如果是自动装配 需要注释该代码 因为默认使用无参实例化 **/
//    public UserServiceImpl2(UserDao userDao) {
//        this.userDao = userDao;
//    }


    public void test() {
        System.out.print("userService2-->");
        userDao.test();
    }
}
