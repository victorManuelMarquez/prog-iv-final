package ar.com.baden.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class CountdownTimer implements ActionListener {

    private final int totalSeconds;
    private final Timer timer;
    private final PropertyChangeSupport support;
    private int countdown;

    public CountdownTimer(int totalSeconds) {
        if (totalSeconds < 1) {
            throw new IllegalArgumentException(String.format("%d < 1", totalSeconds));
        }
        this.totalSeconds = totalSeconds;
        countdown = totalSeconds;
        timer = new Timer(1000, this);
        timer.setActionCommand("countdown");
        support = new PropertyChangeSupport(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int old = calculateProgress(countdown);
        int oldCountdown = countdown;
        countdown--;
        support.firePropertyChange("progress", old, calculateProgress(countdown));
        support.firePropertyChange("countdown", oldCountdown, countdown);
        if (countdown == 0) {
            timer.stop();
            support.firePropertyChange("finish", false, true);
        }
    }

    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        support.addPropertyChangeListener(propertyName, listener);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void start() {
        timer.start();
        support.firePropertyChange("start", false, true);
    }

    public void stop() {
        timer.stop();
        support.firePropertyChange("stop", false, true);
    }

    public boolean isRunning() {
        return timer.isRunning();
    }

    protected int calculateProgress(int second) {
        return second * 100 / totalSeconds;
    }

}
