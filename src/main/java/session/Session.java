package session;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
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
               this.editors.put(filename, new Editor(filename));
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
        Editor editor = new Editor(filename);
        editors.put(filename, editor);
        files.add(filename);
        activeEditor = editor;
    }

    public void save(String filename) {
        // TODO: save file
        editors.get(filename).save();
    }

    public boolean confirm() {
        return activeEditor.isModified();
    }

    public void close() {
        if (activeEditor != null) {
            editors.remove(activeEditor.getName());
            activeEditor = editors.isEmpty() ? null : editors.get(0);
        }
    }

    public List<String> getEditorList() {
        return this.files;
    }

    public void activateEditor(String filename) {
        activeEditor = editors.get(filename);

    }

    private String getDirTreeFormat(File dir, int level) {
        if (!dir.isDirectory()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < level; i++) {
            sb.append("  ");
        }
        sb.append(dir.getName()).append("\n");
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                sb.append(getDirTreeFormat(file, level + 1));
            } else {
                for (int i = 0; i < level + 1; i++) {
                    sb.append("  ");
                }

                sb.append(file.getName());
                if (this.editors.get(file.getName()).get_showid() ) {
                    sb.append("#" + this.editors.get(file.getName()).get_id());
                }
                if (this.files.contains(file.getName())) {
                    sb.append(" *");
                }
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    private String getDirIndentFormat(File dir, int indent) {
        if (!dir.isDirectory()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < indent; i++) {
            sb.append("  ");
        }
        sb.append(dir.getName()).append("\n");
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                sb.append(getDirIndentFormat(file, indent + 1));
            } else {
                for (int i = 0; i < indent + 1; i++) {
                    sb.append("  ");
                }
    
                sb.append(file.getName());
                if (this.editors.get(file.getName()).get_showid()) {
                    sb.append("#" + this.editors.get(file.getName()).get_id());
                }
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