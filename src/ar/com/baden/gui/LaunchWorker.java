package ar.com.baden.gui;

import ar.com.baden.gui.component.InfoPane;
import ar.com.baden.task.FontFamiliesLoader;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LaunchWorker extends SwingWorker<Void, String> {

    private final InfoPane infoPane;
    private final Window ancestor;

    public LaunchWorker(InfoPane infoPane) {
        this.infoPane = infoPane;
        ancestor = SwingUtilities.getWindowAncestor(infoPane);
        if (ancestor == null) {
            throw new IllegalArgumentException("El componente no está contenido en una ventana.");
        }
    }

    @Override
    protected Void doInBackground() throws Exception {
        ancestor.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try (ExecutorService service = Executors.newSingleThreadExecutor()) {
            publish(service.submit(new FontFamiliesLoader()).get());
        }
        setProgress(100);
        return null;
    }

    @Override
    protected void process(List<String> chunks) {
        chunks.forEach(str -> infoPane.appendText(str, null));
    }

    @Override
    protected void done() {
        try {
            ancestor.setCursor(Cursor.getDefaultCursor());
            Object ignore = get();
            ancestor.setVisible(false);
            ancestor.dispose();
            MainFrame.createAndShow();
        } catch (CancellationException e) {
            System.out.println("cancelado por el usuario.");
        } catch (Exception e) {
            e.printStackTrace(System.err);
            infoPane.appendText(findCause(e), infoPane.getErrorStyle());
        }
    }

    private String findCause(Exception e) {
        String message = e.getMessage();
        if (message == null) {
            message = e.getClass().getSimpleName();
        }
        for (Throwable cause = e.getCause(); cause != null; cause = cause.getCause()) {
            if (cause.getMessage() != null) {
                message = cause.getMessage();
            }
        }
        if (!message.endsWith(System.lineSeparator())) {
            message = message.concat(System.lineSeparator());
        }
        return message;
    }

}
