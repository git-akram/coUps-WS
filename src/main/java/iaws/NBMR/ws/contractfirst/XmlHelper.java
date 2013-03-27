package iaws.NBMR.ws.contractfirst;

import org.springframework.core.io.ClassPathResource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * @author franck Silvestre
 */
public class XmlHelper {

    /**
     * Return he dom root element of an xml file
     * @param filePathInClassPath  the file path relative to the classpath
     * @return  the root element
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    public static Element getRootElementFromFileInClasspath(String filePathInClassPath) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        // root elements
        Document doc = docBuilder.parse(new ClassPathResource(filePathInClassPath).getInputStream());
        return doc.getDocumentElement();
    }

}
