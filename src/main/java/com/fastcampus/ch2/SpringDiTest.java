package com.fastcampus.ch2;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

class Car{}
class Engine{}
class Door{}

public class SpringDiTest {
    public static void main(String[] args) {
        ApplicationContext ac = new GenericXmlApplicationContext("config.xml");
//        Car car = (Car) ac.getBean("car");
        Car car = ac.getBean("car", Car.class); // byName 아래와 같은 문장
        Car car2 = (Car) ac.getBean(Car.class);
        Engine engine = (Engine) ac.getBean("engine");
        Door door = (Door) ac.getBean("door");

        System.out.println("car = " + car);
        System.out.println("car2 = " + car2);
        System.out.println("engine = " + engine);
        System.out.println("door = " + door);
    }
}
