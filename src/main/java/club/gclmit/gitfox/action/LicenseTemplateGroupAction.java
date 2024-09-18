package club.gclmit.gitfox.action;

import cn.hutool.core.util.ArrayUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.intellij.icons.AllIcons;
import com.intellij.ide.IdeView;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.project.Project;

import club.gclmit.gitfox.model.Constant;

/**
 * @author gclm
 */
public class LicenseTemplateGroupAction extends ActionGroup {

    public LicenseTemplateGroupAction() {
        super("Licenses", null, AllIcons.FileTypes.Text);
        setPopup(true);
    }

    @Override
    public @NotNull ActionUpdateThread getActionUpdateThread() {
        return super.getActionUpdateThread();
    }

    @NotNull
    @Override
    public AnAction @NotNull [] getChildren(@Nullable AnActionEvent e) {
        String[] licenseTemplates = Constant.LICENSE_TEMPLATES;
        if (ArrayUtil.isEmpty(licenseTemplates)) {
            return new AnAction[0];
        }
        AnAction[] actions = new AnAction[licenseTemplates.length];
        for (int i = 0; i < actions.length; i++) {
            actions[i] = new CreateLicenseFileAction(licenseTemplates[i]);
        }
        return actions;
    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        // 获取项目和视图
        Project project = e.getProject();
        IdeView view = LangDataKeys.IDE_VIEW.getData(e.getDataContext());

        // 检查项目和视图是否有效
        boolean hasValidProject = project != null;
        boolean hasValidView = view != null;
        boolean hasNonEmptyDirectories = hasValidView && view.getDirectories().length > 0;

        // 设置呈现器的状态
        Presentation presentation = e.getPresentation();
        boolean enabled = hasValidProject && hasValidView && hasNonEmptyDirectories;
        presentation.setEnabledAndVisible(enabled);

        // 日志记录
        if (!enabled) {
            System.out.println("LicenseTemplateGroup is disabled due to missing project or directories.");
        }
    }
}
