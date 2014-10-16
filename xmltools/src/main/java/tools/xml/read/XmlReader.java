/**
 * SaxXmlReader.java.java
 * @author FengMy
 * @since 2014年10月13日
 */
package tools.xml.read;

import java.io.InputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import tools.xml.read.agent.XmlNodeAgent;
import tools.xml.read.meta.DefaultEventHandler;
import tools.xml.read.meta.XmlMetaType;
import tools.xml.read.meta.XmlNodeType;

/**  
 * 功能描述：XML读取工具
 * 
 * @author FengMy
 * @since 2014年10月13日
 */
public class XmlReader {
	
	/**
	 * xml数据流
	 */
	private InputStream resourceStream;
	
	public XmlReader(InputStream resourceStream){
		this.resourceStream = resourceStream;
	}
	
	/**
	 * 读取为配置文件
	 * @param xmlMetaType
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public <T> void readAsObject(XmlMetaType<T> xmlMetaType,String rootPath,RootObjectAccessor accessor){
		DefaultEventHandler<T> eventHandler = new DefaultEventHandler<T>(xmlMetaType,rootPath,accessor);
		SaxReader reader = new SaxReader(new XmlNodeAgent(eventHandler),rootPath);
		reader.read();
	}
	
	private class SaxReader{
		private XmlNodeAgent agent;
		private String rootPath;
		SaxReader(XmlNodeAgent agent,String rootPath){
			this.agent = agent;
			this.rootPath = rootPath;
		}
		public void read(){
			SAXParserFactory factory = SAXParserFactory.newInstance();  
			SAXParser parser;
			try{
				parser = factory.newSAXParser();
				parser.parse(XmlReader.this.resourceStream, new DefaultHandler(){
					private String path = "";
					private StringBuilder content;
					@Override
					public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
						path = path + ("".equals(path)?"":"/") + qName;
						if(!path.startsWith(rootPath)){
							return;
						}
						content = new  StringBuilder();
						try {
							agent.dispatch(path,null,XmlNodeType.START);
						} catch (Exception e) {
							throw new RuntimeException(e);
						}
					}
					
					@Override
					public void endElement(String uri, String localName, String qName) throws SAXException {
						if(path.startsWith(rootPath)){
							try {
								agent.dispatch(path, content.toString(),XmlNodeType.END);
							} catch (Exception e) {
								throw new RuntimeException(e);
							}
						}
						if(path.lastIndexOf("/") > 0){
							path = path.substring(0, path.lastIndexOf("/"));
						}else{
							path = path.substring(0, path.lastIndexOf(qName));
						}
					}
					
					@Override
					public void characters(char[] ch, int start, int length) throws SAXException {
						if(path.compareTo(rootPath) <= 0){
							return;
						}
						content.append(new String(ch,start,length));
					}
				});
			}catch(Exception e){
				throw new RuntimeException(e);
			}
		}
	}

}
