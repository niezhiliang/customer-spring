package cn.isuyu.customer.spring.factory;

import cn.isuyu.customer.spring.exception.BeanFactoryException;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @GitHub https://github.com/niezhiliang
 * @Date 2019/12/14 3:40 下午
 */
public class BeanFactory {

    private static Map<String,Object> map = new HashMap<String, Object>(10);

    public BeanFactory(String xmlPath) {
        parseXml(xmlPath);
    }


    public static void parseXml(String xmlPath) {
        xmlPath = BeanFactory.class.getResource("/").getPath().concat(xmlPath);
        SAXReader reader = new SAXReader();
        try {
            Document document = reader.read(xmlPath);
            //获取到beans标签
            Element root = document.getRootElement();
            //获取自动装配的类型
            String autowizeType = root.attribute("default-autowire").getStringValue();
            boolean flag = false;
            //判断当前配置是否为自动装配
            if (null != autowizeType) {
                flag = true;
            }
            //遍历bean标签
            for (Iterator<Element> first = root.elementIterator(); first.hasNext();) {
                Element element = first.next();
                String name =  element.attribute("id").getValue();
                String classPath = element.attribute("class").getValue();
                //拿到当前bean的类型
                Class clazz = Class.forName(classPath);
                //反射得到实例化的对象
                Object object = null;
                //遍历properties标签
                for (Iterator<Element> second = element.elementIterator(); second.hasNext();) {
                    Element secondElement = second.next();
                    //获取当前属性指向的bean对象的名称
                    String refName = secondElement.attribute("ref").getValue();
                    //set方法注入
                     if (secondElement.getName().equals("properties")) {
                         //实例化当前bean
                         object = clazz.newInstance();
                         //获取对象的属性名
                         String fileName =  secondElement.attribute("name").getValue();
                         //通过属性名获取当前属性对象
                         Field field = clazz.getDeclaredField(fileName);
                         field.setAccessible(true);
                         //直接通过ref指向的对象的名称，直接去map中获取
                         field.set(object,map.get(refName));
                     } else if (secondElement.getName().equals("constructor-arg")) {
                         //获取ref指向的类型
                         Class fileClazz = map.get(refName).getClass();
                         //通过类型获取构造函数
                         Constructor constructor = clazz.getConstructor(fileClazz.getInterfaces()[0]);
                         //通过构造函数实例化对象
                         object = constructor.newInstance(map.get(refName));
                     }

                }
                //如果该bean没有子标签，则直接实例化对象
                if (object == null) {
                    object = clazz.newInstance();
                }
                //表示设置了自动装配
                if (flag) {
                    //获取当前对象下所有的属性
                    Field [] fields = object.getClass().getDeclaredFields();

                    boolean isByType = false;
                    if (autowizeType.equals("byType")) {
                        isByType = true;
                    }

                    for (Field field : fields) {
                        field.setAccessible(true);
                        Object injectObject = null;
                        //通过byType的自动装配方式
                        if (isByType) {
                            Class autoClazz = field.getType();
                            int count = 0;
                            for (String key : map.keySet()) {
                                Class  temp = map.get(key).getClass().getInterfaces()[0];
                                //如果类型一样，则表示需要装配的就是该对象
                                if (temp.getName().equals(autoClazz.getName())) {
                                    injectObject = map.get(key);
                                    //累加判断是否有多个对象
                                    count++;
                                }
                            }
                            if (count > 1) {
                                throw new BeanFactoryException("需要一个对象，但是找到多个");
                            } else {
                                field.set(object,injectObject);
                            }
                        //通过byName的自动装配方式  直接通过属性名去map中拿就好
                        } else {
                            field.setAccessible(true);
                            field.set(object,map.get(field.getName()));;
                        }
                    }
                }

                map.put(name,object);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取bean
     * @param beanName
     * @return
     */
    public Object getBean(String beanName) {
        return map.get(beanName);
    }

}
