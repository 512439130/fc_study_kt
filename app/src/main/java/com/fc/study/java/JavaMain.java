package com.fc.study.java;

import com.fc.study.kt.ConstObject;
import com.fc.study.kt.TopTestUtil;
import com.fc.study.stat.SingleClassTest;
import com.fc.study.stat.StaticClassTest;
import com.fc.study.stat.StaticUtil;


public class JavaMain {
    public static void main(String[] args) {


        //1.1 伴生类-@JvmStatic-静态方法
        StaticClassTest.Test.doStaticAction();
        //1.2 单例类-@JvmStatic-静态方法
        SingleClassTest.doStaticAction();

        //2.顶层方法-静态方法
        StaticUtil.topTest();

        //单例类
        SingleClassTest.INSTANCE.doAction();
        //伴生类
        StaticClassTest.Test.doAction();
        //顶层方法
        TopTestUtil.fileTest();

        //单例类
        ConstObject.INSTANCE.test();
    }
}
