package com.youzan.open.sdk.client.oauth.types;

import com.fasterxml.jackson.core.type.TypeReference;
import com.youzan.open.sdk.client.oauth.model.OAuthToken;
import com.youzan.open.sdk.util.http.DefaultHttpClient;
import com.youzan.open.sdk.util.http.HttpClient;
import com.youzan.open.sdk.util.json.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.ContentType;

/**
 * @author ph0ly
 * @time 2017-07-06
 */
public class AuthorizePlatform extends AbstractOAuth {

    private Long kdtId;

    public AuthorizePlatform(String clientId, String clientSecret, Long kdtId) {
        super(clientId, clientSecret);
        this.kdtId = kdtId;
        if (kdtId == null) {
            throw new RuntimeException("店铺id不能为空");
        }
    }

    public OAuthToken getToken() {
        HttpClient httpClient = new DefaultHttpClient();
        HttpClient.Params params = HttpClient.Params.custom().add("client_id", clientId).add("client_secret", clientSecret).add("grant_type", "authorize_platform").add("kdt_id", kdtId.toString()).
                setContentType(ContentType.APPLICATION_FORM_URLENCODED).build();
        String resp = httpClient.post("https://open.youzan.com/oauth/token", params);
        if (StringUtils.isBlank(resp) || !resp.contains("access_token")) {
            throw new RuntimeException(resp);
        }
        return JsonUtils.toBean(resp, new TypeReference<OAuthToken>() {
        });
    }

}
