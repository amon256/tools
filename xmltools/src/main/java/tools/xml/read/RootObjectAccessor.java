/**
 * RootObjectAccessor.java
 * @author FengMy
 * @since 2014年10月16日
 */
package tools.xml.read;

/**  
 * 功能描述：根对象接收器
 * 
 * @author FengMy
 * @since 2014年10月16日
 */
public interface RootObjectAccessor<T> {
	
	/**
	 * 接收数据
	 * @param object
	 * @return
	 */
	public void access(T object);

}
