/**
 * EventHandler.java
 * @author FengMy
 * @since 2014年10月15日
 */
package tools.xml.read.agent;

import tools.xml.read.eventhandler.XmlNodeType;

/**  
 * 功能描述：事件处理接口
 * 
 * @author FengMy
 * @since 2014年10月15日
 */
public interface XmlNodeHandler {
	
	/**
	 * 接收事件
	 * @param event
	 * @throws Exception 
	 */
	public void accept(String nodePath,String content,XmlNodeType nodeType) throws Exception;
	
}
