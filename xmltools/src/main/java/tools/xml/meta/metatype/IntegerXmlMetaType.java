/**
 * IntegerXmlMetaType.java
 * @author FengMy
 * @since 2014年10月15日
 */
package tools.xml.meta.metatype;

import tools.xml.meta.XmlMetaType;

/**  
 * 功能描述：整型
 * 
 * @author FengMy
 * @since 2014年10月15日
 */
public class IntegerXmlMetaType extends XmlMetaType<Integer> {
	public IntegerXmlMetaType(){
	}
	
	public IntegerXmlMetaType(String fieldName){
		super(fieldName);
	}
	@Override
	public Integer parseValue(String content) {
		if(content == null || "".equals(content.trim())){
			return 0;
		}
		return Integer.parseInt(content);
	}

}
