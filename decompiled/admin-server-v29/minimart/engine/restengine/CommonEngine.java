/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.io.Resource
 *  org.springframework.http.HttpEntity
 *  org.springframework.http.HttpHeaders
 *  org.springframework.http.HttpMethod
 *  org.springframework.http.HttpStatus
 *  org.springframework.http.ResponseEntity
 *  org.springframework.http.client.ClientHttpRequestFactory
 *  org.springframework.http.client.HttpComponentsClientHttpRequestFactory
 *  org.springframework.util.MultiValueMap
 *  org.springframework.web.client.ResponseErrorHandler
 *  org.springframework.web.client.RestClientException
 *  org.springframework.web.client.RestTemplate
 */
package minimart.engine.restengine;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import minimart.engine.restengine.RestErrorHandler;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class CommonEngine {
    private String server;
    private String privateToken;
    private RestTemplate rest;
    private HttpHeaders headers;
    private HttpStatus status;

    public CommonEngine() {
        this.server = "https://customer.go.sinarmart.id";
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(30000);
        factory.setReadTimeout(150000);
        this.rest = new RestTemplate((ClientHttpRequestFactory)factory);
        this.rest.setErrorHandler((ResponseErrorHandler)new RestErrorHandler());
        this.headers = new HttpHeaders();
        this.headers.add("Content-Type", "application/json");
        this.headers.add("Accept", "*/*");
    }

    public CommonEngine(String rootUrl) {
        this.server = rootUrl;
        this.rest = new RestTemplate();
        this.rest.setErrorHandler((ResponseErrorHandler)new RestErrorHandler());
        this.headers = new HttpHeaders();
        this.headers.add("Content-Type", "application/json");
        this.headers.add("Accept", "*/*");
    }

    public void setPrivateToken(String token) {
        this.privateToken = token;
        if (this.headers.containsKey((Object)"token")) {
            this.headers.set("token", this.privateToken);
        } else {
            this.headers.add("token", this.privateToken);
        }
    }

    public String get(String uri) {
        HttpEntity requestEntity = new HttpEntity((Object)"", (MultiValueMap)this.headers);
        ResponseEntity responseEntity = this.rest.exchange(this.server + uri, HttpMethod.GET, requestEntity, String.class, new Object[0]);
        this.setStatus(responseEntity.getStatusCode());
        return (String)responseEntity.getBody();
    }

    public String post(String uri, String json) {
        HttpEntity requestEntity = new HttpEntity((Object)json, (MultiValueMap)this.headers);
        ResponseEntity responseEntity = this.rest.exchange(this.server + uri, HttpMethod.POST, requestEntity, String.class, new Object[0]);
        this.setStatus(responseEntity.getStatusCode());
        return (String)responseEntity.getBody();
    }

    public InputStream postStream(String uri, String json) {
        HttpEntity requestEntity = new HttpEntity((Object)json, (MultiValueMap)this.headers);
        try {
            ResponseEntity responseEntity = this.rest.exchange(this.server + uri, HttpMethod.POST, requestEntity, Resource.class, new Object[0]);
            this.setStatus(responseEntity.getStatusCode());
            Resource resource = (Resource)responseEntity.getBody();
            if (resource == null) {
                throw new RuntimeException("Response body is null");
            }
            return resource.getInputStream();
        }
        catch (IOException | RestClientException e) {
            throw new RuntimeException("Failed to send POST request to " + uri, e);
        }
    }

    public InputStream postStreamNew(String uri, String json) {
        HttpEntity requestEntity = new HttpEntity((Object)json, (MultiValueMap)this.headers);
        try {
            ResponseEntity responseEntity = this.rest.exchange(this.server + uri, HttpMethod.POST, requestEntity, byte[].class, new Object[0]);
            this.setStatus(responseEntity.getStatusCode());
            byte[] body = (byte[])responseEntity.getBody();
            if (body == null) {
                throw new RuntimeException("Response body is null");
            }
            return new ByteArrayInputStream(body);
        }
        catch (RestClientException e) {
            throw new RuntimeException("Failed to send POST request to " + uri, e);
        }
    }

    public HttpStatus getStatus() {
        return this.status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}

