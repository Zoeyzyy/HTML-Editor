package document;

import exception.ElementBadRemoved;
import exception.ElementNotFound;

public class HTMLDocument {
    private final HTMLElement root;

    public HTMLDocument(HTMLElement root) {
        this.root = root;
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
     * 插入element
     * @param parentId 父节点Id
     * @param element 需要插入的元素
     * @throws ElementNotFound
     */
    public void insertElementById(String parentId, HTMLElement element) throws ElementNotFound {
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

    public void display(){
        root.display();
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





}
