package com.ofg.microservice.github

import org.springframework.http.HttpRequest
import org.springframework.http.MediaType
import org.springframework.http.client.ClientHttpRequestExecution
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.http.client.ClientHttpResponse
import org.springframework.http.client.support.HttpRequestWrapper

/**
 * Created by Milosz Olszewski (olszewm1) on 2014-08-09.
 * http://svenfila.wordpress.com/2012/01/05/resttemplate-with-custom-http-headers/
 */
class AcceptHeaderHttpRequestInterceptor implements ClientHttpRequestInterceptor {

    public static final String GITHUB_HEADER_ACCEPT = "application/vnd.github.v3+json"

    @Override
    ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        HttpRequestWrapper requestWrapper = new HttpRequestWrapper(request);
        requestWrapper.getHeaders().setAccept(MediaType.valueOf(GITHUB_HEADER_ACCEPT));
        requestWrapper.getHeaders().setAccept(MediaType.valueOf(GITHUB_HEADER_ACCEPT));

        return execution.execute(requestWrapper, body);
    }
}
