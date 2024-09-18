package club.gclmit.gitfox.model;

import java.io.Serial;
import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;


/**
 * CommitGuide
 *
 * @author <a href="https://blog.gclmit.club">gclm</a>
 * @since 2022/3/7 5:34 PM
 * @since jdk11
 */
public class CommitGuide implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public static final Integer MAX_LINE_LENGTH = 72;
    public static final String COMMIT_GUIDE_TEMPLATE = "%s: %s \n\n%s";
    public static final String COMMIT_GUIDE_BRANCH_TEMPLATE = "%s: %s \n\n%s\n\nSubmit Branch: %s";

    public static final String CI_TEMPLATE = "\n\n[skip ci]";

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


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescriptionEn() {
        return descriptionEn;
    }

    public void setDescriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
    }
}
