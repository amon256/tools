/**
 * LongXmlMetaType.java
 * @author FengMy
 * @since 2014年10月15日
 */
package tools.xml.meta.metatype;

import tools.xml.meta.XmlMetaType;

/**  
 * 功能描述：
 * 
 * @author FengMy
 * @since 2014年10月15日
 */
public class LongXmlMetaType extends XmlMetaType<Long> {
	public LongXmlMetaType(){
	}
	
	public LongXmlMetaType(String fieldName){
		super(fieldName);
	}
	@Override
	public Long parseValue(String content) {
		if(content == null || "".equals(content.trim())){
			return 0L;
		}
		return Long.parseLong(content);
	}

}
