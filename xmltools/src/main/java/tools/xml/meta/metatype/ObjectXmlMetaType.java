/**
 * CollectionXmlMetaType.java
 * @author FengMy
 * @since 2014年10月15日
 */
package tools.xml.meta.metatype;

import tools.xml.meta.XmlMetaType;

/**  
 * 功能描述：非原生数据类型和String类型使用，T必须提供无参构造函数，否则要自行实现
 * 
 * @author FengMy
 * @since 2014年10月15日
 */
public class ObjectXmlMetaType<T> extends XmlMetaType<T> {
	
	private Class<T> classType;
	
	public ObjectXmlMetaType(Class<T> classType){
		if(classType == null){
			throw new IllegalArgumentException("argument is must subclass as Collection");
		}
		this.classType = classType;
	}
	
	public ObjectXmlMetaType(Class<T> classType,String fieldName){
		super(fieldName);
		if(classType == null){
			throw new IllegalArgumentException("argument is must subclass as Collection");
		}
		this.classType = classType;
	}

	@Override
	public T parseValue(String content) throws Exception{
		return classType.newInstance();
	}
	
	public Class<T> getClassType(){
		return classType;
	}
}
