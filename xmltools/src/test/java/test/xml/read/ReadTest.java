/**
 * ReadTest.java
 * @author FengMy
 * @since 2014年10月15日
 */
package test.xml.read;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import tools.xml.read.RootObjectAccessor;
import tools.xml.read.XmlReader;
import tools.xml.read.meta.XmlMetaType;
import tools.xml.read.meta.metatype.ObjectXmlMetaType;
import tools.xml.read.meta.metatype.StringXmlMetaType;

/**  
 * 功能描述：
 * 
 * @author FengMy
 * @since 2014年10月15日
 */
public class ReadTest {

	public static void main(String[] args) throws FileNotFoundException {
		XmlReader reader = new XmlReader(new FileInputStream("C:\\Users\\fengmengyue\\Desktop\\s.xml"));
		XmlMetaType<Smsp> rootType = createMetaType();//new ObjectXmlMetaType<HashMap>(HashMap.class);
		long start = System.currentTimeMillis();
		reader.readAsObject(rootType, "MonternetSpData/List/MonternetSp",new RootObjectAccessor<Smsp>() {
			@Override
			public void access(Smsp object) {
			}
		});
		System.out.println(System.currentTimeMillis() - start);
	}
	
	private static XmlMetaType createMetaType(){
		XmlMetaType smssp = new ObjectXmlMetaType(Smsp.class);
		smssp.addChildXmlMetaTypes("SpName", new StringXmlMetaType("spName"));
		smssp.addChildXmlMetaTypes("GWCode", new StringXmlMetaType("gwCode"));
		smssp.addChildXmlMetaTypes("PlateformIP", new StringXmlMetaType("ip"));
		smssp.addChildXmlMetaTypes("ProcId", new StringXmlMetaType("id"));
		XmlMetaType provList = new ObjectXmlMetaType(ArrayList.class,"provinceList");
		smssp.addChildXmlMetaTypes("ProvinceList", provList);
		XmlMetaType prov = new ObjectXmlMetaType(Province.class);
		provList.addChildXmlMetaTypes("Province", prov);
		prov.addChildXmlMetaTypes("Status", new StringXmlMetaType("status"));
		prov.addChildXmlMetaTypes("ProvinceCode", new StringXmlMetaType("provinceCode"));
		prov.addChildXmlMetaTypes("GWCode", new StringXmlMetaType("gwCode"));
		prov.addChildXmlMetaTypes("SpId", new StringXmlMetaType("spId"));
		return smssp;
	}
}
