/**
 * WriteTemplate.java
 * @author FengMy
 * @since 2015年1月9日
 */
package test.xml.write;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import test.xml.read.ParseTest;
import test.xml.read.Smsp;
import tools.xml.meta.XmlMetaType;
import tools.xml.meta.metatype.parse.XmlMetaTypeParser;
import tools.xml.read.RootObjectAccessor;
import tools.xml.read.XmlReader;
import tools.xml.write.XmlWriter;

/**  
 * 功能描述：
 * 
 * @author FengMy
 * @since 2015年1月9日
 */
public class WriteTemplate {

	public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException {
		XmlReader reader = new XmlReader(ParseTest.class.getClassLoader().getResourceAsStream("smsp.xml"));
		XmlMetaType<Smsp> rootType = readMetaType("XmlMeta.xml");//createMetaType();//new ObjectXmlMetaType<HashMap>(HashMap.class);
		final List<Smsp> dataList = new LinkedList<Smsp>();
		reader.readAsObject(rootType, "MonternetSpData/List/MonternetSp",new RootObjectAccessor<Smsp>() {
			@Override
			public void access(Smsp object) {
				dataList.add(object);
			}
		});
		
		System.out.println(dataList.size());
		
		XmlWriter writer = new XmlWriter(new FileOutputStream("D:/cc.ftl"));
		XmlMetaTypeParser<Smsp> parser = new XmlMetaTypeParser<Smsp>();
		writer.write(dataList, rootType, "MonternetSpData/list", false);
//		String template = writer.generateTemplate(type, "MonternetSpData/list", true);
//		System.out.println(template);
	}
	
	@SuppressWarnings("rawtypes")
	private static XmlMetaType readMetaType(String sourcePath) throws SAXException, IOException, ParserConfigurationException{
		XmlMetaTypeParser<Smsp> parser = new XmlMetaTypeParser<Smsp>();
		XmlMetaType<Smsp> type = parser.parse(ParseTest.class.getClassLoader().getResourceAsStream(sourcePath));
		return type;
	}
}
