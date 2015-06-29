package org.chrisle.netbeans.plugins.nbc2asm;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import org.netbeans.api.extexecution.ExecutionDescriptor;
import org.netbeans.api.extexecution.ExecutionService;
import org.openide.cookies.OpenCookie;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObject;
import org.openide.util.Exceptions;

public class CommandUtils {
    public static void exec(String tabName, String inputFile, String outputFile) {
        try {
            // Show something as Outputwindow Caption
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec("gcc -S " + inputFile + " -o " + outputFile);

            Callable processCallable = (Callable) () -> process;
            ExecutionDescriptor descriptor = new ExecutionDescriptor().frontWindow(true).controllable(true).showSuspended(true);
            ExecutionService service = ExecutionService.newService(processCallable, descriptor, tabName);

            Future<Integer> task = service.run();

            try {
                if (task.get() == 0) {
                    // TODO: Message if it's done, inside the same output tab.;
//                    InputOutput test = IOProvider.get(tabName).getIO(tabName, null);
//                    test.getOut().println("Done.");
                    
                    File asmFile = new File(outputFile);
                    DataObject.find(FileUtil.toFileObject(asmFile)).getLookup().lookup(OpenCookie.class).open();
                }
            } catch (InterruptedException | ExecutionException ex) {
                Exceptions.printStackTrace(ex);
            }
            
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }
}