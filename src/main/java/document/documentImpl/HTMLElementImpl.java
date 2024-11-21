package document.documentImpl;

import document.HTMLElement;
import document.SpellChecker;
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
        initializeChildren();
    }

    @Override
    public void addChild(HTMLElement child) {
        if (child == null) return;

        // 确保 children 不为 null
        List<HTMLElement> children = getChildren();
        HTMLElement tail = children.get(children.size() - 1); // Get the tail element

        // Update sibling pointers
        HTMLElement prev = tail.getPreviousSibling();
        prev.setNextSibling(child);
        child.setPreviousSibling(prev);

        child.setNextSibling(tail);
        tail.setPreviousSibling(child);

        child.setParent(this);
        child.setIndex(children.size() - 1);

        children.add(children.size() - 1, child); // Insert before the tail
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
    public List<HTMLElement> getChildren() {
        return super.getChildren();
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
        List<String> results = new ArrayList<>();
        // 检查当前元素的文本内容
        if (getTextContent() != null && !getTextContent().isEmpty()) {
            results.addAll(spellChecker.checkSpelling(getTextContent()));
        }
        // 检查子元素
        for (HTMLElement child : getChildren()) {
            if (!"head".equals(child.getTagName()) && !"tail".equals(child.getTagName())) {
                results.addAll(child.checkSpelling(spellChecker));
            }
        }
        return results;
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
        return element.getNextSibling() != null ? element.getNextSibling().getId() : null;
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
