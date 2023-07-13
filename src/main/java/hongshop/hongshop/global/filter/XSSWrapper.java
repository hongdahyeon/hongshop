package hongshop.hongshop.global.filter;

import hongshop.hongshop.global.util.XSSUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;

@Slf4j
public class XSSWrapper extends ContentCachingRequestWrapper {

    private byte[] rawData;
    private HttpServletRequest request;
    private ResettableServletInputStream servletInputStream;


    public XSSWrapper(HttpServletRequest request) {
        super(request);
        this.request = request;

        this.servletInputStream = new ResettableServletInputStream();

    }

    public void resetInputStream(byte[] data) {
        servletInputStream.stream = new ByteArrayInputStream(data);
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        if (rawData == null) {
            rawData = IOUtils.toByteArray(this.request.getInputStream());
            servletInputStream.stream = new ByteArrayInputStream(rawData);
        }
        return servletInputStream;
    }

    @Override
    public String getHeader(String name) {
        return XSSUtil.charEscape(super.getHeader(name));
    }

    /*
     *  > ServletInputStream
     *  - 클라이언트 요청에서 바이너리 데이터를 읽기 위한 입력 스트림을 제공하는 Java Servlet API 추상 클래스
     *  -> servlet에서 들어오는 HTTP 요청을 처리하는데 사용된다.
     */
    private class ResettableServletInputStream extends ServletInputStream {

        private InputStream stream;

        /*
         *   입력 스트림이 끝에 도달했는지 여부를 나타낸다.
         * - inputStream.available() 메서드를 사용해 stream에서 사용 가능한 데이터가 있는지 확인한다.
         * -> 만일 사용 가능한 데이터의 크기가 0이라면 stream이 완료됐음을 의미한다.
         * */
        @Override
        public boolean isFinished() {
            try {
                return stream.available() == 0;
            } catch (IOException e) {
                return true;
            }
        }

        /* 입력 스트림을 읽을 준비가 됐는지 여부를 결정한다. */
        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener listener) {

        }

        /* 입력 스트림에서 다음 바이트의 데이터를 읽는다. */
        @Override
        public int read() throws IOException {
            return stream.read();
        }
    }
}