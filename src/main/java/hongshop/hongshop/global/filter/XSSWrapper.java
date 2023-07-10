package hongshop.hongshop.global.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

@Slf4j
public class XSSWrapper extends HttpServletRequestWrapper {
    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request The request to wrap
     * @throws IllegalArgumentException if the request is null
     */
    public XSSWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] parameterValues = super.getParameterValues(name);
        log.info("getParameterValues - name : {}", name);
        for (String parameterValue : parameterValues) {
            if(parameterValue != null) log.info("parameter[] - value : {}", parameterValue);
        }
        return parameterValues;
    }
}
