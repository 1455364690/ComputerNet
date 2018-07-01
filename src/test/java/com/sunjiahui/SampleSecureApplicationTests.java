package com.sunjiahui;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Basic integration tests for demo application.
 *
 * @author Dave Syer
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SampleSecureApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @Test
    public void testHome() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.TEXT_HTML));
        ResponseEntity<String> entity = this.restTemplate.exchange("/", HttpMethod.GET,
                new HttpEntity<Void>(headers), String.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(entity.getHeaders().getLocation().toString())
                .endsWith(this.port + "/login");
    }

    @Test
    public void testLoginPage() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.TEXT_HTML));
        ResponseEntity<String> entity = this.restTemplate.exchange("/login",
                HttpMethod.GET, new HttpEntity<Void>(headers), String.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(entity.getBody()).contains("_csrf");
    }

    @Test
    public void testLogin() {
        HttpHeaders headers = getHeaders();
        headers.setAccept(Arrays.asList(MediaType.TEXT_HTML));
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.set("ssoId", "test");
        form.set("password", "test");
        ResponseEntity<String> entity = this.restTemplate.exchange("/login",
                HttpMethod.POST, new HttpEntity<>(form, headers), String.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(entity.getHeaders().getLocation().toString())
                .endsWith(this.port + "/");
        assertThat(entity.getHeaders().get("Set-Cookie")).isNotNull();
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<String> page = this.restTemplate.getForEntity("/login",
                String.class);
        assertThat(page.getStatusCode()).isEqualTo(HttpStatus.OK);
        String cookie = page.getHeaders().getFirst("Set-Cookie");
        headers.set("Cookie", cookie);
        Pattern pattern = Pattern.compile("(?s).*name=\"_csrf\".*?value=\"([^\"]+).*");
        Matcher matcher = pattern.matcher(page.getBody());
        assertThat(matcher.matches()).as(page.getBody()).isTrue();
        headers.set("X-CSRF-TOKEN", matcher.group(1));
        return headers;
    }

    @Test
    public void testCss() {
        ResponseEntity<String> entity = this.restTemplate
                .getForEntity("/css/bootstrap.min.css", String.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(entity.getBody()).contains("body");
    }

    @Test
    public void testEncode() throws WriterException, IOException {
        String filePath = "/Users/wangzhiyue/tmp";
        String fileName = "zxing.png";
        String content = "https://github.com/zxing/zxing/tree/zxing-3.0.0/javase/src/main/java/com/google/zxing";
        int width = 200; // 图像宽度
        int height = 200; // 图像高度
        String format = "png";// 图像类型
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content,
                BarcodeFormat.QR_CODE, width, height, hints);// 生成矩阵
        Path path = FileSystems.getDefault().getPath(filePath, fileName);
        MatrixToImageWriter.writeToPath(bitMatrix, format, path);// 输出图像
        System.out.println("输出成功.");
    }
}
