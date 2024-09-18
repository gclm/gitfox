package club.gclmit.gitfox.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;


/**
 * 插件配置
 *
 * @author <a href="https://blog.gclmit.club">gclm</a>
 * @since 2022/6/29 14:13
 * @since jdk11
 */
public class Gitfox implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String style;
    private String language;
    private List<Item> items;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Gitfox gitfox = (Gitfox)obj;
        return Objects.equals(style, gitfox.style) && Objects.equals(language, gitfox.language)
            && Objects.equals(items, gitfox.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(style, language, items);
    }


    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
