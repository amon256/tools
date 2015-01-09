/**
 * XmlEventAgent.java
 * @author FengMy
 * @since 2014年10月15日
 */
package tools.xml.read.agent;

import tools.xml.read.eventhandler.XmlNodeType;

/**  
 * 功能描述：xml事件处理总线
 * TODO 事件链
 * @author FengMy
 * @since 2014年10月15日
 */
public class XmlNodeAgent {
	private XmlNodeHandler handler;
	public XmlNodeAgent(XmlNodeHandler eventHandler){
		this.handler = eventHandler;
	}
	
	/**
	 * 分发事件
	 * @param event
	 * @throws Exception 
	 */
	public void dispatch(String nodePath,String content,XmlNodeType nodeType) throws Exception{
		handler.accept(nodePath,content,nodeType);
	}
}
