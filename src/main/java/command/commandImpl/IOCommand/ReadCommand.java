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
        File file = new File(filePath);

        // 检查文件是否存在
        if (!file.exists())
            System.out.println("文件不存在！");
        }

        document.read(file);
    }
}
