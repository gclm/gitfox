package club.gclmit.plugin.jetbrain.gitfox.views;

import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class SettingEditorView extends DialogWrapper {
    private JPanel myPanel;
    private JTextField keyFiled;
    private JTextField valueField;

    public interface Validator {
        boolean isOK(String name, String value);
    }

    public SettingEditorView(String title, String key, String value) {
        super(true);
        setTitle(title);
        keyFiled.setText(key);
        valueField.setText(value);
        init();
    }

    public String getKey() { return keyFiled.getText().trim(); }

    public String getValue() {
        return valueField.getText().trim();
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return myPanel;
    }
}
