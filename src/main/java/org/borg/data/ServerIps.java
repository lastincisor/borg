package org.borg.data;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ServerIps {

    public static final List<String> LIST = Arrays.asList(
            "192.168.0.1",
            "192.168.0.2",
            "192.168.0.3",
            "192.168.0.4",
            "192.168.0.5",
            "192.168.0.6",
            "192.168.0.7",
            "192.168.0.8",
            "192.168.0.9",
            "192.168.0.10",
            "192.168.0.11",
            "192.168.0.12",
            "192.168.0.13",
            "192.168.0.14",
            "192.168.0.15",
            "192.168.0.16",
            "192.168.0.17",
            "192.168.0.18",
            "192.168.0.19",
            "192.168.0.20"
    );

    public static final Map<String,Integer> WEIGHT_MAP = new LinkedHashMap<>();

    static {
        WEIGHT_MAP.put("192.168.0.1",50);
        WEIGHT_MAP.put("192.168.0.2",30);
        WEIGHT_MAP.put("192.168.0.3",20);
        WEIGHT_MAP.put("192.168.0.4",10);
    }
}
