package com.yhxx.wxappboot.configuration;

import com.yhxx.common.utils.CloseUtils;
import com.yhxx.common.utils.netUtils.HttpUtils;
import com.yhxx.common.utils.netUtils.manager.api.HttpManager;
import com.yhxx.common.utils.netUtils.manager.impl.HttpManagerImpl;
import com.yhxx.wxapp.domain.constant.WxAppConstant;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLContext;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * @Author: Wanglf
 * @Date: Created in 17:20 2018/6/10
 * @modified By:
 */
@Configuration
public class CloudConfiguration {

    @Bean
    public HttpUtils httpUtils() {

        HttpUtils httpUtils = new HttpUtils();
        httpUtils.setHttpClient(httpClient());
        return httpUtils;
    }

    @Bean
    public HttpClient httpClient() {
        CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(requestConfig())
                .setConnectionManager(httpClientConnectionManager())
                .build();

        return httpClient;
    }

    @Bean
    public RequestConfig requestConfig() {
        RequestConfig defaultRequestConfig = RequestConfig.custom()
                .setSocketTimeout(60000)
                .setConnectTimeout(10000)
                .setConnectionRequestTimeout(10000)
                .build();

        return defaultRequestConfig;
    }

    @Bean(destroyMethod = "shutdown")
    public HttpClientConnectionManager httpClientConnectionManager() {
        PoolingHttpClientConnectionManager httpClientConnectionManager = new PoolingHttpClientConnectionManager();
        httpClientConnectionManager.setMaxTotal(10);
        httpClientConnectionManager.setDefaultMaxPerRoute(5);
        return httpClientConnectionManager;
    }

    //创建使用证书的http请求，独立于之前的httpUtils ，保证每个证书使用不同的请求链接，不会相互影响
    @Bean
    public HttpManager httpManagerOfCertificate() {
        HttpManagerImpl httpManager = new HttpManagerImpl();
        httpManager.setHttpClient(httpClientOfCertificate());
        return httpManager;
    }

    @Bean
    public HttpClient httpClientOfCertificate() {
        CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(requestConfig())
                .setConnectionManager(httpClientConnectionOfCertificateManager())
                .build();

        return httpClient;
    }

    @Bean
    public HttpClientConnectionManager httpClientConnectionOfCertificateManager() {

        PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager(
                RegistryBuilder.<ConnectionSocketFactory>create()
                        .register("http", PlainConnectionSocketFactory.getSocketFactory())
                        .register("https", wxAppOfCertificateSSLConnectionSocketFactory())
                        .build());
        return poolingHttpClientConnectionManager;
    }

    @Bean
    public ConnectionSocketFactory wxAppOfCertificateSSLConnectionSocketFactory() {

        SSLConnectionSocketFactory sslConnectionSocketFactory = null;
        InputStream instream = null;

        try {
            instream = CloudConfiguration.class.getResourceAsStream("/static/app_apiclent_cert.p12");
            //设置类型为证书或者公钥类型
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            //设置下载的密码
            keyStore.load(instream, WxAppConstant.WXAPP_MCH_ID.toCharArray());

            SSLContext sslContext = SSLContexts.custom().loadKeyMaterial(keyStore, WxAppConstant.WXAPP_MCH_ID.toCharArray()).build();
            sslConnectionSocketFactory = new SSLConnectionSocketFactory(
                    sslContext,
                    new String[]{"TLSv1"},
                    null,
                    new DefaultHostnameVerifier());
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            CloseUtils.close(instream);
        }

        return sslConnectionSocketFactory;
    }

    //忽略证书的http链接的创建
    @Bean
    public HttpManager httpManagerOfNoCertificate() {

        HttpManagerImpl httpManager = new HttpManagerImpl();
        httpManager.setHttpClient(httpClientOfNoCertificate());
        return httpManager;
    }

    @Bean
    public HttpClient httpClientOfNoCertificate() {

        CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(requestConfig())
                .setConnectionManager(httpClientConnectionOfNoCertificateManager())
                .build();
        return httpClient;
    }

    @Bean
    public HttpClientConnectionManager httpClientConnectionOfNoCertificateManager() {

        PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager(
                RegistryBuilder.<ConnectionSocketFactory>create()
                        .register("http", PlainConnectionSocketFactory.getSocketFactory())
                        .register("https", wxAppOfNoCertificateSSLConnectionSocketFactory())
                        .build()
        );

        return poolingHttpClientConnectionManager;
    }

    @Bean
    public ConnectionSocketFactory wxAppOfNoCertificateSSLConnectionSocketFactory() {

        SSLConnectionSocketFactory sslConnectionSocketFactory = null;

        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(
                    null, new TrustStrategy() {
                        // 信任所有
                        @Override
                        public boolean isTrusted(X509Certificate[] chain,
                                                 String authType) throws CertificateException {
                            return true;
                        }
                    }).build();
            sslConnectionSocketFactory = new SSLConnectionSocketFactory(
                    sslContext, new String[]{"TLSv1"},
                    null,
                    SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

            return sslConnectionSocketFactory;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
