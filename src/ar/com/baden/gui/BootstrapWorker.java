package ar.com.baden.gui;

import ar.com.baden.gui.component.InfoTextPane;
import ar.com.baden.task.LoadAvailableFontFamilyNames;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class BootstrapWorker extends SwingWorker<MainFrame, String> {

    private final InfoTextPane textPane;
    private final Window ancestor;
    private final CountdownTimer timer;

    public BootstrapWorker(InfoTextPane textPane) {
        this.textPane = textPane;
        ancestor = SwingUtilities.getWindowAncestor(textPane);
        timer = new CountdownTimer(15);
        if (ancestor == null) {
            String format = "No se pudo obtener la ventana contenedora del componente: %s";
            String value = textPane.getName() == null ? textPane.getClass().getSimpleName() : textPane.getName();
            throw new RuntimeException(String.format(format, value));
        }
        timer.addPropertyChangeListener("finish", _ -> ancestor.dispose());
    }

    @Override
    protected MainFrame doInBackground() throws Exception {
        PropertyChangeListener[] propertyChangeListeners = getPropertyChangeSupport().getPropertyChangeListeners();
        for (PropertyChangeListener listener : propertyChangeListeners) {
            timer.addPropertyChangeListener(listener);
        }
        firePropertyChange("indeterminate", false, true);
        ancestor.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try (ExecutorService service = Executors.newSingleThreadExecutor()) {
            LoadAvailableFontFamilyNames task = new LoadAvailableFontFamilyNames();
            Future<String> result = service.submit(task);
            publish(result.get());
        }
        Thread.sleep(1000);
        return new MainFrame("Bienvenido");
    }

    @Override
    protected void process(List<String> chunks) {
        chunks.forEach(chunk -> textPane.appendText(chunk, null));
    }

    @Override
    protected void done() {
        try {
            firePropertyChange("indeterminate", true, false);
            ancestor.setCursor(Cursor.getDefaultCursor());
            MainFrame mainFrame = get();
            ancestor.dispose();
            mainFrame.showOnScreen();
        } catch (Exception e) {
            e.printStackTrace(System.err);
            publishError(e);
            timer.start();
        }
    }

    protected String findCause(Exception e) {
        String message = e.getMessage();
        for (Throwable t = e.getCause(); t != null; t = t.getCause()) {
            if (t.getMessage() != null) {
                message = t.getMessage();
            }
        }
        if (!message.endsWith(System.lineSeparator())) {
            message = message.concat(System.lineSeparator());
        }
        return message;
    }

    protected void publishError(Exception e) {
        textPane.appendText(findCause(e), textPane.getErrorStyle());
    }

    public void stop() {
        if (timer.isRunning()) {
            timer.stop();
        }
        if (!isDone()) {
            cancel(true);
        }
    }

}
