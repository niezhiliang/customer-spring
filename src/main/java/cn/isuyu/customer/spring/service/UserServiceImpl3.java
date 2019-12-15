package cn.isuyu.customer.spring.service;

import cn.isuyu.customer.spring.annos.Autowired;
import cn.isuyu.customer.spring.annos.Service;
import cn.isuyu.customer.spring.dao.UserDao;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @GitHub https://github.com/niezhiliang
 * @Date 2019/12/15 4:26 下午
 */
@Service("userServiceImpl3")
public class UserServiceImpl3 implements UserService {

    @Autowired
    private UserDao userDao;

    public void test() {
        userDao.test();
        System.out.println("annotation di");
    }


}
