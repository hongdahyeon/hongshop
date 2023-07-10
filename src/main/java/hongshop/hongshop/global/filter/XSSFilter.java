package hongshop.hongshop.global.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@Component
@Slf4j
public class XSSFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        ContentCachingRequestWrapper req = new ContentCachingRequestWrapper((HttpServletRequest) request);
        ContentCachingResponseWrapper res = new ContentCachingResponseWrapper((HttpServletResponse) response);

        // 전처리

        chain.doFilter(new XSSWrapper(req), res);
        
        // 후처리
        String url = req.getRequestURI();
        String reqContent = new String(req.getContentAsByteArray());

        log.info("req url : {}, body : {}", url, reqContent);

        String resContent = new String(res.getContentAsByteArray());
        int httpStataus = res.getStatus();

        // 사용자가 정상적으로 응답을 받도록..
        res.copyBodyToResponse();

        log.info("res status : {}, body : {}", httpStataus, resContent);
    }
}
