package org.borg.ha;

import org.borg.data.ServerIps;

import java.util.ArrayList;
import java.util.List;

public class WeightRandomV2 {

    public static String getServer(){
        int sumWeight = 0;
        for (Integer weight : ServerIps.WEIGHT_MAP.values()){
            sumWeight += weight;
        }
        java.util.Random random = new java.util.Random();
        int randomPos = random.nextInt(sumWeight);

        for (String ip : ServerIps.WEIGHT_MAP.keySet()){
            Integer weight = ServerIps.WEIGHT_MAP.get(ip);
            if(randomPos < weight){
                return ip;
            }
            randomPos = randomPos - weight;
        }
        return null;
    }

    public static void main(String[] args) {
        for (int i=0;i<100;i++){
            System.out.println(getServer());
        }
    }
}
