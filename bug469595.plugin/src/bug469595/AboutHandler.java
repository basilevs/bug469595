 
package bug469595;

import java.io.IOException;

import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

import com.google.common.io.Closer;

public class AboutHandler {
	private final MessageConsole console = new MessageConsole("AboutHandler messages", null);
	private final Closer closer = Closer.create();
	
	@Inject
	public AboutHandler(IConsoleManager consoleManager) {
		IConsole[] consoles = new IConsole[] {console};
		consoleManager.addConsoles(consoles);
		closer.register(() -> consoleManager.removeConsoles(consoles));
	}
	
	@PreDestroy
	public void dispose() throws IOException {
		closer.close();
	}
	
	@Execute
	public void execute() throws IOException {
		try(MessageConsoleStream stream = console.newMessageStream()) {
			stream.println("About handler called");
		}
	}
		
}