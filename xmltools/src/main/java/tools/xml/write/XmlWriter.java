/**
 * XmlWriter.java
 * @author FengMy
 * @since 2015年1月9日
 */
package tools.xml.write;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import test.xml.read.Smsp;
import tools.xml.meta.XmlMetaType;
import tools.xml.meta.metatype.DateXmlMetaType;
import tools.xml.meta.metatype.ObjectXmlMetaType;
import tools.xml.meta.metatype.parse.XmlMetaTypeParser;

/**  
 * 功能描述：xml输出
 * 
 * @author FengMy
 * @since 2015年1月9日
 */
public class XmlWriter{
	
	/**
	 * xml头
	 */
	private static final String XML_HEAD = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
	
	/**
	 * 换行符
	 */
	private static final String ENTER_LINE = "\n";
	/**
	 * 制表符
	 */
	private static final String TAB = "\t";
	/**
	 * 空白字符
	 */
	private static final String EMPTY = "";
	
	/**
	 * rootPath分隔符
	 */
	private static final String ROOT_PATH_SPLIT = "/";
	
	/**
	 * 左尖括号
	 */
	private static final String LEFT_BRACKETS = "<";
	/**
	 * 结束节点左尖括号
	 */
	private static final String LEFT_BRACKETS_END = "</";
	/**
	 * 右尖括号
	 */
	private static final String RIGHT_BRACKETS = ">";
	
	/**
	 * CDATA left
	 */
	private static final String CDATA_LEFT = "<![CDATA[";
	
	/**
	 * CDATA right
	 */
	private static final String CDATA_RIGHT = " ]]>";
	
	/**
	 * xml输出流
	 */
	private OutputStream outputStream;
	
	/**
	 * 缩进
	 */
	private String leftIndent = EMPTY;
	
	/**
	 * 是否格式化
	 */
	private boolean format = true;
	
	/**
	 * 临时变量取值递进
	 */
	private int varIndex = 0;
	
	public XmlWriter(OutputStream outputStream){
		if(outputStream == null){
			throw new IllegalArgumentException("outputstream is not allow null");
		}
		this.outputStream = outputStream;
	}
	
	public <T> void write(Object sourceData,XmlMetaType<T> xmlMetaType,String rootPath,boolean format){
		
	}
	
	public <T> String generateTemplate(XmlMetaType<T> xmlMetaType,String rootPath,boolean format){
		this.format = format;
		if(rootPath == null){
			rootPath = "";
		}
		rootPath = rootPath.trim();
		StringBuilder template = new StringBuilder(XML_HEAD);
		template.append(getEnterLine(format));
		String[] rootPaths = rootPath.split(ROOT_PATH_SPLIT);
		if(rootPaths != null && rootPaths.length > 0){
			//xml头
			for(String path : rootPaths){
				if(path == null || EMPTY.equals(path.trim())){
					continue;
				}
				template.append(leftIndent);
				leftIndentInc();
				template.append(LEFT_BRACKETS).append(path).append(RIGHT_BRACKETS).append(getEnterLine(format));
			}
		}
		
		String rootField = getNextVar();//rootFieldName = D0  TODO
		String var = getNextVar();
		template.append(leftIndent).append("<#list (" + rootField+ ")?if_exists as " + var + ">").append(getEnterLine(format));
		generateTemplate(xmlMetaType, rootField, template);
		template.append(leftIndent).append("</#list>").append(getEnterLine(format));
		
		if(rootPaths != null && rootPaths.length > 0){
			for(int i = rootPaths.length - 1; i >=0 ; i--){
				if(rootPaths[i] == null || EMPTY.equals(rootPaths[i].trim())){
					continue;
				}
				leftIndentDec();
				template.append(leftIndent);
				template.append(LEFT_BRACKETS_END).append(rootPaths[i]).append(RIGHT_BRACKETS).append(getEnterLine(format));
			}
		}
		return template.toString();
	}
	
	private <T> void generateTemplate(XmlMetaType<T> xmlMetaType,String parentFieldName,StringBuilder template){
		template.append(leftIndent);
		leftIndentInc();
		template.append(LEFT_BRACKETS).append(xmlMetaType.getNodeName()).append(RIGHT_BRACKETS).append(getEnterLine(format));
		boolean isLeaf = xmlMetaType.getChildXmlMetaTypes() == null || xmlMetaType.getChildXmlMetaTypes().isEmpty();
		String field = null;
		if(xmlMetaType.getParentXmlMetaType() != null 
				&& xmlMetaType.getParentXmlMetaType() instanceof ObjectXmlMetaType 
				&& Collection.class.isAssignableFrom(((ObjectXmlMetaType<?>)(xmlMetaType.getParentXmlMetaType())).getClassType())){
			field = parentFieldName;
		}else{
			field = parentFieldName + "." + xmlMetaType.getFieldName();
		}
		if(isLeaf){
			template.append(leftIndent).append(CDATA_LEFT);
			//叶子节点,追加值
			if(xmlMetaType instanceof DateXmlMetaType){
				//日期类型${(device.lastUpdate)???string(device.lastUpdate?string("yyyyMMddHHmmss"),"")}
				template.append("${(").append(field).append(")???string(").append(field).append("?string(\"yyyyMMddHHmmss\"),\"\")}");
			}else{
				//${(device.deviceId)!}
				template.append("${(").append(parentFieldName).append(".").append(xmlMetaType.getFieldName()).append(")!}");
			}
			template.append(CDATA_RIGHT).append(getEnterLine(format));
		}else{
			//非叶子节点
			Map<String, XmlMetaType<?>> childrenMetaTypes = xmlMetaType.getChildXmlMetaTypes();
			Set<Entry<String, XmlMetaType<?>>> entrySet = childrenMetaTypes.entrySet();
			for(Entry<String, XmlMetaType<?>> entry : entrySet){
				XmlMetaType<?> children = entry.getValue();
				if(children instanceof ObjectXmlMetaType){
					if(Collection.class.isAssignableFrom(((ObjectXmlMetaType<?>)children).getClassType())){
						//collection类型节点
						//<#list (device.deviceExtList)?if_exists as deviceExt>
						String var = getNextVar();
						template.append(leftIndent).append("<#list (" + field+ ")?if_exists as " + var + ">").append(getEnterLine(format));
						generateTemplate(children, var, template);
						template.append(leftIndent).append("</#list>").append(getEnterLine(format));
					}else{
						if(children.getParentXmlMetaType()  instanceof ObjectXmlMetaType 
								&& Collection.class.isAssignableFrom(((ObjectXmlMetaType<?>)(children.getParentXmlMetaType())).getClassType())){
							generateTemplate(children, parentFieldName, template);
						}
					}
				}else{
					//普通非叶子节点
					generateTemplate(children, parentFieldName + "." + xmlMetaType.getFieldName(), template);
				}
			}
		}
		leftIndentDec();
		template.append(leftIndent).append(LEFT_BRACKETS_END).append(xmlMetaType.getNodeName()).append(RIGHT_BRACKETS).append(getEnterLine(format));
	}
	
	/**
	 * 缩进增加
	 */
	private void leftIndentInc(){
		leftIndent += getTab(format);
	}
	
	/**
	 * 缩进减少
	 */
	private void leftIndentDec(){
		leftIndent = leftIndent.replaceFirst(TAB, EMPTY);
	}

	private String getEnterLine(boolean format){
		if(format){
			return ENTER_LINE;
		}
		return EMPTY;
	}
	
	private String getTab(boolean format){
		if(format){
			return TAB;
		}
		return EMPTY;
	}
	
	private String getNextVar(){
		return "VAR_TMP_" + varIndex++;
	}
}
