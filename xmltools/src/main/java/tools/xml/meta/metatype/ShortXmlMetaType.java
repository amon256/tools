/**
 * ShortXmlMetaType.java
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
public class ShortXmlMetaType extends XmlMetaType<Short> {
	public ShortXmlMetaType(){
	}
	
	public ShortXmlMetaType(String fieldName){
		super(fieldName);
	}
	@Override
	public Short parseValue(String content) {
		if(content == null || "".equals(content.trim())){
			return 0;
		}
		return Short.parseShort(content);
	}

}
