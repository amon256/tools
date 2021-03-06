/**
 * ReadTest.java
 * @author FengMy
 * @since 2014年10月15日
 */
package test.xml.read;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import tools.xml.meta.XmlMetaType;
import tools.xml.meta.metatype.ObjectXmlMetaType;
import tools.xml.meta.metatype.StringXmlMetaType;
import tools.xml.meta.metatype.parse.XmlMetaTypeParser;
import tools.xml.read.RootObjectAccessor;
import tools.xml.read.XmlReader;

/**  
 * 功能描述：
 * 
 * @author FengMy
 * @since 2014年10月15日
 */
public class ReadTest {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException {
		XmlReader reader = new XmlReader(ParseTest.class.getClassLoader().getResourceAsStream("smsp.xml"));
		XmlMetaType<Smsp> rootType = readMetaType("XmlMeta.xml");//createMetaType();//new ObjectXmlMetaType<HashMap>(HashMap.class);
		long start = System.currentTimeMillis();
		reader.readAsObject(rootType, "MonternetSpData/List/MonternetSp",new RootObjectAccessor<Smsp>() {
			@Override
			public void access(Smsp object) {
				System.out.println(object.toString());
			}
		});
		System.gc();
		System.out.println(System.currentTimeMillis() - start);
	}
	
	@SuppressWarnings("rawtypes")
	private static XmlMetaType readMetaType(String sourcePath) throws SAXException, IOException, ParserConfigurationException{
		XmlMetaTypeParser<Smsp> parser = new XmlMetaTypeParser<Smsp>();
		XmlMetaType<Smsp> type = parser.parse(ParseTest.class.getClassLoader().getResourceAsStream(sourcePath));
		return type;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
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
