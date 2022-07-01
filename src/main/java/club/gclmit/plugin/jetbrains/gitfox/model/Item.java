package club.gclmit.plugin.jetbrains.gitfox.model;

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
public class Item {

    private String key;
    private String value;

}
