package org.borg.ha;

import org.borg.data.ServerIps;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class ConsistentHash {

    public static TreeMap<Integer,String> virtualNodes = new TreeMap<>();

    private static final int VITTUAL_NODES = 160;

    static {
        for (String ip : ServerIps.LIST){
            for (int i = 0;i< VITTUAL_NODES;i++){
                int hash = getHash(ip + "VN" + i);
                virtualNodes.put(hash,ip);
            }
        }
    }


    public static int getHash(String client){
        return client.hashCode();
    }
    public static String getServer(String client){
        int hash = getHash(client);
        SortedMap<Integer,String> subMap = virtualNodes.tailMap(hash);
        Integer key = null;
        if(subMap == null || subMap.isEmpty()){
            key = virtualNodes.firstKey();
        }else {
            key = subMap.firstKey();
        }
        return virtualNodes.get(key);

    }

    public static void main(String[] args) {
        for (int i=0;i<1000;i++){
            System.out.println(getServer("cli"+i));
        }
    }

}
