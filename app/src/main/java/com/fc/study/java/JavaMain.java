package com.fc.study.java;

import com.fc.study.kt.TopTestUtil;
import com.fc.study.stat.SingleClassTest;
import com.fc.study.stat.StaticClassTest;
import com.fc.study.stat.StatusUtil;


public class JavaMain {
    public static void main(String[] args) {


        //1.1 伴生类-@JvmStatic-静态方法
        StaticClassTest.Test.doStatusAction();
        //1.2 单例类-@JvmStatic-静态方法
        SingleClassTest.doStaticAction();

        //2.顶层方法-静态方法
        StatusUtil.topTest();

        //单例类
        SingleClassTest.INSTANCE.doAction();
        //伴生类
        StaticClassTest.Test.doAction();
        //顶层方法
        TopTestUtil.fileTest();
    }
}
