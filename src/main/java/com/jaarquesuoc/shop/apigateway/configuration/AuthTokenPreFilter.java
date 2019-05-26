package com.jaarquesuoc.shop.apigateway.configuration;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

@Component
public class AuthTokenPreFilter extends ZuulFilter {

    @Override
    public int filterOrder() {
        return PRE_DECORATION_FILTER_ORDER - 1; // run before PreDecoration
    }

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.addZuulRequestHeader("Authorization", "Basic " + base64Encode("my-client:secret"));
        return null;
    }

    private static String base64Encode(String token) {
        byte[] encodedBytes = Base64.encode(token.getBytes());
        return new String(encodedBytes, Charset.forName("UTF-8"));
    }
}
