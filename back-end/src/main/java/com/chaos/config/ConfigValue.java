package com.chaos.config;

import com.chaos.vo.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
public class ConfigValue {

    public static Integer port;
    public static String serverIp;
    public static Integer serverPort;


    public static Map<String,String> appMap=new HashMap<>();

    public static Map<String,User> tokenMap=new HashMap<>();

    @PostConstruct
    public void init(){

    }

    @Value("${netty.port}")
    public void setPort(Integer port) {
        ConfigValue.port = port;
    }

    @Value("${netty.serverIp}")
    public void setServerIp(String serverIp) {
        ConfigValue.serverIp = serverIp;
    }

    @Value("${netty.serverPort}")
    public void setServerPort(Integer serverPort) {
        ConfigValue.serverPort = serverPort;
    }

}
