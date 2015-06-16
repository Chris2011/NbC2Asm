package org.chrisle.netbeans.plugins.nbc2asm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import org.openide.loaders.DataObject;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.NbBundle.Messages;

@ActionID(
        category = "Tools",
        id = "org.chrisle.netbeans.plugins.nbc2asm.ToAsmAction"
)
@ActionRegistration(
        displayName = "#CTL_ToAsmAction"
)
@ActionReferences({
    @ActionReference(path = "Loaders/text/x-c/Actions", position = 900),
    @ActionReference(path = "Loaders/text/x-c++/Actions", position = 900),
    @ActionReference(path = "Editors/text/x-c/Popup", position = 550),
    @ActionReference(path = "Editors/text/x-c++/Popup", position = 550),
})
@Messages("CTL_ToAsmAction=Compile File to asm")
public final class ToAsmAction implements ActionListener {

    private final DataObject _context;

    public ToAsmAction(DataObject context) {
        this._context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        FileObject file = this._context.getPrimaryFile();

        String fileName = file.getName();
        String fileWithExt = file.getNameExt();
        String fileWithPath = FileUtil.toFile(file).getAbsolutePath();
        String folder = fileWithPath.substring(0, fileWithPath.lastIndexOf(File.separator));
        String outputFile = folder + "\\" + fileName + ".asm";

        CommandUtils.exec("Compile " + fileWithExt + " to " + fileName + ".asm", fileWithPath, outputFile);
    }
}
