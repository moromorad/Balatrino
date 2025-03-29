package ui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import model.Event;
import model.EventLog;

// Prints the event log when a frame that has this added as its WindowListener
public class GameListener extends WindowAdapter {
    @Override
    // EFFECTS : prints the event log when the frame is closed then ends the application
    public void windowClosing(WindowEvent e) {
        EventLog eventLog = EventLog.getInstance();
        for (Event event : eventLog) {
            System.out.println(event.toString());
            System.out.println("");
        }
        System.exit(0);
    }
}
