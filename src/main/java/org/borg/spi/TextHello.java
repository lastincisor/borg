package org.borg.spi;

public class TextHello implements HelloSPI{
    @Override
    public void sayHello() {
        System.out.println("Text Hello");
    }
}
