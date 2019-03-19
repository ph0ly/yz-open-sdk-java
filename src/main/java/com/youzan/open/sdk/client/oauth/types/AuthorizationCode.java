package com.youzan.open.sdk.client.oauth.types;

import com.fasterxml.jackson.core.type.TypeReference;
import com.youzan.open.sdk.client.oauth.model.OAuthToken;
import com.youzan.open.sdk.util.http.DefaultHttpClient;
import com.youzan.open.sdk.util.http.HttpClient;
import com.youzan.open.sdk.util.json.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.ContentType;

import java.awt.*;
import java.net.URI;

/**
 * @author ph0ly
 * @time 2017-07-05
 */
public class AuthorizationCode extends AbstractOAuth {

    private String redirectUrl;
    private String state;
    private String code;

    public AuthorizationCode(String clientId, String clientSecret, String redirectUrl, String state) {
        super(clientId, clientSecret);
        this.redirectUrl = redirectUrl;
        this.state = state;
    }

    public AuthorizationCode(String clientId, String clientSecret, String redirectUrl, String state, String code) {
        super(clientId, clientSecret);
        this.redirectUrl = redirectUrl;
        this.state = state;
        this.code = code;
    }

    /**
     * 该方法将跳转到浏览器，code将传入回调地址的queryString上
     * @return
     */
    public void getCode() {
        if (!Desktop.isDesktopSupported()) {
            throw new RuntimeException("无桌面情况下不支持获取code，请手工获取code，详情参见：https://www.youzanyun.com/docs/guide/common/680");
        }
        try {
            Desktop.getDesktop().browse(new URI("https://open.youzan.com/oauth/authorize?client_id=" + clientId + "&response_type=code&state=" + state +"&redirect_uri=" + redirectUrl));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 根据用户获取的授权code，获取token
     * @return
     */
    public OAuthToken getToken() {
        if (StringUtils.isBlank(code)) {
            throw new RuntimeException("授权码不能为空，请先调用getCode方法获取code后，调用setCode方法，再调用该方法获取token");
        }
        HttpClient httpClient = new DefaultHttpClient();
        HttpClient.Params params = HttpClient.Params.custom().add("client_id", clientId).add("client_secret", clientSecret).add("grant_type", "authorization_code").add("code", code).add("redirect_uri", redirectUrl).
                setContentType(ContentType.APPLICATION_FORM_URLENCODED).build();
        String resp = httpClient.post("https://open.youzan.com/oauth/token", params);
        if (StringUtils.isBlank(resp) || !resp.contains("access_token")) {
            throw new RuntimeException(resp);
        }
        return JsonUtils.toBean(resp, new TypeReference<OAuthToken>() {
        });
    }

}
