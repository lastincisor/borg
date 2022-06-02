package org.borg.ha;

import org.borg.data.Sequence;
import org.borg.data.ServerIps;

public class WeightRoundRobin {

    public static String getServer(){
        int sumWeight = 0;
        for (Integer weight : ServerIps.WEIGHT_MAP.values()){
            sumWeight += weight;
        }

        Integer requestId = Sequence.getAndIncrement();
        int offset = requestId % sumWeight;


        for (String ip : ServerIps.WEIGHT_MAP.keySet()){
            Integer weight = ServerIps.WEIGHT_MAP.get(ip);
            if(offset < weight){
                return ip;
            }
            offset = offset - weight;
        }
        return null;
    }

    public static void main(String[] args) {
        for (int i=0;i<100;i++){
            System.out.println(getServer());
        }
    }
}
