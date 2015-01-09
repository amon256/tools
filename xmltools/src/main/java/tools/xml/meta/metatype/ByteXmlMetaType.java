/**
 * ByteXmlMetaType.java
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
public class ByteXmlMetaType extends XmlMetaType<Byte> {
	public ByteXmlMetaType(){
	}
	
	public ByteXmlMetaType(String fieldName){
		super(fieldName);
	}
	@Override
	public Byte parseValue(String content) throws Exception{
		if(content == null || "".equals(content.trim())){
			return 0;
		}
		return Byte.parseByte(content);
	}

}
