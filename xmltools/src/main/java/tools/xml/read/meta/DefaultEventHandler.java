/**
 * DefaultEventHandler.java
 * @author FengMy
 * @since 2014年10月15日
 */
package tools.xml.read.meta;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

import tools.xml.read.RootObjectAccessor;
import tools.xml.read.agent.XmlNodeHandler;

/**  
 * 功能描述：xml节点事件默认实现
 * 
 * @author FengMy
 * @since 2014年10月15日
 */
@SuppressWarnings("rawtypes")
public class DefaultEventHandler<T> implements XmlNodeHandler {
	private XmlMetaType<T> rootXmlMetaType;
	private String rootPath;
	private Map<String,XmlMetaType<?>> pathMeta = new HashMap<String, XmlMetaType<?>>();
	private LinkedList<Object> valueStack = new LinkedList<Object>();
	private RootObjectAccessor objectAccessor;
	public DefaultEventHandler(XmlMetaType<T> xmlMetaType,String rootPath,RootObjectAccessor objectAccessor){
		this.rootXmlMetaType = xmlMetaType;
		this.rootPath = rootPath;
		this.objectAccessor = objectAccessor;
		initFromMetaType();
	}
	
	private void initFromMetaType(){
		if(rootPath == null){
			rootPath = "";
		}
		addMetaTypeToPathMeta(rootXmlMetaType,rootPath);
	}

	private void addMetaTypeToPathMeta(XmlMetaType<?> metaType,String path){
		if(metaType != null){
			if(pathMeta.containsValue(metaType)){
				throw new RuntimeException("xmlMetaType is circle");
			}
			pathMeta.put(path, metaType);
			if(metaType.getChildXmlMetaTypes() != null && !metaType.getChildXmlMetaTypes().isEmpty()){
				Iterator<Entry<String, XmlMetaType<?>>> iterator = metaType.getChildXmlMetaTypes().entrySet().iterator();
				while(iterator.hasNext()){
					Entry<String, XmlMetaType<?>> entry = iterator.next();
					addMetaTypeToPathMeta(entry.getValue(), path + "/" + entry.getKey());
				}
			}
		}
	}
	@Override
	public void accept(String nodePath,String content,XmlNodeType nodeType) throws Exception{
		if(!pathMeta.containsKey(nodePath)){
			return;
		}
		if(nodeType == XmlNodeType.START){
			handStartNodeEvent(nodePath);
		}
		if(nodeType == XmlNodeType.END){
			handEndNodeEvent(nodePath,content);
		}
	}
	
	private void handStartNodeEvent(String nodePath)throws Exception{
		XmlMetaType<?> metaType = pathMeta.get(nodePath);
		if(!isLeaf(metaType)){
			//非叶子节点，则直接构造值
			Object v = metaType.parseValue(null);
			valueStack.add(v);
		}
		//叶子节点由end事件处理时构造实体
	}
	
	@SuppressWarnings({ "unchecked"})
	private void handEndNodeEvent(String nodePath,String content)throws Exception{
		XmlMetaType<?> metaType = pathMeta.get(nodePath);
		Object v;
		if(isLeaf(metaType)){
			v = metaType.parseValue(content);
		}else{
			v = valueStack.pollLast();
		}
		if(nodePath.equals(rootPath) && objectAccessor != null){
			objectAccessor.access(v);
		}else{
			assemObject(metaType, v);
		}
	}
	
	@SuppressWarnings("unchecked")
	private void assemObject(XmlMetaType<?> metaType,Object v){
		//将此节点对象赋为上级节点对象属性
		Object parent = valueStack.peekLast();
		if(parent != null){
			XmlMetaType<?> parentMetaType = metaType.getParentXmlMetaType();
			if(parentMetaType != null){
				Class<?> clazz = parent.getClass();
				String fieldName = null;
				Iterator<Entry<String, XmlMetaType<?>>> iterator = parentMetaType.getChildXmlMetaTypes().entrySet().iterator();
				Entry<String, XmlMetaType<?>> entry;
				while(iterator.hasNext()){
					entry = iterator.next();
					if(entry.getValue() == metaType){
						fieldName = (metaType.getFieldName()==null || "".equals(metaType.getFieldName().trim()))?entry.getKey() : metaType.getFieldName();
						break;
					}
				}
				if(fieldName != null){
					if(Collection.class.isAssignableFrom(clazz)){
						((Collection)parent).add(v);
					}else if(Map.class.isAssignableFrom(clazz)){
						((Map)parent).put(fieldName, v);
					}else if(clazz.isArray()){
						int len = Array.getLength(parent);
						Object newParent = Array.newInstance(parent.getClass(), len + 1);
						for(int i = 0; i < len; i++){
							Array.set(newParent, i, Array.get(parent, i));
						}
						Array.set(newParent, len, v);
					}else{
						try{
							Method setter = null;
							Method[] methods = clazz.getDeclaredMethods();
							String setterName = "set" + (fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1));
							for(Method method : methods){
								if(method.getName().equals(setterName)){
									setter = method;
									break;
								}
							}
							if(setter != null){
								setter.invoke(parent, v);
							}else{
								Field[] fields = clazz.getDeclaredFields();
								for(Field f : fields){
									if(f.getName().equals(fieldName) && f.getDeclaringClass().isAssignableFrom(v.getClass())){
										f.setAccessible(true);
										f.set(parent, v);
										break;
									}
								}
							}
						}catch(Exception e){
							throw new RuntimeException(e);
						}
					}
				}
			}
		}
	}
	
	protected boolean isLeaf(XmlMetaType<?> metaType){
		return metaType.getChildXmlMetaTypes() == null || metaType.getChildXmlMetaTypes().isEmpty();
	}
}
