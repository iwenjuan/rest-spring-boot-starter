package cn.iwenjuan.sample;

import cn.iwenjuan.rest.UploadFile;
import cn.iwenjuan.rest.client.HttpClientBuilder;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author li1244
 * @date 2023/4/26 15:51
 */
@SpringBootTest
public class HttpClientTest {

    @Test
    public void run() {
        String url = "http://127.0.0.1:8080/test";

        String name = "张三";
        Map<String, String[]> params = new HashMap<>(16);
        params.put("name", new String[]{name, "李四"});
        params.put("date", new String[]{"2023-04-27 10:00:00"});

        // 测试GET请求
        String getResult = HttpClientBuilder.create(url).params(params).build().doGet();
        System.out.println("get请求：" + getResult);

        // 测试POST请求
        String postResult = HttpClientBuilder.create(url).body(params).build().doPost();
        System.out.println("post请求：" + postResult);

        // 测试PUT请求
        String putResult = HttpClientBuilder.create(url).body(params).build().doPut();
        System.out.println("put请求：" + putResult);

        // 测试DELETE请求
        String deleteResult = HttpClientBuilder.create(url).body(params).build().doDelete();
        System.out.println("delete请求：" + deleteResult);
    }

    @Test
    public void download() throws IOException {
        // 测试文件下载
        String fileUrl = "https://img0.baidu.com/it/u=741268616,1401664941&fm=253&fmt=auto&app=138&f=JPEG?w=748&h=500";
        byte[] bytes = HttpClientBuilder.create(fileUrl).build().doGet(byte[].class);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        FileOutputStream outputStream = new FileOutputStream("C://tmp/968e0ad51b9b2130f6373e4b5fc14052.jpeg");
        IOUtils.copy(inputStream, outputStream);
    }

    @Test
    public void upload() throws IOException {
        // 测试文件上传
        String url = "http://127.0.0.1:8080/storage/upload";

        File file = new File("C://tmp/968e0ad51b9b2130f6373e4b5fc14052.jpeg");
        MultiValueMap<String, UploadFile> fileMap = new LinkedMultiValueMap<>();
        fileMap.add("file", new UploadFile(file));
        String upload = HttpClientBuilder.create(url).fileMap(fileMap).build().doUpload();
        System.out.println("文件上传：" + upload);
    }

}
