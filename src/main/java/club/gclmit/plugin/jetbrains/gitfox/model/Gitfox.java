package club.gclmit.plugin.jetbrains.gitfox.model;

import java.util.List;
import java.util.Objects;

import lombok.Data;

/**
 * 插件配置
 *
 * @author <a href="https://blog.gclmit.club">gclm</a>
 * @since 2022/6/29 14:13
 * @since jdk11
 */
@Data
public class Gitfox {
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
}
