/**
 * DoubleXmlMetaType.java
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
public class DoubleXmlMetaType extends XmlMetaType<Double> {
	public DoubleXmlMetaType(){
	}
	
	public DoubleXmlMetaType(String fieldName){
		super(fieldName);
	}
	@Override
	public Double parseValue(String content) {
		if(content == null || "".equals(content.trim())){
			return 0D;
		}
		return Double.parseDouble(content);
	}

}
