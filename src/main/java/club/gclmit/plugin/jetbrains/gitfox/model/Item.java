package club.gclmit.plugin.jetbrains.gitfox.model;

import java.util.List;

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

    public static Item getItem(List<Item> data, String key, boolean isKey) {
        for (Item item : data) {
            if (isKey) {
                if (item.getKey().equals(key)) {
                    return item;
                }
            } else {
                if (item.getValue().equals(key)) {
                    return item;
                }
            }
        }
        return null;
    }
}
