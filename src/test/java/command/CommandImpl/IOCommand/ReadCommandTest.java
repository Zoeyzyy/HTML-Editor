package command.CommandImpl.IOCommand;

import command.commandImpl.IOCommand.ReadCommand;
import command.commandImpl.displayCommand.PrintIndentCommand;
import document.HTMLDocument;
import editor.Editor;
import org.junit.jupiter.api.Test;
import session.Session;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReadCommandTest {
    @Test
    public void execute() {
        Session session = new Session("default");

        ReadCommand readCommand = new ReadCommand(session, "src\\main\\resources\\Test.html");
        try {
            readCommand.execute();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(byteArrayOutputStream);

        PrintIndentCommand printIndentCommand = new PrintIndentCommand(session.getActiveEditor(), 1, printStream);
        try {
            printIndentCommand.execute();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        String output = byteArrayOutputStream.toString();
        assertEquals("<html>\n" +
                " <head>\n" +
                "  <title>\n" +
                "   My Webpage\n" +
                "  </title>\n" +
                " </head>\n" +
                " <body>\n" +
                "  <h1 id=\"title\">\n" +
                "   Welcome to my webpage\n" +
                "  </h1>\n" +
                "  <p id=\"description\">\n" +
                "   This is a paragraph.\n" +
                "  </p>\n" +
                "  <ul id=\"list\">\n" +
                "   <li id=\"item1\">\n" +
                "    Item 1\n" +
                "   </li>\n" +
                "   <li id=\"item2\">\n" +
                "    Item 2\n" +
                "   </li>\n" +
                "   <li id=\"item3\">\n" +
                "    Item 3\n" +
                "   </li>\n" +
                "  </ul>\n" +
                "  <div id=\"footer\">\n" +
                "   this is a text contect in div\n" +
                "   <p id=\"last-updated\">\n" +
                "    Last updated: 2024-01-01\n" +
                "   </p>\n" +
                "   <p id=\"copyright\">\n" +
                "    Copyright © 2021 MyWebpage.com\n" +
                "   </p>\n" +
                "  </div>\n" +
                " </body>\n" +
                "</html>", output);
        printStream.close();
    }
}
