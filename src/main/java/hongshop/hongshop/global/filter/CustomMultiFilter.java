package hongshop.hongshop.global.filter;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.support.MultipartFilter;

import javax.servlet.annotation.WebFilter;

@WebFilter("/*")
@Component
@Order(1)
public class CustomMultiFilter extends MultipartFilter {
}
