/**
 * XmlMetaType.java
 * @author FengMy
 * @since 2014年10月15日
 */
package tools.xml.read.meta;

import java.util.HashMap;
import java.util.Map;


/**  
 * 功能描述：xml转换元数据类型
 * 
 * @author FengMy
 * @since 2014年10月15日
 */
public abstract class XmlMetaType<T> {

	/**
	 * 上级xml转换元数据类型
	 */
	private XmlMetaType<?> parentXmlMetaType;
	
	/**
	 * 下级xml元数据类型(当前类型的成员变量元数据类型)
	 */
	private Map<String,XmlMetaType<?>> childXmlMetaTypes = new HashMap<String, XmlMetaType<?>>();
	
	/**
	 * 相应属性名,如果为空则取childXmlMetaTypes中对应的key
	 */
	private String fieldName;
	
	public XmlMetaType(){
		
	}
	
	public XmlMetaType(String fieldName){
		this.fieldName = fieldName;
	}
	
	/**
	 * 格式化为字符串格式化为相应的值
	 * @param content
	 * @return
	 */
	public abstract T parseValue(String content)throws Exception;

	public XmlMetaType<?> getParentXmlMetaType() {
		return parentXmlMetaType;
	}

	public void setParentXmlMetaType(XmlMetaType<?> parentXmlMetaType) {
		this.parentXmlMetaType = parentXmlMetaType;
	}

	public Map<String,XmlMetaType<?>> getChildXmlMetaTypes() {
		return childXmlMetaTypes;
	}

	public void addChildXmlMetaTypes(String fieldName,XmlMetaType<?> childXmlMetaType) {
		if(fieldName == null || "".equals(fieldName.trim())){
			throw new IllegalArgumentException("fieldName is empty");
		}
		if(childXmlMetaType == null){
			this.childXmlMetaTypes.remove(fieldName);
		}else{
			childXmlMetaType.setParentXmlMetaType(this);
			this.childXmlMetaTypes.put(fieldName, childXmlMetaType);
		}
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
}
