package club.gclmit.gitfox.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;


/**
 * 基础服务信息配置
 *
 * @author gclm
 */
public class Item implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String key;
    private String value;

    public Item() {
    }

    public Item(String key, String value) {
        this.key = key;
        this.value = value;
    }

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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Item{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
