package club.gclmit.plugin.jetbrains.gitfox.config;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.intellij.icons.AllIcons;
import com.intellij.ide.IdeView;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.project.Project;

import club.gclmit.plugin.jetbrains.gitfox.model.Constant;

public class LicenseTemplateGroup extends ActionGroup {

    public LicenseTemplateGroup() {
        super("Licenses", null, AllIcons.FileTypes.Text);
        setPopup(true);
    }

    @NotNull
    @Override
    public AnAction[] getChildren(@Nullable AnActionEvent e) {
        AnAction[] actions = new AnAction[Constant.LICENSE_TEMPLATES.length];
        for (int i = 0; i < actions.length; i++) {
            actions[i] = new CreateLicenseFileAction(Constant.LICENSE_TEMPLATES[i]);
        }
        return actions;
    }

    @Override
    public void update(AnActionEvent e) {
        IdeView view = LangDataKeys.IDE_VIEW.getData(e.getDataContext());
        Project project = e.getProject();
        boolean enabled = project != null && view != null && view.getDirectories().length != 0;
        Presentation presentation = e.getPresentation();
        presentation.setEnabledAndVisible(enabled);
    }
}
