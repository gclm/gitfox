package club.gclmit.plugin.jetbrains.gitfox.model;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;
import lombok.EqualsAndHashCode;

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

    public static final String COMMIT_GUIDE_TEMPLATE = "%s: %s \n\n%s";
    public static final String COMMIT_GUIDE_BRANCH_TEMPLATE = "%s: %s \n\n%s\n\nSubmit Branch: %s";
    private static final long serialVersionUID = 1L;
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
