package cn.isuyu.customer.spring.dao;

import cn.isuyu.customer.spring.annos.Service;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @GitHub https://github.com/niezhiliang
 * @Date 2019/12/15 5:10 下午
 */
@Service("userDaoImpl2")
public class UserDaoImpl2 implements UserDao {
    public void test() {
        System.out.println("annot di hello");
    }
}
