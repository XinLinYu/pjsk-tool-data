package com.projectsekai.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * @ClassName FileUploadUtil
 * @description
 * @Author XinlindeYu
 * @Date 2020/11/10 0010 下午 3:36
 * @Version 1.0
 **/
public class FileUploadUtil {
    public JSONObject uploadByUrl(String urlString) {
        URL url = null;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //设置超时间为3秒
        conn.setConnectTimeout(3 * 1000);
        //得到输入流
        InputStream inputStream = null;
        try {
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                inputStream = conn.getInputStream();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String file = uploadByHttp(inputStream, urlString.substring(urlString.lastIndexOf("/") + 1));
        return JSON.parseObject(file);
    }

    public String uploadByHttp(InputStream inputStream, String fileName) {
        final String remote_url = "https://www.zxinlindeyu.com/file-server/upload/upload.do";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String result = "";
        try {
            HttpPost httpPost = new HttpPost(remote_url);
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addBinaryBody("file", inputStream, ContentType.MULTIPART_FORM_DATA, fileName);
            HttpEntity entity = builder.build();
            httpPost.setEntity(entity);
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                // 将响应内容转换为字符串
                result = EntityUtils.toString(responseEntity, Charset.forName("UTF-8"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
