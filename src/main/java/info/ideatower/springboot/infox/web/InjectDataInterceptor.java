package info.ideatower.springboot.infox.web;

import info.ideatower.springboot.infox.Infox;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class InjectDataInterceptor extends HandlerInterceptorAdapter {

    private final String infoxMark;

    public InjectDataInterceptor(String infoxMark) {
        this.infoxMark = StringUtils.isBlank(infoxMark) ? Infox.DEFAULT_MARK : infoxMark;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 当条件满足时，才加入视图中
        if (modelAndView != null && modelAndView.hasView() && !Infox.getAll().isEmpty()) {
            modelAndView.addObject(this.infoxMark, Infox.getAll());
        }
    }
}
