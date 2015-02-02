/**
 * WriteTemplate.java
 * @author FengMy
 * @since 2015年1月9日
 */
package test.xml.write;

import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import test.xml.read.Smsp;
import tools.xml.meta.XmlMetaType;
import tools.xml.meta.metatype.parse.XmlMetaTypeParser;
import tools.xml.write.XmlWriter;

/**  
 * 功能描述：
 * 
 * @author FengMy
 * @since 2015年1月9日
 */
public class WriteTemplate {

	public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException {
		XmlWriter writer = new XmlWriter(new FileOutputStream("D:/cc.ftl"));
		XmlMetaTypeParser<Smsp> parser = new XmlMetaTypeParser<Smsp>();
		XmlMetaType<Smsp> type = parser.parse(XmlWriter.class.getClassLoader().getResourceAsStream("XmlMeta.xml"));
		String template = writer.generateTemplate(type, "MonternetSpData/list", true);
		System.out.println(template);
	}
}
