package info.ideatower.springboot.infox.web.json;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.val;

public class JsonParser {

    public static String ok(Object target) {
        val jsonObj = new JsonObject();
        jsonObj.setData(target);
        return JSON.toJSONString(jsonObj);
    }

    public static String error(String code, String message) {
        val jsonObj = new JsonObject();
        jsonObj.setCode(code);
        jsonObj.setMessage(message);
        return JSON.toJSONString(jsonObj);
    }

    @Data
    protected static class JsonObject {
        /** 返回编码 */
        private String code = "000000";
        /** 消息 */
        private String message = "okay";
        /** 数据 */
        private Object data = null;
    }
}
