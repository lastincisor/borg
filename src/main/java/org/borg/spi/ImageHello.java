package org.borg.spi;

public class ImageHello implements HelloSPI{
    @Override
    public void sayHello() {
        System.out.println("Image Hello");
    }
}
