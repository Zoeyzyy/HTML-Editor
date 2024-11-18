package command.commandImpl.IOCommand;

import document.HTMLDocument;
import document.HTMLElement;

import java.io.File;
import java.io.StringWriter;

import javax.swing.text.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Node;

import command.Command;

public class ReadCommand implements Command {
    private final HTMLDocument document;
    private final String filePath;

    public ReadCommand(HTMLDocument document, String filePath) {
        this.document = document;
        this.filePath = filePath;
    }

    public static Command create(HTMLDocument document, String filePath) {
        return new ReadCommand(document, filePath);
    }

    @Override
    public void execute() {
        // Read the HTML file
        // TODO
        String file = "";
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = (Document) dBuilder.parse(new File(filePath));

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource((Node) doc), new StreamResult(writer));

            file = writer.getBuffer().toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        document.read(file);
    }
}
