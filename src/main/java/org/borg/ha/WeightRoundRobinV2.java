package org.borg.ha;

import org.borg.data.Sequence;
import org.borg.data.ServerIps;
import org.borg.data.Weight;

import java.util.HashMap;
import java.util.Map;

public class WeightRoundRobinV2 {

    private static Map<String, Weight> weightMap = new HashMap<>();

    static {
        for (String ip : ServerIps.WEIGHT_MAP.keySet()){
            weightMap.put(ip,new Weight(ip,ServerIps.WEIGHT_MAP.get(ip),0));
        }
    }

    public static String getServer(){

        int sumWeight = 0;
        for (Integer weight : ServerIps.WEIGHT_MAP.values()){
            sumWeight += weight;
        }

        for (Weight weight : weightMap.values()){
            weight.setCurrentWeight(weight.getCurrentWeight() + weight.getWeight());
        }


        Weight maxCurrentWeight = null;
        for (Weight weight : weightMap.values()){
            if(maxCurrentWeight == null || maxCurrentWeight.getCurrentWeight() < weight.getCurrentWeight()){
                maxCurrentWeight = weight;
            }
        }
        maxCurrentWeight.setCurrentWeight(maxCurrentWeight.getCurrentWeight() - sumWeight);
        return maxCurrentWeight.getIp();
    }

    public static void main(String[] args) {
        for (int i=0;i<100;i++){
            System.out.println(getServer());
        }
    }
}
