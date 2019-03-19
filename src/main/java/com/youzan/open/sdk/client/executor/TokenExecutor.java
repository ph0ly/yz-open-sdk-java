package com.youzan.open.sdk.client.executor;

import com.google.common.collect.Maps;
import com.youzan.open.sdk.api.API;
import com.youzan.open.sdk.client.auth.Token;
import com.youzan.open.sdk.model.APIParams;
import com.youzan.open.sdk.util.http.HttpClient;
import com.youzan.open.sdk.util.misc.TimeUtil;

import java.util.Date;
import java.util.Map;

/**
 * @author ph0ly
 * @time 2016-11-28
 */
public class TokenExecutor extends AbstractExecutor implements Executor {

    private Token token;

    public TokenExecutor(HttpClient httpClient, Token token) {
        this.token = token;
        this.httpClient = httpClient;
    }

    @Override
    protected void prepare(ExecutionContext context) {
        API api = context.getApi();

        APIParams apiParams = api.getAPIParams();

        Map<String, Object> params = apiParams.toParams();

        Map<String, String> finalParams = Maps.newHashMap();

        for (String key : params.keySet()) {
            Object value = params.get(key);
            String newValue = value.toString();
            if (value instanceof Date) {
                Date tmp = (Date)value;
                newValue = TimeUtil.formatTime(tmp);
            }
            finalParams.put(key, newValue);
        }

        finalParams.put("access_token", token.getAccessToken());

        context.setParams(finalParams);
        context.setUrl(api.getHttpUrl() + "/oauthentry");
    }
}
