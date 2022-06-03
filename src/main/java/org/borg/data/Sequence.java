package org.borg.data;

public class Sequence {

    public static Integer num = 0;

    public static Integer getAndIncrement (){
        return ++num;
    }
}
