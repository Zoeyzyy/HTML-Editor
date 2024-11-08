package document;

import exception.ElementBadRemoved;
import exception.ElementNotFound;
import lombok.Data;
import lombok.Setter;

@Data
public class HTMLDocument {
    private final HTMLElement root;
    private boolean showID;

    public HTMLDocument(HTMLElement root) {
        this.root = root;
        this.showID = false;
    }

    /**
     * init document
     */
    public void init(){
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

    public String getIndentFormat(int indent) {

        return "";
    }

    public String getTreeFormat(boolean spellCheck) {
        return "";
    }

    public boolean getSpellCheck() {
        return false;
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


    public void editID(String oldID, String newID) {
        // TODO
    }
}
