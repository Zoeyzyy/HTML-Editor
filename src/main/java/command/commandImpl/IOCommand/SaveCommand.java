package command.commandImpl.IOCommand;

import document.HTMLDocument;
import document.HTMLElement;

import java.io.File;
import java.io.StringReader;

import javax.swing.text.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import command.Command;

public class SaveCommand implements Command {
    private HTMLDocument document;
    private String filePath;

    public SaveCommand(HTMLDocument document, String filePath) {
        this.document = document;
        this.filePath = filePath;
    }

    public static Command create(HTMLDocument document, String filePath) {
        return new SaveCommand(document, filePath);
    }

    @Override
    public void execute() {
        String file = document.save();
        // save file into filePath
        // TODO
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = (Document) dBuilder.parse(new InputSource(new StringReader(file)));

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.transform(new DOMSource((Node) doc), new StreamResult(new File(filePath)));
            
            System.out.println("XML content written to " + filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
