package club.gclmit.plugin.jetbrains.gitfox.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 插件配置
 *
 * @author <a href="https://blog.gclmit.club">gclm</a>
 * @since 2022/6/29 14:13
 * @since jdk11
 */
@Getter
@Setter
public class Gitfox {
    private String style;
    private String language;
    private List<Item> items;
}
