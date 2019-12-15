package cn.isuyu.customer.spring;

import cn.isuyu.customer.spring.factory.AnnotationConfigApplicationContext;
import cn.isuyu.customer.spring.factory.BeanFactory;
import cn.isuyu.customer.spring.service.UserService;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @GitHub https://github.com/niezhiliang
 * @Date 2019/12/14 3:39 下午
 */
public class Test {

    public static void main(String[] args) {
//        BeanFactory beanFactory = new BeanFactory("spring.xml");
//        UserService userService = (UserService) beanFactory.getBean("userService");
//        userService.test();
//
//        UserService userService2 = (UserService) beanFactory.getBean("userService2");
//        userService2.test();

        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.scm("cn.isuyu.customer.spring");
        UserService userService = (UserService) applicationContext.getBean("userServiceImpl3");
        userService.test();

    }

}
