package co.yiiu.pymp.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class JSONUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static <T> List<T> parseList(String json, Class<T> clazz) {
        try {
            TypeFactory typeFactory = objectMapper.getTypeFactory();
            return objectMapper.readValue(json, typeFactory.constructCollectionType(List.class, clazz));
        } catch (JsonProcessingException e) {
//            e.printStackTrace();
            log.error("json to list error: {}", e.getMessage());
            return null;
        }
    }

    public static <T> T parseObject(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
//            e.printStackTrace();
            log.error("json to object error: {}", e.getMessage());
            return null;
        }
    }

    public static String toJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
//            e.printStackTrace();
            log.error("object to json string error: {}", e.getMessage());
            return null;
        }
    }
}
