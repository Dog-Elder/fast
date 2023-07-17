package com.fast.core.common.util.wx;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.fast.core.common.util.SUtil;
import com.fast.core.common.exception.ServiceException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @program: xxxxx
 * @description: 微信工具类
 * @author: @DogElder
 * @create: 2021-04-13 23:51
 **/
@Log4j2
@Service
public class WxUtils {
    /**
     * 微信公众号appid
     */
    private static final String APPID = "wx7b142952297ab8fb";
    /**
     * 微信公众号secret
     */
    private static final String SECRET = "148c90777374da724e03d74fbcd6730d";
    /**
     * 域名
     */
    private static final String DOMAIN_NAME = "http:// 8sg95s.natappfree.cc";


    /**
     * 获取微信的code
     *
     * @param response 响应体
     * @return 重定向
     */
    public String getWxCode(HttpServletResponse response) {
        //  第一步：用户同意授权，获取code
        StringBuilder path = new StringBuilder();
        // 微信公众号appid
        path.append("https:// open.weixin.qq.com/connect/oauth2/authorize?appid=").append(APPID);
        // 重定向的地址
        path.append("&redirect_uri=").append(DOMAIN_NAME).append("/getWxgzhUser");
        path.append("&response_type=code");
        /*
         *以snsapi_base为scope发起的网页授权，是用来获取进入页面的用户的openid的，并且是静默授权并自动跳转到回调页的。用户感知的就是直接进入了回调页（往往是业务页面）
         *以snsapi_userinfo为scope发起的网页授权，是用来获取用户的基本信息的。但这种授权需要用户手动同意，并且由于用户同意过，所以无须关注，就可在授权后获取该用户的基本信息。
         */
        path.append("&scope=").append("snsapi_userinfo");
        path.append("&state=STATE");
        path.append("&connect_redirect=1#wechat_redirect");
        try {
            response.sendRedirect(path.toString());
        } catch (IOException e) {
            log.error("获取微信code失败: " + e.getMessage());
        }
        // 必须重定向，否则不能成功
        return "redirect:" + path.toString();
    }

    /**
     * 获取openid和access_token 这里的 access_token与基本的access_token不是一个东西具体看官方文档
     * @param code 微信code 可以通过后台获取 或者 让前端传过来
     * @return
     */
    public Map<String, Object> getOpenId(@NotBlank(message = "微信code不能为空/空串") String code) {
       return this.getOpenId(APPID, SECRET, code);
    }

    /**
     * 获取openid和access_token 这里的 access_token与基本的access_token不是一个东西具体看官方文档
     * @param appid  微信公众号appid
     * @param secret 微信公众号secret
     * @param code   微信code 可以通过后台获取 或者 让前端传过来
     * @return
     */
    public Map<String, Object> getOpenId(@NotBlank(message = "微信appid不能为空/空串") String appid
            , @NotBlank(message = "微信secret不能为空/空串") String secret
            , @NotBlank(message = "微信code不能为空/空串") String code) {
        StringBuilder path = new StringBuilder();
        path.append("https:// api.weixin.qq.com/sns/oauth2/access_token?appid=").append(appid);
        path.append("&secret=").append(secret);
        path.append("&code=").append(code);
        path.append("&grant_type=authorization_code");

        // 发送请求 响应微信返回的参数
        String res = HttpUtil.get(path.toString());
        JSONObject jsonObject = JSONUtil.parseObj(res);
        String openid = jsonObject.getStr("openid");
        String access_token = jsonObject.getStr("access_token");
        if (SUtil.isEmpty(openid) || SUtil.isEmpty(access_token)) {
            // log.error("openid或access_token参数为空");
            throw new ServiceException("openid或access_token参数为空");
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("openid", openid);
        map.put("access_token", access_token);
        return map;
    }

    /**
     * 获取用户信息
     *
     * @param openid 用户openid
     * @param accessToken 用户access_token
     * @return
     */
    public Map<String, Object> getUserInfo(@NotBlank(message = "微信openid不能为空/空串")String openid,@NotBlank(message = "微信accessToken不能为空/空串") String accessToken) {
        StringBuilder path = new StringBuilder();
        path.append("https:// api.weixin.qq.com/sns/userinfo?access_token=").append(accessToken);
        path.append("&openid=").append(openid);
        String res = HttpUtil.get(path.toString());
        JSONObject jsonObject = JSONUtil.parseObj(res);
        Map<String, Object> map = jsonToMap(jsonObject);
        return map;
    }


    /**
    * @Description: json对象转为map
    * @Param: jsonObject
    * @return: Map
    * @Author:黄嘉浩
    * @Date: 2021/4/14
    */
    public static Map<String,Object> jsonToMap(JSONObject jsonObject){
        Map<String, Object> map = new HashMap<String, Object>();
        Iterator it = jsonObject.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Object> entry = (Map.Entry<String, Object>) it.next();
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }


}
