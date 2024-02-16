package ir.ds.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import ir.ds.config.MqttConfig;
import ir.ds.config.RunLater;
import ir.ds.dto.ClientConfigDTO;
import ir.ds.dto.InitBrowserDTO;
import ir.ds.dto.MyJwtClaims;
import ir.ds.model.Registered;
import ir.ds.model.RegisteredRepository;
import ir.ds.utils.Observer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class RegisteredService implements Observer {

    @Value("${mqtt.client.id}")
    private String clientId;
    @Autowired
    private RegisteredRepository registeredRepository;


    public Registered save(ClientConfigDTO obj) {
        Registered registered = registeredRepository.findByClientId(obj.getClientId());
        if (registered == null) {
            registered = new Registered();
            registered.setClientId(obj.getClientId());
            registered.setRegisterDate(new Date());
            registered.setUsername(RunLater.generateCode());
            registered.setPassword(RunLater.generateCode());
            registered.setMacAddress(obj.getMacAddress());
        }
        registered.setUpdateDate(new Date());
        registeredRepository.save(registered);
        return registered;
    }


    public static String generateToken(MyJwtClaims claims, String secretKey) {
       return "SSSSSSSSSSSSSSSSSSSSss";
    }

    @Value("${mqtt.broker.url}")
    private String brokerUrl;

    @Value("${mqtt.broker.port}")
    private int brokerPort;


    @Override
    public void clientRegistered(String clientId) {
        Registered regi = registeredRepository.findByClientId(clientId);
        String token = generateToken(new MyJwtClaims(regi.getClientId(), regi.getMacAddress()), clientId);
        String url = "http://192.168.1.5:8080/";
        String dest = clientId;
        String topic = "common";
        String server = brokerUrl;
        int port = brokerPort;

        InitBrowserDTO browserDto = new InitBrowserDTO(token, url, dest, topic, server, port);
        try {
            String messge = MqttConfig.mapper.writeValueAsString(browserDto);
            MqttConfig.publishMessage("command", messge);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}