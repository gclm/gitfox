package club.gclmit.plugin.jetbrains.gitfox.config;

import com.intellij.icons.AllIcons;
import com.intellij.ide.fileTemplates.FileTemplateGroupDescriptor;
import com.intellij.ide.fileTemplates.FileTemplateGroupDescriptorFactory;

import club.gclmit.plugin.jetbrains.gitfox.model.Constant;

/**
 * @author gclm
 */
public class LicenseTemplateGroupDescriptorFactory implements FileTemplateGroupDescriptorFactory {

    @Override
    public FileTemplateGroupDescriptor getFileTemplatesDescriptor() {
        FileTemplateGroupDescriptor group = new FileTemplateGroupDescriptor("Licenses", AllIcons.FileTypes.Text);
        for (String name : Constant.LICENSE_TEMPLATES) {
            group.addTemplate(name);
        }
        return group;
    }
}
