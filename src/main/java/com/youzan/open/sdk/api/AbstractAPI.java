package com.youzan.open.sdk.api;

import com.google.common.collect.Maps;
import com.youzan.open.sdk.model.APIParams;

import java.util.Map;

/**
 * @author ph0ly
 * @time 2016-11-28
 */
public abstract class AbstractAPI implements API {

    protected APIParams params;

    public APIParams getAPIParams() {
        return this.params;
    }

    public void setAPIParams(APIParams apiParams) {
        this.params = apiParams;
    }

    public String getHttpUrl() {
        return "https://open.youzan.com/api";
    }

    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public Map<String, String> getHeaders() {
        return Maps.newHashMap();
    }
}
