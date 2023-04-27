package cn.iwenjuan.rest.utils;

import org.apache.tomcat.util.http.fileupload.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author li1244
 * @date 2023/4/27 10:51
 */
public class IoUtils extends IOUtils {

    /**
     * 从输入流读取byte数组
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static byte[] toBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        copy(inputStream, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
}
