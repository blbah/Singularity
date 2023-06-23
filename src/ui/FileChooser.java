package ui;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;

public class FileChooser {

    public static File chooseFileWindow(boolean directoriesOnly) {
        File selectedFile = null;
        try {
            final JFrame iFRAME = new JFrame();
            iFRAME.setAlwaysOnTop(true);
            iFRAME.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            iFRAME.setLocationRelativeTo(null);
            iFRAME.requestFocus();

            JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView()
                    .getHomeDirectory());
            if (!directoriesOnly) {
                jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            }
            if (directoriesOnly) {
                jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            }
            int returnValue = jfc.showOpenDialog(iFRAME);
            iFRAME.dispose();
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                selectedFile = jfc.getSelectedFile();
            }
        }
        catch (Exception ignored) {}
        return selectedFile;
    }
}
