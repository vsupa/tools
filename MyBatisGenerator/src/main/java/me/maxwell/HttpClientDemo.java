package me.maxwell;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * @author Maxwell.Lee
 * @date 2018-01-23 16:58
 * @since
 */
public class HttpClientDemo {


    public static void main(String[] args) {
        try {
            URL url = new URL("https://cli.im");
//            URL url = new URL("https://www.zhihu.com");
            HttpURLConnection       connection = (HttpURLConnection)url.openConnection();

            connection.connect();

            BufferedInputStream bis = new BufferedInputStream(connection.getInputStream(), 100 * 1024);
            Reader reader = new InputStreamReader(bis, Charset.forName("UTF-8"));
            char[] buf = new char[1024 * 100];

            FileOutputStream fos = new FileOutputStream("D:\\var\\tmp\\zhihu.html");
            OutputStreamWriter osw = new OutputStreamWriter(fos, Charset.forName("UTF-8"));

            int rc;
            while (true) {
                rc = reader.read(buf);
                if (rc <= 0) break;

                osw.write(buf, 0, rc);
                System.out.println(String.format("====Write %d chars.====", rc));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
