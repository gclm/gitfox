package club.gclmit.gitfox.views;

import club.gclmit.gitfox.config.GitfoxState;
import club.gclmit.gitfox.model.Gitfox;
import club.gclmit.gitfox.model.Item;
import cn.hutool.extra.cglib.CglibUtil;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.ui.DoubleClickListener;
import com.intellij.ui.ToolbarDecorator;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * gitfox 配置视图
 *
 * @author <a href="https://blog.gclmit.club">gclm</a>
 * @since 2022/6/29 13:38
 * @since jdk11
 */
public class GitfoxSettingPanel {
    private JPanel mainPanel;
    private JTabbedPane itemPanel;
    private JComboBox<String> languages;
    private JComboBox<String> styles;

    /**
     * ItemTable
     */
    protected ItemTable itemTable;
    protected Gitfox gitfox = new Gitfox();

    public GitfoxSettingPanel() {
        itemTable = new ItemTable();

        // init otherSettingPanel
        itemPanel.add(ToolbarDecorator.createDecorator(itemTable).setAddAction(button -> itemTable.addGitfoxServer())
                .setRemoveAction(button -> itemTable.removeSelectedGitfoxServers())
                .setEditAction(button -> itemTable.editGitfoxServer()).setMoveUpAction(button -> itemTable.moveUp())
                .setMoveDownAction(button -> itemTable.moveDown()).addExtraAction(new AnAction(() -> "Reset Default CommitServer", AllIcons.Actions.Rollback) {
                    @Override
                    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
                        itemTable.reset(GitfoxState.loadDefaultSettings().getItems());
                    }
                }).createPanel(), BorderLayout.CENTER);
        new DoubleClickListener() {
            @Override
            protected boolean onDoubleClick(@NotNull MouseEvent e) {
                return itemTable.editGitfoxServer();
            }
        }.installOn(itemTable);
    }

    public void reset(Gitfox data) {
        gitfox = CglibUtil.copy(data, Gitfox.class);
        if (itemTable != null) {
            itemTable.reset(gitfox.getItems());
        }
        gitfox.getItems().stream().map(Item::getKey).forEach(styles::addItem);
        GitfoxState.LANGUAGE_List.forEach(languages::addItem);
        styles.setSelectedItem(gitfox.getStyle());
        languages.setSelectedItem(gitfox.getLanguage());
    }

    public void fixStyle(List<String> updateStyles, String style) {
        styles.removeAllItems();
        updateStyles.forEach(styles::addItem);
        if (updateStyles.contains(style)) {
            styles.setSelectedItem(style);
        } else {
            styles.setSelectedIndex(0);
        }
    }

    public Gitfox getGitfox() {
        gitfox.setStyle((String) styles.getSelectedItem());
        gitfox.setLanguage((String) languages.getSelectedItem());
        gitfox.setItems(itemTable.getItems());
        return gitfox;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
