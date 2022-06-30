package club.gclmit.plugin.jetbrains.gitfox.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

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
    private String type;
    private Boolean showBranch;
    private Boolean useChinese;
    private List<GitfoxServer> gitfoxServers;
}
