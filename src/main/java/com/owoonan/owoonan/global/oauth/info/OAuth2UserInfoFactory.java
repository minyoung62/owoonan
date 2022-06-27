package com.owoonan.owoonan.global.oauth.info;



import com.owoonan.owoonan.domain.user.domain.vo.ProviderType;
import com.owoonan.owoonan.global.oauth.info.impl.KakaoOAuth2UserInfo;
import com.owoonan.owoonan.global.oauth.info.impl.NaverOAuth2UserInfo;

import java.util.Map;

public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(ProviderType providerType, Map<String, Object> attributes) {
        switch (providerType) {
            case KAKAO: return new KakaoOAuth2UserInfo(attributes);
            case NAVER: return new NaverOAuth2UserInfo(attributes);
            default: throw new IllegalArgumentException("Invalid Provider Type.");
        }
    }
}
