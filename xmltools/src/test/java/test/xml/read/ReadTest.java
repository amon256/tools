/**
 * ReadTest.java
 * @author FengMy
 * @since 2014年10月15日
 */
package test.xml.read;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import tools.xml.read.RootObjectAccessor;
import tools.xml.read.XmlReader;
import tools.xml.read.meta.XmlMetaType;
import tools.xml.read.meta.metatype.ObjectXmlMetaType;
import tools.xml.read.meta.metatype.StringXmlMetaType;
import tools.xml.read.meta.metatype.parse.XmlMetaTypeParser;

/**  
 * 功能描述：
 * 
 * @author FengMy
 * @since 2014年10月15日
 */
public class ReadTest {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException {
		XmlReader reader = new XmlReader(ReadTest.class.getClassLoader().getResourceAsStream("smsp.xml"));
		XmlMetaType<Smsp> rootType = readMetaType("XmlMeta.xml");//createMetaType();//new ObjectXmlMetaType<HashMap>(HashMap.class);
		long start = System.currentTimeMillis();
		reader.readAsObject(rootType, "MonternetSpData/List/MonternetSp",new RootObjectAccessor<Smsp>() {
			@Override
			public void access(Smsp object) {
			}
		});
		System.out.println(System.currentTimeMillis() - start);
	}
	
	private static XmlMetaType readMetaType(String sourcePath) throws SAXException, IOException, ParserConfigurationException{
		XmlMetaTypeParser<Smsp> parser = new XmlMetaTypeParser<Smsp>();
		XmlMetaType<Smsp> type = parser.parse(ParseTest.class.getClassLoader().getResourceAsStream(sourcePath));
		return type;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
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
