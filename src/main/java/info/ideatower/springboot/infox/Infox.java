package info.ideatower.springboot.infox;

import com.google.common.collect.Maps;
import info.ideatower.springboot.infox.web.InjectDataInterceptor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.io.FileUtils;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.util.Map;

/**
 * 可以直接从Infox类中使用加载的配置文件。主要用于SpringBoot程序加载静态数据。
 *
 * <code>
 *     Infox.load(); // 加载，仅在包内可用
 *
 *     Infox.getAll(); // 获取所有加载静态数据
 *
 *     Infox.get("global"); // 获取指定名称文件的静态数据
 * </code>
 */
@Slf4j
public class Infox {

    public static final String DEFAULT_MARK = "infox";

    public static final String[] EXTENSIONTS = new String[] {"yml", "yaml"};

    /**
     * 数据
     */
    private static final Map<String, Object> CONTAINER = Maps.newConcurrentMap();

    /**
     * 1. 会先在运行目录下寻找infox文件夹
     * 2. 其次会类路径下寻找infox文件夹
     */
    protected static void load() {
        File currentPath = null;
        File classPath = null;
        try {
            currentPath = new File(System.getProperty("user.dir") + File.separator + DEFAULT_MARK);
            classPath = new File(InjectDataInterceptor.class.getClassLoader().getResource(DEFAULT_MARK).toURI());
            File infoxPath = null;
            if (currentPath.exists() && currentPath.canRead()) {
                infoxPath = currentPath;
            }
            else if (classPath.exists() && classPath.canRead()) {
                infoxPath = classPath;
            }

            if (infoxPath != null) {
                val yaml = new Yaml();
                for (val file : FileUtils.listFiles(infoxPath, EXTENSIONTS, true)) {
                    val item = yaml.loadAs(new FileInputStream(file), Map.class);
                    val name = file.getName();
                    val itemName = name.substring(0, name.lastIndexOf('.'));
                    CONTAINER.put(itemName, item);
                }
            }
            else {
                log.debug("在指定的位置找不到infox: {}", currentPath);
                log.debug("在指定的位置找不到infox: {}", classPath);
            }
        }
        catch (Exception e) {
            log.warn("infox不能初始化: {}", e.getMessage());
        }
    }

    /**
     * 获取所有值
     * @return 所有加载值
     */
    public static Map<String, Object> getAll() {
        return CONTAINER;
    }

    /**
     * 获取指定名称的值
     * @param name 指定名片
     * @return 指定的加载值
     */
    public static Map<String, Object> get(String name) {
        return (Map<String, Object>) CONTAINER.get(name);
    }
}
