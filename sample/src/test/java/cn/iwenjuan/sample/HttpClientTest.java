package cn.iwenjuan.sample;

import cn.iwenjuan.rest.client.HttpClient;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author li1244
 * @date 2023/4/26 15:51
 */
@SpringBootTest
public class HttpClientTest {

    @Test
    public void run() throws IOException {
        String url = "http://10.11.10.75:10000/gw4pc/open/health";
        String result = HttpClient.get(url);
        System.out.println(result);

        String fileUrl = "http://10.11.10.75:9000/isrmc/files/01GK339T4P9S57PP6948MFPRAY.png";
        byte[] bytes = HttpClient.get(fileUrl, byte[].class);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        FileOutputStream outputStream = new FileOutputStream("C://tmp/01GK339T4P9S57PP6948MFPRAY.png");
        IOUtils.copy(inputStream, outputStream);
    }
}
