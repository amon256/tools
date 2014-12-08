/**
 * ReadAndSave.java
 * @author FengMy
 * @since 2014年12月4日
 */
package test.xml.read.save;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import tools.xml.read.RootObjectAccessor;
import tools.xml.read.XmlReader;
import tools.xml.read.meta.XmlMetaType;
import tools.xml.read.meta.metatype.parse.XmlMetaTypeParser;

/**  
 * 功能描述：
 * 
 * @author FengMy
 * @since 2014年12月4日
 */
public class ReadAndSave {

	/**
	 * @param args
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException, ClassNotFoundException, SQLException {
		long start = System.currentTimeMillis();
		XmlMetaTypeParser<SmsGwSec> parser = new XmlMetaTypeParser<SmsGwSec>();
		XmlMetaType<SmsGwSec> type = parser.parse(ReadAndSave.class.getClassLoader().getResourceAsStream("test/xml/read/save/XmlMeta.xml"));
		final List<SmsGwSec> dataList = new LinkedList<SmsGwSec>();
		XmlReader reader = new XmlReader(new FileInputStream("C:/Users/fengmengyue/Desktop/Same.xml"));
		reader.readAsObject(type, "SmsGwSectionData/List/SmsGwSection", new RootObjectAccessor<SmsGwSec>() {
			@Override
			public void access(SmsGwSec object) {
				dataList.add(object);
			}
		});
		System.out.println("读取对象个数:" + dataList.size());
		System.out.println("读对象耗时:" + (System.currentTimeMillis() - start) + "ms.");
	}

}
