package xg.demo.test.Untils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpClientUntil {

    private HttpClientUntil() {
    }

    public static String SendGetRequest(String url) throws Exception{
        //1.创建HttpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //2.使用get请求，创建HttpGet对象
        HttpGet httpGet = new HttpGet(url);
        //3.发起远程调用
        CloseableHttpResponse closeableHttpResponse = httpClient.execute(httpGet);
        //4.获取响应的数据
        HttpEntity entity = closeableHttpResponse.getEntity();
        //利用EntityUtils工具类把响应的数据转换成string类型的
        String s = EntityUtils.toString(entity);
        //关闭HttpClient对象
        httpClient.close();

        /*如何设置请求头
        HttpUriRequest request = RequestBuilder.get().addHeader("token", "123").build();
        HttpResponse response = httpClient.execute(request);*/
        return s;
    }

    public static String SendPostRequest(String url) throws Exception{
        //1.创建HttpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //2.使用post请求，创建HttpPost对象
        HttpPost httpPost = new HttpPost(url);
        //3.发起远程调用
        CloseableHttpResponse closeableHttpResponse = httpClient.execute(httpPost);
        //4.获取响应的数据
        HttpEntity entity = closeableHttpResponse.getEntity();
        //利用EntityUtils工具类把响应的数据转换成string类型的
        String s = EntityUtils.toString(entity);
        //关闭HttpClient对象
        httpClient.close();
        return s;
    }

}
