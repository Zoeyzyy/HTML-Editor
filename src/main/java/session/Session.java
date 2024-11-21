package session;

import java.util.*;

import editor.Editor;
import lombok.Getter;

import java.io.*;


public class Session {
    @Getter
    private String id;
    private List<String> files;
    private Map<String, Editor> editors;
    @Getter
    private Editor activeEditor;

    public Session(String id) {
        if (this.recover("./data" + id) == null) {
            this.files = new ArrayList<>();
            this.editors = new HashMap<>();
            this.activeEditor = null;
        }
        this.id = id;
    }

    public void dump(String filename) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            List<String> saved = this.files;
            if (activeEditor != null) {
                saved.add(0, activeEditor.getFileName());
            }

            out.writeObject(saved);
            System.out.println("Object dumped to file: " + filename);
        } catch (IOException e) {
            System.out.println("fail to dump session: " + e);
        }
    }

    public List<String> recover(String filename) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            @SuppressWarnings("unchecked")
            List<String> list = (List<String>) in.readObject();

            System.out.println("Object loaded from file: " + filename);
            if (list.isEmpty()) {
                return null;
            }
            this.files = list.subList(1, list.size());
            this.editors = new HashMap<>();
            for (String file : this.files) {
                Editor editor = new Editor();
                editor.load(file);
                this.editors.put(file, editor);
            }
            this.activeEditor = this.editors.get(list.get(0));
            return list;
        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
            return null;
        }
    }

    public void load(String filename) throws IOException {
        Editor editor = new Editor();
        editor.load(filename);

        editors.put(filename, editor);
        files.add(filename);
        activeEditor = editor;
    }

    public void save(String filename) throws IOException {
        activeEditor.save(filename);
    }

    public boolean confirm() {
        return activeEditor.isModified();
    }

    public void close() {
        if (activeEditor != null) {
            editors.remove(activeEditor.getFileName());
            files.remove(activeEditor.getFileName());
            activeEditor = editors.isEmpty() ? null : editors.get(files.get(0));
        }
    }

    public List<String> getEditorList() {
        return this.files;
    }

    public void activateEditor(String filename) {
        activeEditor = editors.get(filename);
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
        this.dump("./data" + this.id);
    }
}