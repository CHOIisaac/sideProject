package com.fastcampus.ch2.aop;

import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AopMain {
    public static void main(String[] args) throws Exception{
        MyAdvice myAdvice = new MyAdvice();

        Class myclass= Class.forName("com.fastcampus.ch2.aop.MyClass");
        Object obj = myclass.newInstance();

        for(Method n : myclass.getDeclaredMethods()){
            myAdvice.invoke(n, obj, null);
        }

    }
}

class MyAdvice{
    Pattern p = Pattern.compile("a.*"); //a로 시작하는 단어

    boolean matchers(Method m){
        Matcher matcher = p.matcher(m.getName());
        return matcher.matches();
    }

    void invoke(Method m, Object obj, Object... args) throws Exception{
        if(matchers(m))
            System.out.println("[before]{");

            m.invoke(obj, args);

            if(matchers(m))
            System.out.println("}[after}");

    }
}

class MyClass{
    void aaa(){
        System.out.println("aaa() is called. ");
    }
    void aaa2(){
        System.out.println("aaa2() is called. ");
    }
    void bbb(){
        System.out.println("bbb() is called. ");
    }
}