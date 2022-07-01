package club.gclmit.plugin.jetbrains.gitfox.views;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.swing.*;

import org.jetbrains.annotations.NotNull;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.ui.AnActionButton;
import com.intellij.ui.DoubleClickListener;
import com.intellij.ui.ToolbarDecorator;

import club.gclmit.chaos.core.utils.StringUtils;
import club.gclmit.plugin.jetbrains.gitfox.config.GitfoxState;
import club.gclmit.plugin.jetbrains.gitfox.model.Gitfox;
import club.gclmit.plugin.jetbrains.gitfox.model.Item;
import cn.hutool.core.collection.CollectionUtil;

/**
 * gitfox 配置视图
 *
 * @author <a href="https://blog.gclmit.club">gclm</a>
 * @since 2022/6/29 13:38
 * @since jdk11
 */
public class GitfoxSettingView {
    private final ItemTable itemTable;
    // 配置
    protected GitfoxState gitfoxState;
    private JPanel mainPanel;
    private JTabbedPane itemPanel;
    private JComboBox<String> languages;
    private JComboBox<String> styles;

    public GitfoxSettingView(GitfoxState state) {
        this.gitfoxState = state;
        itemTable = new ItemTable();

        // init otherSettingPanel
        itemPanel.add(ToolbarDecorator.createDecorator(itemTable).setAddAction(button -> itemTable.addGitfoxServer())
            .setRemoveAction(button -> itemTable.removeSelectedGitfoxServers())
            .setEditAction(button -> itemTable.editGitfoxServer()).setMoveUpAction(button -> itemTable.moveUp())
            .setMoveDownAction(button -> itemTable.moveDown())
            .addExtraAction(new AnActionButton("Reset Default GitfoxServer", AllIcons.Actions.Rollback) {
                @Override
                public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
                    itemTable.resetDefaultGitfoxServers();
                }
            }).createPanel(), BorderLayout.CENTER);
        new DoubleClickListener() {
            @Override
            protected boolean onDoubleClick(@NotNull MouseEvent e) {
                return itemTable.editGitfoxServer();
            }
        }.installOn(itemTable);
    }

    public void reset(GitfoxState gitfoxState) {
        Gitfox gitfox = gitfoxState.getState();
        gitfox.getItems().stream().map(Item::getKey).forEach(styles::addItem);
        if (StringUtils.isNotBlank(gitfox.getStyle())) {
            styles.setSelectedItem(gitfox.getStyle());
        }
        GitfoxState.LANGUAGE_List.forEach(languages::addItem);
        if (StringUtils.isNotBlank(gitfox.getLanguage())) {
            styles.setSelectedItem(gitfox.getLanguage());
        }
        itemTable.reset(gitfox.getItems());
    }

    public GitfoxState getState() {
        Gitfox gitfox = gitfoxState.getState();
        gitfox.setStyle((String)styles.getSelectedItem());
        gitfox.setLanguage((String)languages.getSelectedItem());
        gitfox.setItems(itemTable.getItems());
        return gitfoxState;
    }

    public boolean isModified(GitfoxState state) {
        Gitfox gitfox = state.getState();
        String style = Objects.requireNonNull(styles.getSelectedItem()).toString().trim();
        String language = Objects.requireNonNull(languages.getSelectedItem()).toString().trim();
        List<Item> items = itemTable.getItems();
        List<String> updateStyles = items.stream().map(Item::getKey).collect(Collectors.toList());
        if (!StringUtils.equalsIgnoreCase(gitfox.getStyle(), style)) {
            return true;
        }
        if (!StringUtils.equalsIgnoreCase(gitfox.getLanguage(), language)) {
            return true;
        }

        if (!updateStyles.contains(style)) {
            styles.removeAllItems();
            updateStyles.forEach(styles::addItem);
            styles.setSelectedIndex(0);
            return true;
        }
        return !CollectionUtil.isEqualList(gitfoxState.getState().getItems(), items);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
