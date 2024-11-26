package command.CommandImpl.IOCommand;

import command.commandImpl.IOCommand.ReadCommand;
import command.commandImpl.displayCommand.PrintIndentCommand;
import document.HTMLDocument;
import editor.Editor;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReadCommandTest {
    @Test
    public void execute() {
        Editor editor = new Editor();
        try {
            editor.load("\\src\\main\\resources\\template.html");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        ReadCommand readCommand = new ReadCommand(editor, "\\src\\main\\resources\\Test.html");
        try {
            readCommand.execute();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(byteArrayOutputStream);

        PrintIndentCommand printIndentCommand = new PrintIndentCommand(editor, 1,printStream);
        try {
            printIndentCommand.execute();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        String output = byteArrayOutputStream.toString();
        assertEquals("<html>\n" +
                "<head>\n" +
                "    <title>My Webpage</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<h1 id=\"title\">Welcome to my webpage</h1>\n" +
                "<p id=\"description\">This is a paragraph.</p>\n" +
                "<ul id=\"list\">\n" +
                "    <li id=\"item1\">Item 1</li>\n" +
                "    <li id=\"item2\">Item 2</li>\n" +
                "    <li id=\"item3\">Item 3</li>\n" +
                "</ul>\n" +
                "<div id=\"footer\">\n" +
                "    this is a text contect in div\n" +
                "    <p id=\"last-updated\">Last updated: 2024-01-01</p>\n" +
                "    <p id=\"copyright\">Copyright © 2021 MyWebpage.com</p>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>", output);
        printStream.close();
    }
}
