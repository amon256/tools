/**
 * DateXmlMetaType.java
 * @author FengMy
 * @since 2014年10月15日
 */
package tools.xml.meta.metatype;

import java.text.SimpleDateFormat;
import java.util.Date;

import tools.xml.meta.XmlMetaType;

/**  
 * 功能描述：
 * 
 * @author FengMy
 * @since 2014年10月15日
 */
public class DateXmlMetaType extends XmlMetaType<Date> {
	private String pattern;
	
	public DateXmlMetaType(String pattern){
		this.pattern = pattern;
	}
	
	public DateXmlMetaType(String pattern,String fieldName){
		super(fieldName);
		this.pattern = pattern;
	}

	@Override
	public Date parseValue(String content) throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.parse(content);
	}

}
