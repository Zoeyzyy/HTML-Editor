package command.CommandImpl.displayCommand;

import command.commandImpl.displayCommand.PrintTreeCommand;
import command.commandImpl.editCommand.AppendCommand;
import document.HTMLDocument;
import editor.Editor;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PrintTreeCommandTest {
    @Test
    public void execute() {
        Editor editor = new Editor();
        editor.init();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(byteArrayOutputStream);

        PrintTreeCommand printTreeCommand = new PrintTreeCommand(editor, printStream);
        printTreeCommand.execute();

        String output = byteArrayOutputStream.toString();
        assertEquals("html\n" +
                "├── head\n" +
                "│   └── title\n" +
                "└── body\n", output);
        printStream.close();
    }

    @Test
    public void executeTreeSpellCheck() {
        Editor editor = new Editor();
        editor.init();

        AppendCommand appendCommand = new AppendCommand(editor, "div", "id1", "body", "Hello Wrold");
        appendCommand.execute();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(byteArrayOutputStream);

        PrintTreeCommand printTreeCommand = new PrintTreeCommand(editor, printStream);
        printTreeCommand.execute();

        String output = byteArrayOutputStream.toString();
        assertEquals("html\n" +
                "├── head\n" +
                "│   └── title\n" +
                "└── body\n"+
                "    └── [x]div\n"+
                "        └── Hello Wrold\n", output);
        printStream.close();
    }

    /**
     * 测试复杂Html的正常打印，并且展示id
     * @throws IOException
     */
    @Test
    public void executeComplexTreeCheck() throws IOException {
        Editor editor = new Editor();
        editor.init();
        editor.load("src/main/resources/Test.html");
        editor.showId(true);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(byteArrayOutputStream);

        PrintTreeCommand printTreeCommand = new PrintTreeCommand(editor, printStream);
        printTreeCommand.execute();

        String output = byteArrayOutputStream.toString();
        assertEquals("html\n" +
                "├── head\n" +
                "│   └── title\n" +
                "│       └── My Webpage\n" +
                "└── body\n" +
                "    ├── h1#title\n" +
                "    │   └── Welcome to my webpage\n" +
                "    ├── p#description\n" +
                "    │   └── This is a paragraph.\n" +
                "    ├── ul#list\n" +
                "    │   ├── li#item1\n" +
                "    │   │   └── Item 1\n" +
                "    │   ├── li#item2\n" +
                "    │   │   └── Item 2\n" +
                "    │   └── li#item3\n" +
                "    │       └── Item 3\n" +
                "    └── [x]div#footer\n" +
                "        ├── this is a text contect in div\n" +
                "        ├── p#last-updated\n" +
                "        │   └── Last updated: 2024-01-01\n" +
                "        └── p#copyright\n" +
                "            └── Copyright © 2021 MyWebpage.com\n", output);
        printStream.close();
    }
}
