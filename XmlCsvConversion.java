package programs;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlCsvConversion {
    public static void main(String[] args) {
        String xmlFilePath = "C:\\Users\\nathi\\testdata\\books.xml";
        String csvFilePath = "C:\\Users\\nathi\\testdata\\output.csv";

        convertXMLtoCSV(xmlFilePath, csvFilePath);
    }

    public static void convertXMLtoCSV(String xmlFilePath, String csvFilePath) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(xmlFilePath));

            FileWriter csvWriter = new FileWriter(csvFilePath);

            // Get the root element
            Element root = document.getDocumentElement();
            NodeList nodeList = root.getChildNodes();

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    // Assuming each element corresponds to a row in the CSV file
                    StringBuilder csvRow = new StringBuilder();
                    NodeList childNodes = element.getChildNodes();
                    for (int j = 0; j < childNodes.getLength(); j++) {
                        Node childNode = childNodes.item(j);
                        if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                            csvRow.append(childNode.getTextContent().trim()).append(",");
                        }
                    }
                    csvRow.deleteCharAt(csvRow.length() - 1); // Remove trailing comma
                    csvRow.append("\n");
                    csvWriter.write(csvRow.toString());
                }
            }

            csvWriter.close();
            System.out.println("Conversion completed successfully.");
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }
}