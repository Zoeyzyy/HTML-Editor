package session;

import java.util.*;

import editor.Editor;

import java.io.*;


public class Session {
    private String id;
    private List<String> files;
    private Map<String, Editor> editors;
    private Editor activeEditor;

    public Session(String id) {
        
        this.editors = new HashMap<>();
        this.activeEditor = null;
        if (this.recover(id) != null) {
           for (String filename : this.files) {
               Editor editor = new Editor();
               editor.load(filename);
               this.editors.put(filename, editor);
           }
        }else{
            this.files = new ArrayList<>();
        }
        this.id = id;
    }

    public void dump(String filename) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(this.files);
            System.out.println("Object dumped to file: " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> recover(String filename) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            @SuppressWarnings("unchecked")
            List<String> list = (List<String>) in.readObject();

            System.out.println("Object loaded from file: " + filename);
            this.files = list;
            return list;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void load(String filename) {
        Editor editor = new Editor();
        editor.load(filename);
        editors.put(filename, editor);
        files.add(filename);
        activeEditor = editor;
    }

    public void save(String filename) {
        editors.get(filename).save();
    }

    public boolean confirm() {
        return activeEditor.isModified();
    }

    public void close() {
        if (activeEditor != null) {
            editors.remove(activeEditor.getFileName());
            files.remove(activeEditor.getFileName());
            activeEditor = editors.isEmpty() ? null : editors.get(0);
        }
    }

    public List<String> getEditorList() {
        return this.files;
    }

    public void activateEditor(String filename) {
        activeEditor = editors.get(filename);
    }

    public Editor getActiveEditor() {
        return this.activeEditor;
    }

    public String getDirTreeFormat(int level) {
        File activeFile = new File(activeEditor.getFileName());
        File dir = activeFile.getParentFile();
        if (!dir.isDirectory()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < level; i++) {
            sb.append("  ");
        }
        sb.append(dir.getName()).append("\n");
        for (File file : Objects.requireNonNull(dir.listFiles())) {
            if (file.isDirectory()) {
                sb.append(getDirTreeFormat(level + 1));
            } else {
                for (int i = 0; i < level + 1; i++) {
                    sb.append("  ");
                }

                sb.append(file.getName());
                if (this.files.contains(file.getName())) {
                    sb.append(" *");
                }
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    public String getDirIndentFormat(int indent) {
        File activeFile = new File(activeEditor.getFileName());
        File dir = activeFile.getParentFile();
        if (!dir.isDirectory()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < indent; i++) {
            sb.append("  ");
        }
        sb.append(dir.getName()).append("\n");
        for (File file : Objects.requireNonNull(dir.listFiles())) {
            if (file.isDirectory()) {
                sb.append(getDirIndentFormat(indent + 1));
            } else {
                for (int i = 0; i < indent + 1; i++) {
                    sb.append("  ");
                }
    
                sb.append(file.getName());
                if (this.files.contains(file.getName())) {
                    sb.append(" *");
                }
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    public Session enter(String id) {
        return new Session(id);
    }

    public void exit() {
        this.dump(this.id);
    }
}