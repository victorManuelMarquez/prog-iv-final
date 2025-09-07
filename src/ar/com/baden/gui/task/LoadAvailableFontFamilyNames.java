package ar.com.baden.gui.task;

import ar.com.baden.main.App;

import java.awt.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

public final class LoadAvailableFontFamilyNames implements Callable<String> {

    @Override
    public String call() throws Exception {
        if (Thread.interrupted()) {
            throw new InterruptedException(Thread.currentThread().getName());
        }
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        if (ge == null) {
            throw new ExecutionException(new RuntimeException("Interfáz gráfica sin iniciar."));
        }
        StringBuilder builder = new StringBuilder();
        String[] names = ge.getAvailableFontFamilyNames();
        for (String name : names) {
            App.availableFontFamilyNames.add(name);
            String format = "Fuente: %s" + System.lineSeparator();
            builder.append(String.format(format, name));
        }
        builder.append(String.format("%d fuentes cargadas.%s", names.length, System.lineSeparator()));
        return builder.toString();
    }

}
