/**
 * BooleanXmlMetaType.java
 * @author FengMy
 * @since 2014年10月16日
 */
package tools.xml.read.meta.metatype;

import tools.xml.read.meta.XmlMetaType;

/**  
 * 功能描述：
 * 
 * @author FengMy
 * @since 2014年10月16日
 */
public class BooleanXmlMetaType extends XmlMetaType<Boolean>{
	public BooleanXmlMetaType(){
	}
	
	public BooleanXmlMetaType(String fieldName){
		super(fieldName);
	}
	@Override
	public Boolean parseValue(String content) throws Exception{
		if(content == null || "".equals(content)){
			return false;
		}
		return Boolean.valueOf(content.trim());
	}

}
