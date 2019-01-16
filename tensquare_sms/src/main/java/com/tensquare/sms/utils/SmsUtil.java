package com.tensquare.sms.utils;

import com.zhenzi.sms.ZhenziSmsClient;
import lombok.extern.slf4j.Slf4j;
import org.omg.CORBA.portable.UnknownException;
import org.springframework.stereotype.Component;

/**
 * 短信工具类
 * 榛子云-短信用户中心
 * http://sms_developer.zhenzikj.com/zhenzisms_user/index.html#app/detail/585
 * 账号：18025446036
 * 密码：18025446036
 */
@Slf4j
@Component
public class SmsUtil {

    private final String apiUrl = "https://sms_developer.zhenzikj.com";
    private final String appId = "100545";
    private final String appSecret = "3ffb5ca8-7d47-4c22-bf5c-3c9ca2e575a0";

    public void sendSmsCheckCode(String mobile, String checkcode) {
        ZhenziSmsClient client = new ZhenziSmsClient(apiUrl, appId, appSecret);
        try {
            client.send(mobile, "您的短信验证码为：" + checkcode);
        } catch (Exception e) {
            log.error("发送短信验证码时发生异常");
            throw new UnknownException(e);
        }
    }

}
