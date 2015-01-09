/**
 * BigDecimalXmlMetaType.java
 * @author FengMy
 * @since 2014年10月15日
 */
package tools.xml.meta.metatype;

import java.math.BigDecimal;

import tools.xml.meta.XmlMetaType;

/**  
 * 功能描述：
 * 
 * @author FengMy
 * @since 2014年10月15日
 */
public class BigDecimalXmlMetaType extends XmlMetaType<BigDecimal> {
	public BigDecimalXmlMetaType(){
	}
	
	public BigDecimalXmlMetaType(String fieldName){
		super(fieldName);
	}
	@Override
	public BigDecimal parseValue(String content) throws Exception{
		if(content == null || "".equals(content.trim())){
			return null;
		}
		return new BigDecimal(content.trim());
	}

}
