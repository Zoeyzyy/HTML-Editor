package document.documentImpl;

import document.HTMLElement;
import document.SpellChecker;
import document.SpellCheckerManager;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * HTMLElement 的具体实现类
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class HTMLElementImpl extends HTMLElement {
    private String className;


    public HTMLElementImpl() {
    }

    @Override
    public void addChild(HTMLElement child) {
        if (child == null) return;

        List<HTMLElement> children = getChildren();

        // 确保children初始化
        if (children==null) {
            initializeChildren();
            children = getChildren();
        }

        // 确保至少有 "start" 和 "tail"
        if (children.size() < 2) {
            throw new IllegalStateException("Children must contain at least 'start' and 'tail' elements.");
        }

        HTMLElement tail = children.get(children.size() - 1); // 获取尾部元素
        // 插入到 "tail" 前
        HTMLElement lastValidNode = children.get(children.size() - 2); // "tail" 的前一个节点
        lastValidNode.setNextSibling(child);
        child.setPreviousSibling(lastValidNode);
        child.setNextSibling(tail);
        tail.setPreviousSibling(child);

        child.setParent(this);
        child.setIndex(children.size() - 1);

        children.add(children.size() - 1, child); // 插入到 "tail" 前
    }

    @Override
    public void removeChild(HTMLElement child) {
        if (child == null || !getChildren().contains(child)) return;

        List<HTMLElement> children = getChildren();
        int index = child.getIndex();

        HTMLElement prev = child.getPreviousSibling();
        HTMLElement next = child.getNextSibling();

        if (prev != null) prev.setNextSibling(next);
        if (next != null) next.setPreviousSibling(prev);

        children.remove(index);

        // Reset the child's relations
        child.setParent(null);
        child.setPreviousSibling(null);
        child.setNextSibling(null);

        // Update indexes
        for (int i = index; i < children.size() - 1; i++) {
            children.get(i).setIndex(i);
        }
    }

    @Override
    public void removeChild(String id) {
        getChildren().removeIf(child -> id.equals(child.getId()));
    }


    @Override
    public void display() {
        StringBuilder builder = new StringBuilder();
        builder.append("<").append(getTagName());
        if (getId() != null) builder.append(" id=\"").append(getId()).append("\"");
        if (className != null) builder.append(" class=\"").append(className).append("\"");
        builder.append(">");
        if (getTextContent() != null) builder.append(getTextContent());
        for (HTMLElement child : getChildren()) {
            if (child instanceof HTMLElementImpl) {
                ((HTMLElementImpl) child).display();
            }
        }
        builder.append("</").append(getTagName()).append(">");
        System.out.println(builder);
    }

    @Override
    public List<String> checkSpelling(SpellChecker spellChecker) throws IOException {
        List<String> correctedResults = new ArrayList<>();
        // 使用单例 SpellChecker 进行拼写检查
        SpellChecker singletonSpellChecker = SpellCheckerManager.getInstance();
        if (getTextContent() != null && !getTextContent().isEmpty()) {
            String correctedText = singletonSpellChecker.checkSpelling(getTextContent());
            correctedResults.add(correctedText);
        }
        return correctedResults;
    }

    @Override
    public void insertElementBefore(HTMLElement element, String targetId) {
        List<HTMLElement> children = getChildren();
        if (targetId == null) {
            // 直接添加到末尾
            addChild(element);
            return;
        }
        for (int i = 0; i < children.size(); i++) {
            if (targetId.equals(children.get(i).getId())) {
                HTMLElement target = children.get(i);

                element.setParent(this);
                element.setNextSibling(target);
                element.setPreviousSibling(target.getPreviousSibling());
                element.setIndex(i);

                if (target.getPreviousSibling() != null) {
                    target.getPreviousSibling().setNextSibling(element);
                }
                target.setPreviousSibling(element);

                children.add(i, element);

                for (int j = i + 1; j < children.size() - 1; j++) {
                    children.get(j).setIndex(j);
                }
                return;
            }
        }
        throw new IllegalArgumentException("Element with ID " + targetId + " not found.");
    }

    @Override
    public String getInsertLocation(HTMLElement element) {
        HTMLElement nextSibling = element.getNextSibling();
        if (nextSibling != null) {
            return nextSibling.getId();
        }

        // 如果没有标准的 nextSibling，返回 "tail" 的 ID
        List<HTMLElement> children = getChildren();
        if (children != null && !children.isEmpty()) {
            HTMLElement tail = children.get(children.size() - 1);
            if ("tail".equals(tail.getTagName())) {
                return tail.getId();
            }
        }

        return null; // 极端情况下，返回 null
    }

    /**
     * 静态内部类实现 Builder 模式
     */
    public static class BuilderImpl implements Builder {
        private final HTMLElementImpl element;

        public BuilderImpl() {
            this.element = new HTMLElementImpl();
        }

        @Override
        public Builder setTagName(String tagName) {
            element.setTagName(tagName);
            return this;
        }

        @Override
        public Builder setId(String id) {
            element.setId(id);
            return this;
        }

        @Override
        public Builder setClassName(String className) {
            element.setClassName(className);
            return this;
        }

        @Override
        public Builder setTextContent(String content) {
            element.setTextContent(content);
            return this;
        }

        @Override
        public Builder addChild(HTMLElement child) {
            element.addChild(child);
            return this;
        }

        @Override
        public HTMLElement build() {

            try {
                List<String> results = element.checkSpelling(null);

                element.setSpellCheckResults(results);
            } catch (IOException e) {
                element.setSpellCheckResults(new ArrayList<>());
            }
            return element;
        }
    }

    /**
     * 提供 HTMLElement 的 Builder 实现
     * @return 一个 Builder 对象
     */
    public static Builder builder() {
        return new BuilderImpl();
    }
}
