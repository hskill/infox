package info.ideatower.springboot.infox.web;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.yaml.snakeyaml.Yaml;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.util.Map;

@Slf4j
public class InjectDataInterceptor extends HandlerInterceptorAdapter {

    protected static final String DEFAULT_MARK = "infox";

    protected static final String[] EXTENSIONTS = new String[] {"yml", "yaml"};

    /**
     * 数据文件目录路径
     */
    private final String path;

    /**
     * 数据在页面渲染时使用的标记
     */
    private final String mark;

    /**
     * 数据
     */
    protected final Map<String, Object> infox;

    /**
     * 初始化数据
     */
    private void init() {
        try {
            val infoxPath
                    = new File(InjectDataInterceptor.class.getClassLoader().getResource(this.path).toURI());
            val yaml = new Yaml();
            for (val file : FileUtils.listFiles(infoxPath, EXTENSIONTS, true)) {
                val item = yaml.loadAs(new FileInputStream(file), Map.class);
                val itemName = this.handleItemName(file.getName());
                infox.put(itemName, item);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * 处理文件名称
     * @param originalName 原始名称
     * @return 移除后辍名的名称
     */
    private String handleItemName(String originalName) {
        val selected = originalName.endsWith(".yaml") ? ".yaml" : ".yml";
        return originalName.substring(0, originalName.length() - selected.length());
    }

    public InjectDataInterceptor(final String mark, final String path) {
        this.mark = StringUtils.isBlank(mark) ? DEFAULT_MARK : mark;
        this.path = StringUtils.isBlank(path) ? DEFAULT_MARK : path;
        this.infox = Maps.newConcurrentMap();

        this.init();
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (!infox.isEmpty() && modelAndView.hasView()) {
            modelAndView.addObject(this.mark, this.infox);
        }
    }
}
