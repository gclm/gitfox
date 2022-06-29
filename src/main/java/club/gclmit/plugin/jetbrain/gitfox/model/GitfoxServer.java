package club.gclmit.plugin.jetbrain.gitfox.model;

import lombok.*;

/**
 * 基础服务信息配置
 *
 * @author gclm
 */
@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@EqualsAndHashCode(callSuper = false)
public class GitfoxServer {

    public String key;
    public String value;

}
