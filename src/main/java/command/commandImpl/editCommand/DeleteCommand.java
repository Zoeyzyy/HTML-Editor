package command.commandImpl.editCommand;

import command.CanUndoCommand;
import command.Command;
import editor.Editor;

public class DeleteCommand implements CanUndoCommand {
    private final Editor editor;
    private final String element; // ID
    private String tagName;
    private String idValue;
    private String insertLocation;
    private String textContent;

    public DeleteCommand(Editor editor, String element) {
        this.editor = editor;
        this.element = element;
    }

    public static Command create(Editor editor, String element) {
        return new DeleteCommand(editor, element);
    }

    @Override
    public void execute() throws Exception {
        // TODO: whether touch HTMLelement or not
        tagName = editor.getDocument().findElementById(element).getTagName();
        idValue = editor.getDocument().findElementById(element).getId();
        insertLocation = editor.getDocument().findElementById(element).getInsertLocation(editor.getDocument().findElementById(element));
        textContent = editor.getDocument().findElementById(element).getTextContent();
//        System.out.println("When executing DeleteCommand,the tagName/idValue/textContent: " + tagName + idValue + textContent);
//        System.out.println("After execute DeleteCommand,insertLocation is " + insertLocation);

        editor.delete(element);
    }

    @Override
    public void undo() throws Exception {
        editor.insert(tagName, idValue, insertLocation, textContent);
    }
}
