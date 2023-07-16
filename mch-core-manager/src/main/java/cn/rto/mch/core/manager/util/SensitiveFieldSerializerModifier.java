package cn.rto.mch.core.manager.util;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * ClassName: SensitiveFieldSerializerModifier
 * Description: TODO
 * Author: guanjieye
 * Date: 2023/07/17
 */
public class SensitiveFieldSerializerModifier  extends BeanSerializerModifier {

    public SensitiveFieldSerializerModifier() {
    }

    public List<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc, List<BeanPropertyWriter> beanProperties) {
        if (!Objects.isNull(beanProperties) && !beanProperties.isEmpty()) {
            Iterator var4 = beanProperties.iterator();

            while(var4.hasNext()) {
                BeanPropertyWriter writer = (BeanPropertyWriter)var4.next();

            }

            return beanProperties;
        } else {
            return beanProperties;
        }
    }
}
