package command.commandImpl.editCommand;

import command.CanUndoCommand;
import command.Command;
import editor.Editor;

public class EditTextCommand implements CanUndoCommand {
    private final Editor editor;
    private final String element; // ID
    private final String newTextContent;
    private String oldTextContent;

    public EditTextCommand(Editor editor, String element, String newTextContent) {
        this.editor = editor;
        this.element = element;
        this.newTextContent = newTextContent;
    }

    public static Command create(Editor editor, String element, String newTextContent) {
        return new EditTextCommand(editor, element, newTextContent);
    }

    @Override
    public void execute() throws Exception{
        // TODO： whether touch HTMLElement or not
        oldTextContent = editor.getDocument().findElementById(element).getTextContent();

        editor.editText(element, newTextContent);
    }

    @Override
    public void undo() throws Exception {
        editor.editText(element, oldTextContent);
    }
}
