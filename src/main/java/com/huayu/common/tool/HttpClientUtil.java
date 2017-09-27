package com.huayu.common.tool;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class HttpClientUtil {
    private static final Logger log = LoggerFactory.getLogger(HttpClientUtil.class);

    private HttpClient client;
    private HttpMethodBase httpMethod;

    public HttpClientUtil(HttpMethodBase method) {
        this.httpMethod = method;
        this.client = new HttpClient();
        this.client.getParams().setParameter("http.protocol.content-charset", "UTF-8");
    }

    public HttpClientUtil(String proxyHost, int proxyPort) {
        this.client = null;
        this.httpMethod = null;
        this.client = new HttpClient();
        this.client.getParams().setParameter("http.protocol.content-charset", "UTF-8");
        setProxy(client, proxyHost , proxyPort);
    }

    public HttpClientUtil(int timeoutInMilliseconds) {
        this(null);
        HttpConnectionManagerParams ps = this.client.getHttpConnectionManager().getParams();
        ps.setSoTimeout(timeoutInMilliseconds);
        ps.setConnectionTimeout(timeoutInMilliseconds);
    }

    public void setRequestHeader(String name, String value) {
        this.httpMethod.setRequestHeader(name, value);
    }

    public void addParameter(String name, String value) throws IllegalArgumentException {
        if(name != null && value != null) {
            if(this.httpMethod instanceof GetMethod) {
                String q = this.httpMethod.getQueryString();
                if(q == null) {
                    this.httpMethod.setQueryString(name + "=" + value);
                } else {
                    this.httpMethod.setQueryString(q + "&" + name + "=" + value);
                }
            } else if(this.httpMethod instanceof PostMethod) {
                ((PostMethod)this.httpMethod).addParameter(name, String.valueOf(value));
            }
        } else {
            throw new IllegalArgumentException("Arguments to addParameter(String, String) cannot be null");
        }
    }

    public void setPostRequestBody(String data) throws UnsupportedEncodingException {
        if(this.httpMethod instanceof PostMethod) {
            PostMethod httpMethod = (PostMethod) this.httpMethod;
            RequestEntity entity = new StringRequestEntity(data, "text/plain","UTF-8");
            httpMethod.setRequestEntity(entity);
        }
    }

    public int send() throws IOException {
        this.httpMethod.setRequestHeader("Connection", "close");
        return this.client.executeMethod(this.httpMethod);
    }

    public Map<String, String> getResponseHeader() {
        Map<String, String> r = new HashMap();
        Header[] h = this.httpMethod.getResponseHeaders();
        Header[] var3 = h;
        int var4 = h.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            Header header = var3[var5];
            r.put(header.getName(), header.getValue());
        }

        return r;
    }

    public Map<String, String> getCookies() {
        Map<String, String> r = new HashMap();
        Cookie[] cs = this.client.getState().getCookies();
        Cookie[] var3 = cs;
        int var4 = cs.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            Cookie c = var3[var5];
            r.put(c.getName(), c.getValue());
        }

        return r;
    }

    public InputStream getResponseBodyAsStream() throws IOException {
        return this.httpMethod.getResponseBodyAsStream();
    }

    public String getResponseBodyAsString(String contentCharset) throws IOException {
        InputStream instream = this.httpMethod.getResponseBodyAsStream();
        ByteArrayOutputStream outstream = new ByteArrayOutputStream(4096);
        byte[] buffer = new byte[4096];

        int len;
        while((len = instream.read(buffer)) > 0) {
            outstream.write(buffer, 0, len);
        }

        outstream.close();
        byte[] rawdata = outstream.toByteArray();
        return contentCharset != null?new String(rawdata, contentCharset):new String(rawdata);
    }

    public void close() {
        if(this.httpMethod != null) {
            try {
                this.httpMethod.releaseConnection();
            } catch (Exception var2) {
                ;
            }
        }

    }

    private static void setProxy(HttpClient client, String proxyHost, int proxyPort ) {
        if(StringUtils.isNotBlank(proxyHost)) {
            if(proxyPort > 0) {
                client.getHostConfiguration().setProxy(proxyHost, proxyPort);
            }
        }

    }

    public static String getContent(String url, String proxyHost, int proxyPort ) {
        if(StringUtils.isNotBlank(url)) {
            HttpClient client = new HttpClient();
            GetMethod get = new GetMethod(url);
            setProxy(client,proxyHost,proxyPort);
            get.setRequestHeader("Connection", "close");
            String var3;
            try {
                client.executeMethod(get);
                var3 = get.getResponseBodyAsString();
            } catch (Exception var7) {
                log.error("", var7);
                return null;
            } finally {
                get.releaseConnection();
            }

            return var3;
        } else {
            return null;
        }
    }

    public static String getContent(String url) {
        if(StringUtils.isNotBlank(url)) {
            HttpClient client = new HttpClient();
            GetMethod get = new GetMethod(url);
            get.setRequestHeader("Connection", "close");
            String var3;
            try {
                client.executeMethod(get);
                var3 = get.getResponseBodyAsString();
            } catch (Exception var7) {
                log.error("", var7);
                return null;
            } finally {
                get.releaseConnection();
            }
            return var3;
        } else {
            return null;
        }
    }

    public static Header[] getContentHeader(String url) {
        if(StringUtils.isNotBlank(url)) {
            HttpClient client = new HttpClient();
            GetMethod get = new GetMethod(url);
            get.setRequestHeader("Connection", "close");
            Header[] var3;
            try {
                client.executeMethod(get);
                var3 = get.getResponseHeaders();
            } catch (Exception var7) {
                log.error("", var7);
                return null;
            } finally {
                get.releaseConnection();
            }
            return var3;
        } else {
            return null;
        }
    }

}