/**
 * StringXmlMetaType.java
 * @author FengMy
 * @since 2014年10月15日
 */
package tools.xml.read.meta.metatype;

import tools.xml.read.meta.XmlMetaType;

/**  
 * 功能描述：
 * 
 * @author FengMy
 * @since 2014年10月15日
 */
public class StringXmlMetaType extends XmlMetaType<String> {
	
	public StringXmlMetaType(){
	}
	
	public StringXmlMetaType(String fieldName){
		super(fieldName);
	}

	@Override
	public String parseValue(String content) {
		return content;
	}

}
