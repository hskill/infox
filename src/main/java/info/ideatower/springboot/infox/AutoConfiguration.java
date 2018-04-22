package info.ideatower.springboot.infox;

import info.ideatower.springboot.infox.web.InjectDataInterceptor;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.Resource;

@Configuration
public class AutoConfiguration extends WebMvcConfigurerAdapter {

    @Resource
    public Environment env;

    public void addInterceptors(InterceptorRegistry registry) {
        val mark = env.getProperty("infox.mark", StringUtils.EMPTY);
        val path = env.getProperty("infox.path", StringUtils.EMPTY);

        registry.addInterceptor(new InjectDataInterceptor(mark, path));
    }

}
