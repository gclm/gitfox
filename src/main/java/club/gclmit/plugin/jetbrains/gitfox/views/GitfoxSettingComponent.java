package club.gclmit.plugin.jetbrains.gitfox.views;

import com.intellij.ui.components.JBCheckBox;
import com.intellij.util.ui.FormBuilder;

import javax.swing.*;

/**
 * TODO
 *
 * @author <a href="https://blog.gclmit.club">gclm</a>
 * @since 2022/6/29 16:03
 * @since jdk11
 */
public class GitfoxSettingComponent {

    private final JPanel mainPanel;
    private final JBCheckBox showBranchStatus = new JBCheckBox("显示版本号");
    private final JBCheckBox useChineseStatus = new JBCheckBox("使用中文");

    public GitfoxSettingComponent() {
        mainPanel = FormBuilder.createFormBuilder()
                .addComponent(showBranchStatus, 1)
                .addComponent(useChineseStatus, 1)
                .addComponentFillVertically(new JPanel(), 0)
                .getPanel();
    }

    public JPanel getPanel() {
        return mainPanel;
    }

    public boolean getShowBranchStatus() {
        return showBranchStatus.isSelected();
    }

    public void setShowBranchStatus(boolean newStatus) {
        showBranchStatus.setSelected(newStatus);
    }

    public boolean getUseChineseStatus() {
        return useChineseStatus.isSelected();
    }

    public void setUseChineseStatus(boolean newStatus) {
        useChineseStatus.setSelected(newStatus);
    }

}
