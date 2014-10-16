/**
 * Smsp.java
 * @author FengMy
 * @since 2014年10月16日
 */
package test.xml.read;

import java.util.List;

/**  
 * 功能描述：
 * 
 * @author FengMy
 * @since 2014年10月16日
 */
public class Smsp {
	private String spName;
	
	private String gwCode;
	private String ip;
	
	private String id;
	
	private List<Province> provinceList;

	public String getSpName() {
		return spName;
	}

	public void setSpName(String spName) {
		this.spName = spName;
	}

	public String getGwCode() {
		return gwCode;
	}

	public void setGwCode(String gwCode) {
		this.gwCode = gwCode;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Province> getProvinceList() {
		return provinceList;
	}

	public void setProvinceList(List<Province> provinceList) {
		this.provinceList = provinceList;
	}
}
