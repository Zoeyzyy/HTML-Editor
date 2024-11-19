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
        // 初始化 children 为一个空列表，防止 NullPointerException
        setChildren(new ArrayList<>());
    }

    @Override
    public void addChild(HTMLElement child) {
        if (child != null) {
            getChildren().add(child);
        }
    }

    @Override
    public void removeChild(HTMLElement child) {
        getChildren().remove(child);
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
            results.addAll(child.checkSpelling(spellChecker));
        }
        return results;
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
