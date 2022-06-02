package org.borg.ha;

import org.borg.data.ServerIps;

public class Random {

    public static String getServer(){
        java.util.Random random = new java.util.Random();
        int randomPos = random.nextInt(ServerIps.LIST.size());
        return ServerIps.LIST.get(randomPos);
    }

    public static void main(String[] args) {
        for (int i=0;i<10;i++){
            System.out.println(getServer());
        }
    }
}
