package club.gclmit.gitfox.action;

import org.jetbrains.annotations.Nullable;

import com.intellij.icons.AllIcons;
import com.intellij.ide.IdeView;
import com.intellij.ide.actions.CreateElementActionBase;
import com.intellij.ide.actions.CreateFileFromTemplateAction;
import com.intellij.ide.fileTemplates.FileTemplate;
import com.intellij.ide.fileTemplates.FileTemplateManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.PsiDirectory;

/**
 * @author gclm
 */
public class CreateLicenseFileAction extends AnAction {
    private final String name;

    public CreateLicenseFileAction(@Nullable String text) {
        super(text, "Create " + text + " license file", AllIcons.FileTypes.Text);
        name = text;
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        IdeView view = LangDataKeys.IDE_VIEW.getData(e.getDataContext());
        if (view == null) {
            return;
        }
        Project project = e.getProject();
        PsiDirectory dir = view.getOrChooseDirectory();
        if (project == null || dir == null) {
            return;
        }
        FileTemplate template = FileTemplateManager.getDefaultInstance().getJ2eeTemplate(name + "");
        try {
            template.setExtension(" ");
            CreateFileFromTemplateAction.createFileFromTemplate("LICENSE", template, dir, null, true);
        } catch (Exception ex) {
            Messages.showErrorDialog(project, CreateElementActionBase.filterMessage(ex.getMessage()), "Error");
        }
    }
}
