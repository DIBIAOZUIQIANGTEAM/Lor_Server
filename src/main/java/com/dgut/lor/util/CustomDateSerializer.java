package com.dgut.lor.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;


/**
 *  java日期对象经过Jackson库转换成JSON日期格式化自定义类
 * @author yuruli
 * @date 2010-7-25
 */
@SuppressWarnings("rawtypes")
public class CustomDateSerializer extends JsonSerializer {

      

		@Override
		public void serialize(Object value, JsonGenerator gen,
				SerializerProvider serializers) throws IOException,
				JsonProcessingException {
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                String formattedDate = formatter.format(value);
                System.out.println("formattedDate"+formattedDate);
                gen.writeString(formattedDate);
                
			
		}
}
