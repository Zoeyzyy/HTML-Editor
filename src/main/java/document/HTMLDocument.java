package document;

import exception.ElementBadRemoved;
import exception.ElementNotFound;
import lombok.Data;
import lombok.Setter;
import javax.lang.model.element.Element;
import java.util.List;

@Data
public class HTMLDocument {
    private HTMLElement root;
    private boolean showID;
    private StringBuilder sb;

    public HTMLDocument(HTMLElement root) {
        this.root = root;
        this.showID = false;
        this.sb = new StringBuilder();

    }
    public HTMLDocument() {
        root=null;
        showID = false;
        sb = new StringBuilder();
    }

    /**
     * init document
     */
    public void init(){
        root=null;
        // TODO
    }

    /**
     * 读取文件
     */
    public void read(String file){
        // TODO
    }

    public String save() {
        return "";
    }

    /**
     * 根据Id查找当前HTML下的元素
     * @param id element id
     * @return 满足该id的第一个元素 else null
     */
    public HTMLElement findElementById(String id) {
        return findElementById(id,root);
    }

    /**
     *
     * @param tagName 新建element tag name
     * @param idValue 新建element id value
     * @param textContent new element text content
     * @param parentElement 父元素的id value
     */

    public void appendElement(String tagName, String idValue, String textContent, String parentElement) throws ElementNotFound {
        HTMLElement newElement = HTMLElement.builder()
                                            .setId(idValue)
                                            .setTagName(tagName)
                                            .setTextContent(textContent)
                                            .build();

        insertElementById(parentElement,newElement);
    }

    /**
     * 插入element
     * @param parentId 父节点Id
     * @param element 需要插入的元素
     * @throws ElementNotFound
     */
    private void insertElementById(String parentId, HTMLElement element) throws ElementNotFound {
        HTMLElement parent = findElementById(parentId);
        if (parent == null) {
            throw new ElementNotFound("Element: Id "+parentId+" Not Found");
        }
        parent.addChild(element);
    }



    /**
     * 移除元素
     * @param id 需要移除的元素Id
     * @throws ElementBadRemoved
     */
    public void removeElementById(String id) throws ElementBadRemoved {
        if(root.getId().equals(id)){
            throw new ElementBadRemoved("Cannot remove the root element.");
        }
        removeElementById(id,root);
    }

    /**
     * 获取doc 的缩进形式
     * @param indent
     * @return
     */
    public String getIndentFormat(int indent) {
        sb.setLength(0);
        getIndentFormat(this.root,indent,0);
        return sb.toString();
    }

    public String getTreeFormat(boolean spellCheck) {
        sb.setLength(0);
        getTreeFormat(this.root,0);
        return sb.toString();
    }


    private void getIndentFormat(HTMLElement ele, int indent, int level) {
        if(ele==null){
            return;
        }

        printIndent(level, indent);

        String eleTagHeader = String.format("<%s",ele.getTagName());
        if(ele.getTagName()!=null){
            eleTagHeader+=String.format(" id=\"%s\">\n",ele.getId());
        }else
            eleTagHeader+=">\n";

        sb.append(eleTagHeader);

        if(ele.getTextContent()!=null){
            printIndent(level+1, indent);
            sb.append(ele.getTextContent()).append("\n");
        }
        for(HTMLElement child : ele.getChildren()){
            getIndentFormat(child,indent,level+1);
        }

        sb.append(String.format("</%s>\n",ele.getTagName()));
    }

    private void getTreeFormat(HTMLElement element,  int level) {
        // 打印当前元素的标签名和ID
        printIndent(2, level);
        sb.setLength(sb.length() - 1); // 删除printIndent添加的换行符
        sb.append(element.getTagName());

        // 如果有ID，添加ID
        if (element.getId() != null && !element.getId().isEmpty()) {
            sb.append("#").append(element.getId());
        }
        sb.append("\n");

        // 如果有文本内容，添加文本内容
        if (element.getTextContent() != null && !element.getTextContent().isEmpty()) {
            printIndent(2, level + 1);
            sb.append("└── ").append(element.getTextContent()).append("\n");
        }

        // 处理子元素
        List<HTMLElement> children = element.getChildren();
        if (children != null && !children.isEmpty()) {
            for (int i = 0; i < children.size(); i++) {
                HTMLElement child = children.get(i);
                boolean isLast = (i == children.size() - 1);

                // 为子元素添加适当的前缀
                printIndent(2, level + 1);

                if (isLast) {
                    sb.append("└── ");
                } else {
                    sb.append("├── ");
                }

                // 递归处理子元素
                getTreeFormat(child, level + 1);
            }
        }
    }


    public String getSpellCheck() {
        return "";
    }


    private HTMLElement findElementById(String id, HTMLElement ele) {
        if(ele.getId().equals(id)){
            return ele;
        }
        for(HTMLElement child : ele.getChildren()){
            HTMLElement found = findElementById(id, child);
            if(found != null){
                return found;
            }
        }
        return null;
    }

    private void removeElementById(String id, HTMLElement ele) {
        for(HTMLElement child : ele.getChildren()){
            if(child.getId().equals(id)){
                ele.removeChild(child);
            }
        }
    }

    private void printIndent(int indent,int level){
        String format="%" + (level * indent) + "s%s";
        sb.append(String.format(format,"",""));
    }


    public void editID(String oldID, String newID) {
        findElementById(oldID)
                .setId(newID);
    }
}
