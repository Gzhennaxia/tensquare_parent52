package com.tensquare.sms.listener;

import com.tensquare.sms.utils.SmsUtil;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RabbitListener
public class SmsListener {

    @Autowired
    private SmsUtil smsUtil;

    @RabbitHandler
    public void executeSms(Map<String, String> map) {
        smsUtil.sendSmsCheckCode(map.get("mobile"), map.get("checkcode"));
        System.out.println("手机号：" + map.get("mobile"));
        System.out.println("验证码：" + map.get("checkcode"));
    }
}
