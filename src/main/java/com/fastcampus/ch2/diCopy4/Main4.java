package com.fastcampus.ch2.diCopy4;

import com.google.common.reflect.ClassPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component class Car{
    @Resource
    Engine engine;
//    @Resource
    Door door;

    @Override
    public String toString() {
        return "Car{" +
                "engine=" + engine +
                ", door=" + door +
                '}';
    }
}

@Component class SportsCar extends Car{}

@Component class Truck extends Car{}

@Component class Engine{}

@Component class Door{}

class AppContext{
    Map map;

    AppContext(){

        map = new HashMap();
        doComponentScan();
        doAutowired();
        doResource();
    }

    private void doResource() {
        //map�� ����� ��ü�� iv�߿� @Resource �پ� ������
        //map���� iv�� �̸� �´� ��ü�� �Ƽ� ����(��ü�� �ּҸ� iv�� ����)ã
        try {
            for(Object bean : map.values()){
                for(Field field : bean.getClass().getDeclaredFields()){
                    if(field.getAnnotation(Resource.class) != null) //byName
                        field.set(bean, getBean(field.getName())); // car.engine = obj;
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void doAutowired() {
        //map�� ����� ��ü�� iv�߿� @Autowired�� �پ� ������
        //map���� iv�� Ÿ�Կ� �´� ��ü�� �Ƽ� ����(��ü�� �ּҸ� iv�� ����)ã
        try {
            for(Object bean : map.values()){
                for(Field field : bean.getClass().getDeclaredFields()){
                    if(field.getAnnotation(Autowired.class) != null) // byType
                        field.set(bean, getBean(field.getType())); // car.engine = obj;
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void doComponentScan() {
        try {
            // 1.��Ű�� ���� Ŭ���� ����� �����´�.
            // 2. �ݺ������� Ŭ������ �ϳ��� �о�ͼ� @Component�� �پ� �ִ��� Ȯ��
            // 3. @Component�� �پ� ������ ��ü�� �����ؼ� map�� ����
            ClassLoader classLoader = AppContext.class.getClassLoader();
            ClassPath classPath = ClassPath.from(classLoader);

            Set<ClassPath.ClassInfo> set = classPath.getTopLevelClasses("com.fastcampus.ch2.diCopy4");

            for(ClassPath.ClassInfo classInfo : set){
                Class clazz = classInfo.load();
                Component component = (Component) clazz.getAnnotation(Component.class);
                if(component != null){
                    String id = StringUtils.uncapitalize(classInfo.getSimpleName());
                    map.put(id, clazz.newInstance());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Object getBean(String key){ //byName
        return map.get(key);
   }
   Object getBean(Class clazz){ //byType
        for(Object obj : map.values()){
            if(clazz.isInstance(obj))
                return obj;
        }
        return null;
   }
}

public class Main4 {
    public static void main(String[] args) throws Exception{
        AppContext ac = new AppContext();
        Car car = (Car) ac.getBean("car"); //byName���� ��ü �˻�
        Engine engine = (Engine) ac.getBean("engine");
        Door door = (Door) ac.getBean(Door.class); //byType

        //��ü�� �������� ����
//        car.engine = engine;
//        car.door = door;

        System.out.println("car = " + car);
        System.out.println("engine = " + engine);
        System.out.println("door = " + door);
    }

}

