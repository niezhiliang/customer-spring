package cn.isuyu.customer.spring.factory;

import cn.isuyu.customer.spring.annos.Autowired;
import cn.isuyu.customer.spring.annos.Service;

import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @GitHub https://github.com/niezhiliang
 * @Date 2019/12/15 3:51 下午
 */
public class AnnotationConfigApplicationContext {

    private static Set<String> set = new HashSet<String>(10);

    private static Map<String,Object> map = new HashMap<String, Object>(10);


    public void scm(String basePackage) {
        try {
            String rootPath = this.getClass().getResource("/").getPath();
            basePackage = basePackage.replaceAll("\\.","\\/");
            Set<String> classPaths = getclassPaths(rootPath.concat("/").concat(basePackage));
            for (String s : classPaths) {
                s = s.replace(rootPath,"");
                Class clazz = Class.forName(s.replaceAll("\\/","\\."));
                //如果是被注解修饰
                if (clazz.isAnnotationPresent(Service.class)) {
                    Service service = (Service) clazz.getAnnotation(Service.class);

                    Object object = clazz.newInstance();

                    Field [] fields = clazz.getDeclaredFields();
                    Object injectObject = null;
                    for (Field field : fields) {
                        if (field.isAnnotationPresent(Autowired.class)) {
                            field.setAccessible(true);
                            for(String key: map.keySet()) {
                                if (field.getType().getName().equals(map.get(key).getClass().getName())) {
                                    injectObject = map.get(key);
                                }
                            }
                        }
                        field.set(object,injectObject);
                    }
                    map.put(service.value(),object);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Object getBean(String beanName) {
        return map.get(beanName);
    }


    /**
     * 遍历指定目录下的所有类型文件
     * @param path
     * @return
     */
    private static Set<String> getclassPaths(String path) {
        File dir = new File(path);
        // 该文件目录下文件全部放入数组
        File[] files = dir.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                String fileName = files[i].getName();
                // 判断是文件还是文件夹
                if (files[i].isDirectory()) {
                    // 获取文件绝对路径
                    getclassPaths(files[i].getAbsolutePath());
                    // 判断文件名是否以.class结尾
                } else if (fileName.endsWith(".class")) {
                    String strFileName = files[i].getAbsolutePath();
                    set.add(files[i].getPath().replace(".class",""));
                } else {
                    continue;
                }
            }
        }
        return set;
    }

}
