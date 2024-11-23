package command.commandImpl.IOCommand;


import java.io.File;
import java.io.StringReader;

import javax.swing.text.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import editor.Editor;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import command.Command;
import session.Session;

public class SaveCommand implements Command {
    private final Session session;
    private final String filePath;

    public SaveCommand(Session session, String filePath) {
        this.session = session;
        this.filePath = filePath;
    }

    public static Command create(Session session, String filePath) {
        return new SaveCommand(session, filePath);
    }

    @Override
    public void execute() {
        String currentPath = System.getProperty("user.dir");
        String absoluteFilePath = currentPath + filePath;
        try {
            session.save(absoluteFilePath);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
