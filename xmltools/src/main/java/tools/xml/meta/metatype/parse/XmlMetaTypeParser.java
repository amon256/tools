/**
 * XmlMetaTypeParser.java
 * @author FengMy
 * @since 2014年10月17日
 */
package tools.xml.meta.metatype.parse;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import tools.xml.meta.XmlMetaType;
import tools.xml.meta.metatype.BigDecimalXmlMetaType;
import tools.xml.meta.metatype.BooleanXmlMetaType;
import tools.xml.meta.metatype.ByteXmlMetaType;
import tools.xml.meta.metatype.DateXmlMetaType;
import tools.xml.meta.metatype.DoubleXmlMetaType;
import tools.xml.meta.metatype.FloatXmlMetaType;
import tools.xml.meta.metatype.IntegerXmlMetaType;
import tools.xml.meta.metatype.LongXmlMetaType;
import tools.xml.meta.metatype.ObjectXmlMetaType;
import tools.xml.meta.metatype.ShortXmlMetaType;
import tools.xml.meta.metatype.StringXmlMetaType;

/**  
 * 功能描述：
 * 
 * @author FengMy
 * @since 2014年10月17日
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class XmlMetaTypeParser<T> {
	
	private static final Map<String,Class<?>> METATYPES = new HashMap<String, Class<?>>();
	static{
		METATYPES.put("string", StringXmlMetaType.class);
		METATYPES.put("bigdecimal", BigDecimalXmlMetaType.class);
		METATYPES.put("boolean", BooleanXmlMetaType.class);
		METATYPES.put("byte", ByteXmlMetaType.class);
//		METATYPES.put("date", DateXmlMetaType.class);
		METATYPES.put("double", DoubleXmlMetaType.class);
		METATYPES.put("float", FloatXmlMetaType.class);
		METATYPES.put("integer", IntegerXmlMetaType.class);
		METATYPES.put("long", LongXmlMetaType.class);
		METATYPES.put("int", IntegerXmlMetaType.class);
		METATYPES.put("integer", IntegerXmlMetaType.class);
		METATYPES.put("short", ShortXmlMetaType.class);
//		METATYPES.put("object", ObjectXmlMetaType.class);
	}

	public XmlMetaType<T> parse(InputStream inputStream) throws SAXException, IOException, ParserConfigurationException{
		Document doc = readXmlConfig(inputStream);
		Element rootElement = doc.getDocumentElement();
		return createXmlMetaTypeByElement(rootElement, null);
	}
	
	private XmlMetaType createXmlMetaTypeByElement(Element ele,XmlMetaType parentMetaType){
		if(ele == null || !"node".equals(ele.getNodeName())){
			return null;
		}
		XmlMetaType metaType = createXmlMetaType(ele);
		NodeList nodeList = ele.getChildNodes();
		int len = nodeList.getLength();
		XmlMetaType childMetaType;
		Element childEle;
		for(int i = 0 ; i < len; i++){
			if(! (nodeList.item(i) instanceof Element)){
				continue;
			}
			childEle = (Element) nodeList.item(i);
			childMetaType = createXmlMetaTypeByElement(childEle, metaType);
			if(childMetaType != null){
				metaType.addChildXmlMetaTypes(childEle.getAttribute("nodeName"), childMetaType);
			}
		}
		return metaType;
	}
	
	private XmlMetaType createXmlMetaType(Element ele){
		String nodeName = ele.getAttribute("nodeName");
		String nodeType = ele.getAttribute("nodeType");
		String fieldName = ele.getAttribute("fieldName");
		if(nodeName == null || "".equals(nodeName.trim())){
			throw new RuntimeException("nodeName is required");
		}
		if(nodeType == null || "".equals(nodeType.trim())){
			throw new RuntimeException("nodeType is required");
		}
		String lowerNodeType = nodeType.trim().toLowerCase();
		Class<?> classType = null;
		Class<?>[] constructorParameterTypes = null;
		Object[] newInstanceParams = null;
		if(METATYPES.containsKey(lowerNodeType)){
			//基本数据类型，构造函数只需要传fieldName
			classType = METATYPES.get(lowerNodeType);
			constructorParameterTypes = new Class[]{String.class};
			newInstanceParams = new String[]{fieldName};
		}else if("object".equals(lowerNodeType)){
			classType = ObjectXmlMetaType.class;
			constructorParameterTypes = new Class[]{Class.class,String.class};
			String className = ele.getAttribute("classType");
			if(className == null || "".equals(className.trim())){
				throw new RuntimeException("case nodeType=object,it's classType must required.for nodeName:" + nodeName);
			}
			Class<?> c = null;
			try {
				c = Class.forName(className);
			} catch (ClassNotFoundException e) {
				throw new RuntimeException(e);
			}
			newInstanceParams = new Object[]{c,fieldName};
		}else if("date".equals(lowerNodeType)){
			classType = DateXmlMetaType.class;
			String pattern = ele.getAttribute("pattern");
			if(pattern == null || "".equals(pattern)){
				throw new RuntimeException("pattern is required for date node.for node:" + nodeName);
			}
			constructorParameterTypes = new Class[]{String.class,String.class};
			newInstanceParams = new String[]{pattern,fieldName};
		}else{
			try {
				classType = Class.forName(nodeType);
			} catch (ClassNotFoundException e) {
				throw new RuntimeException("unknow class:" + nodeType,e);
			}
			newInstanceParams = new String[]{fieldName};
		}
		try {
			Constructor<?> constructor = classType.getConstructor(constructorParameterTypes);
			XmlMetaType<?> xmlMetaType = (XmlMetaType) constructor.newInstance(newInstanceParams);
			xmlMetaType.setNodeName(nodeName);
			return xmlMetaType;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private Document readXmlConfig(InputStream inputStream) throws SAXException, IOException, ParserConfigurationException{
		Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputStream);
		return doc;
	}
}
