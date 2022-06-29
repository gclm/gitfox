package club.gclmit.plugin.jetbrain.gitfox.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * CommitGuide
 *
 * @author <a href="https://blog.gclmit.club">gclm</a>
 * @since 2022/3/7 5:34 PM
 * @since jdk11
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CommitGuide implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String COMMIT_GUIDE_TEMPLATE = "%s: %s \n\n%s";

    public static final String COMMIT_GUIDE_BRANCH_TEMPLATE = "%s: %s \n\n%s\n\nSubmit Branch: %s";

    /**
     * emoji
     */
    private String emoji;

    /**
     * code
     */
    private String code;

    /**
     * 中文描述
     */
    private String description;

    /**
     * 英文描述
     */
    @JSONField(name = "description_en")
    private String descriptionEn;

}
