package xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.lang.reflect.Field;
import java.util.List;

public class DOMConverter {

    // Universal method to export a list of objects to XML using reflection
    public static void exportToXML(List<?> objects, String rootName, String elementName, String filePath) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();

        // Create root element (e.g., <conference>)
        Element rootElement = doc.createElement(rootName);
        doc.appendChild(rootElement);

        for (Object obj : objects) {
            // Create element for the object (e.g., <participant>)
            Element itemElement = doc.createElement(elementName);
            rootElement.appendChild(itemElement);

            // Use reflection to iterate through the class fields
            Class<?> clazz = obj.getClass();
            Field[] fields = clazz.getDeclaredFields();

            for (Field field : fields) {
                // Ignore synthetic fields and serialVersionUID
                if (field.isSynthetic() || field.getName().equals("serialVersionUID")) {
                    continue;
                }
                
                field.setAccessible(true); // Allow access to private fields
                Object value = field.get(obj);
                
                // Create child tag with field name
                Element fieldElement = doc.createElement(field.getName());
                fieldElement.appendChild(doc.createTextNode(value != null ? value.toString() : ""));
                itemElement.appendChild(fieldElement);
            }
        }

        // Write DOM to file
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(filePath));
        transformer.transform(source, result);
    }
}