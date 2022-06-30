package club.gclmit.plugin.jetbrains.gitfox.views;

import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class GitfoxServerEditor extends DialogWrapper {
    private JPanel myPanel;
    private JTextField valueField;
    private JTextField keyField;

    public interface Validator {
        boolean isOk(String name, String value);
    }

    public GitfoxServerEditor(String title, String key, String value) {
        super(true);
        setTitle(title);
        keyField.setText(key);
        valueField.setText(value);
        init();
    }

    public String getKey() { return keyField.getText().trim(); }

    public String getValue() {
        return valueField.getText().trim();
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return myPanel;
    }
}
