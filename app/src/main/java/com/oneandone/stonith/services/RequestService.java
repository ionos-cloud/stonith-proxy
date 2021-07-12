package com.oneandone.stonith.services;

import com.oneandone.stonith.entities.RequestConfiguration;
import com.oneandone.stonith.errors.RequestException;
import com.oneandone.stonith.errors.RestTemplateResponseErrorHandler;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Map;

@Service
public class RequestService {
    @Autowired
    private RestTemplateResponseErrorHandler errorHandler;

    public void executeRequest(RequestConfiguration requestConfiguration) throws RequestException {
        RestTemplate restTemplate = this.getRestTemplate();
        HttpHeaders headers = this.getHeaders(requestConfiguration);
        String requestUrl = requestConfiguration.getServer().getManagementUrl() + requestConfiguration.getDialectConfiguration().getEndpoint();

        HttpEntity<String> httpEntity = new HttpEntity<>(requestConfiguration.getDialectConfiguration().getBody(), headers);
        ResponseEntity<Map> response = restTemplate.exchange(requestUrl,
                requestConfiguration.getDialectConfiguration().getMethod(), httpEntity, Map.class);
        if (response.getStatusCodeValue() < 200 || response.getStatusCodeValue() > 299) {
            throw new RequestException("Request failed with status code %d and body %s", response.getStatusCodeValue(), response.getBody());
        }
    }

    private RestTemplate getRestTemplate() throws RequestException {
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        HttpClientBuilder httpClientBuilder;

        try {
            SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
                    .loadTrustMaterial(null, (chain, authType) -> true)
                    .build();
            SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());
            httpClientBuilder = HttpClients.custom().setSSLSocketFactory(csf);
        } catch (NoSuchAlgorithmException | KeyManagementException | KeyStoreException e) {
            throw new RequestException(e, "Failed to execute request to server");
        }
        CloseableHttpClient httpClient = httpClientBuilder.build();
        requestFactory.setHttpClient(httpClient);
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        restTemplate.setErrorHandler(this.errorHandler);
        return restTemplate;
    }

    private HttpHeaders getHeaders(RequestConfiguration requestConfiguration) {
        HttpHeaders headers = new HttpHeaders();
        for (Map.Entry<String, String> entry : requestConfiguration.getDialectConfiguration().getHeaders().entrySet()) {
            headers.add(entry.getKey(), entry.getValue());
        }

        String userAndPassword = requestConfiguration.getServer().getLoginUser() + ":" + requestConfiguration.getServer().getLoginPassword();
        headers.add("authorization", "Basic " + Base64.getEncoder().encodeToString(userAndPassword.getBytes()));
        return headers;
    }
}
