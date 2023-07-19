package hongshop.hongshop.global.filter;

import hongshop.hongshop.global.util.XSSUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@WebFilter("/*")
@Component
@Order(2)
@Slf4j
public class XSSFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        ContentCachingResponseWrapper wrappingResponse = new ContentCachingResponseWrapper(response);

        String method = request.getMethod();
        String contentType = request.getContentType();

        if(method.equals(HttpMethod.DELETE.name()) || method.equals(HttpMethod.GET.name())) doFilterParam(request, wrappingResponse, chain);
        else if(method.equals(HttpMethod.POST.name()) || method.equals(HttpMethod.PUT.name())) doFilterBody(request, wrappingResponse, chain);

    }


    public void doFilterParam(HttpServletRequest request, ContentCachingResponseWrapper response, FilterChain chain) throws IOException, ServletException {
        final Map<String, String[]> modifiedParameterMap = getModifiedXSSParameterMap(request);

        HttpServletRequestWrapper modifiedRequest = new HttpServletRequestWrapper(request) {
            @Override
            public String getHeader(String name) {
                return XSSUtil.charEscape(super.getHeader(name));
            }
            @Override
            public Map<String, String[]> getParameterMap() {
                return modifiedParameterMap;
            }
            @Override
            public String[] getParameterValues(String name) {
                return modifiedParameterMap.get(name);
            }
            @Override
            public String getParameter(String name) {
                String[] values = modifiedParameterMap.get(name);
                if (values != null && values.length > 0) {
                    return values[0];
                }
                return null;
            }
            @Override
            public Enumeration<String> getParameterNames() {
                return Collections.enumeration(modifiedParameterMap.keySet());
            }
        };

        chain.doFilter(modifiedRequest, response);
        response.copyBodyToResponse();
    }
    public void doFilterBody(HttpServletRequest request, ContentCachingResponseWrapper response, FilterChain chain) throws IOException, ServletException {
        XSSWrapper wrapper = new XSSWrapper(request);
        String body = IOUtils.toString(wrapper.getInputStream(), Charset.defaultCharset());

        if(!StringUtils.isBlank(body)){
            log.info("body is not blank : {}", body);
            String filteredBody = XSSUtil.charEscape(body);
            wrapper.resetInputStream(filteredBody.getBytes());
        }

        chain.doFilter(wrapper, response);
        response.copyBodyToResponse();
    }

    private Map<String, String[]> getModifiedXSSParameterMap(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, String[]> modifiedParameterMap = new HashMap<>(parameterMap);
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            String[] values = entry.getValue();
            for (int i = 0; i < values.length; i++) {
                values[i] = XSSUtil.charEscape(values[i]);
            }
            modifiedParameterMap.put(entry.getKey(), values);
        }
        return modifiedParameterMap;
    }

}
