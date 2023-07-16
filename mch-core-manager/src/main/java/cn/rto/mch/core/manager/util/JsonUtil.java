package cn.rto.mch.core.manager.util;

import cn.rto.mch.core.manager.common.SystemException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/**
 * ClassName: JsonUtil
 * Description: TODO
 * Author: guanjieye
 * Date: 2023/07/17
 */
public class JsonUtil {
    private static ObjectMapper mapper = new ObjectMapper();

    private JsonUtil() {
    }

    public static String toJSONString(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException var2) {
            throw new SystemException("系统内部错误", var2);
        }
    }

    public static <T> T parseObject(String text, Class<T> clazz) {
        try {
            return mapper.readValue(text, clazz);
        } catch (IOException var3) {
            throw new SystemException("系统内部错误", var3);
        }
    }

    public static <T> List<T> parseList(String text, Class<T> clazz) {
        try {
            CollectionType type = mapper.getTypeFactory().constructCollectionType(List.class, clazz);
            return (List)mapper.readValue(text, type);
        } catch (IOException var3) {
            throw new SystemException("系统内部错误", var3);
        }
    }

    public static List<Map<String, Object>> parseListMap(String text) {
        try {
            return (List)mapper.readValue(text, new TypeReference<List<Map<String, Object>>>() {
            });
        } catch (IOException var2) {
            throw new SystemException("系统内部错误", var2);
        }
    }

    public static Map<String, Object> parseMap(String text) {
        try {
            return (Map)mapper.readValue(text, new TypeReference<Map<String, Object>>() {
            });
        } catch (IOException var2) {
            throw new SystemException("系统内部错误", var2);
        }
    }

    public static JsonNode parseJsonNode(String text) {
        try {
            return mapper.readTree(text);
        } catch (IOException var2) {
            throw new SystemException("系统内部错误", var2);
        }
    }

    public static <T> T parseObject(String text, Class<T> clazz, Class innerGeneric) {
        try {
            JavaType javaType = mapper.getTypeFactory().constructParametricType(clazz, new Class[]{innerGeneric});
            return mapper.readValue(text, javaType);
        } catch (IOException var4) {
            throw new SystemException("系统内部错误", var4);
        }
    }

    static {
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern("HH:mm:ss")));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern("HH:mm:ss")));
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL).
                setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).
                configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).
                disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).
                disable(new JsonGenerator.Feature[]{JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN}).
                setSerializerFactory(mapper.getSerializerFactory().withSerializerModifier(new SensitiveFieldSerializerModifier()));
        mapper.registerModule(javaTimeModule).registerModule(new Jdk8Module());
    }
}
