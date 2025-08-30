package ar.com.baden.task;

import ar.com.baden.main.App;

import java.awt.*;
import java.util.concurrent.Callable;

public class FontFamiliesLoader implements Callable<String> {

    @Override
    public String call() throws Exception {
        if (Thread.interrupted()) {
            throw new InterruptedException(Thread.currentThread().getName());
        }
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] families = ge.getAvailableFontFamilyNames();
        return installFonts(families);
    }

    private String installFonts(String[] availableFontFamilyNames) {
        int total = availableFontFamilyNames.length;
        StringBuilder buffer = new StringBuilder("Cargando fuentes...\n");
        for (String name : availableFontFamilyNames) {
            buffer.append("Fuente: ").append(name).append("\n");
            App.availableFontFamilyNames.add(name);
        }
        buffer.append("Total: ").append(total).append(" fuentes.\n");
        return buffer.toString();
    }

}
