package com.youzan.open.sdk.client.oauth;

import com.youzan.open.sdk.client.oauth.types.*;

/**
 * @author ph0ly
 * @time 2017-07-06
 */
public class OAuthFactory {

    private OAuthFactory() {

    }

    /**
     * 创建一个鉴权类
     * @param type
     * @param context
     * @return
     */
    public static OAuth create(OAuthType type, OAuthContext context) {
        if (type == null || context == null) {
            throw new RuntimeException("Param error, type or context must be specified");
        }
        switch (type) {
            case SELF:
                return new Silent(context.getClientId(), context.getClientSecret(), context.getKdtId());
            case PLATFORM:
                return new AuthorizePlatform(context.getClientId(), context.getClientSecret(), context.getKdtId());
            case TOOL_REFRESH_TOKEN:
                return new RefreshToken(context.getClientId(), context.getClientSecret(), context.getRefreshToken());
            case TOOL_TOKEN:
                return new AuthorizationCode(context.getClientId(), context.getClientSecret(), context.getRedirectUrl(), context.getState(), context.getCode());
            default:
                throw new RuntimeException("Unknown auth type, please check your param");
        }
    }

}
