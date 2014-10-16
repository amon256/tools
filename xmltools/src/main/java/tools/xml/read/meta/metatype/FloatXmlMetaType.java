/**
 * FloatXmlMetaType.java
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
public class FloatXmlMetaType extends XmlMetaType<Float> {
	public FloatXmlMetaType(){
	}
	
	public FloatXmlMetaType(String fieldName){
		super(fieldName);
	}
	@Override
	public Float parseValue(String content) {
		if(content == null || "".equals(content.trim())){
			return 0F;
		}
		return Float.parseFloat(content);
	}

}
