package org.chrisle.netbeans.plugins.nbc2asm;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import org.netbeans.api.extexecution.ExecutionDescriptor;
import org.netbeans.api.extexecution.ExecutionService;
import org.openide.util.Exceptions;

public class CommandUtils {
    public static void exec(String tabName, String inputFile, String outputFile) {
        try {
            // Show something as Outputwindow Caption
            Runtime runtime = Runtime.getRuntime();
            final Process process = runtime.exec("gcc -S " + inputFile + " -o " + outputFile);

            Callable processCallable = (Callable) () -> process;

            ExecutionDescriptor descriptor = new ExecutionDescriptor().frontWindow(true).controllable(true).showSuspended(true);

            ExecutionService service = ExecutionService.newService(processCallable, descriptor, tabName);

            Future task = service.run();
            if (task.isDone()) {
                // TODO: Show success message.;
            }
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }
}