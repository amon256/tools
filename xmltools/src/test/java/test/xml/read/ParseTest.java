/**
 * ParseTest.java
 * @author FengMy
 * @since 2014年10月17日
 */
package test.xml.read;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import tools.xml.meta.XmlMetaType;
import tools.xml.meta.metatype.parse.XmlMetaTypeParser;

/**  
 * 功能描述：
 * 
 * @author FengMy
 * @since 2014年10月17日
 */
public class ParseTest {

	public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException {
		XmlMetaTypeParser<Smsp> parser = new XmlMetaTypeParser<Smsp>();
		XmlMetaType<Smsp> type = parser.parse(ParseTest.class.getClassLoader().getResourceAsStream("XmlMeta.xml"));
		System.out.println(type);
	}
}
