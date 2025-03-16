package ro.iss2025.medicineorderingsystem.utils.observer;

import ro.iss2025.medicineorderingsystem.utils.events.Event;

public interface Observer<E extends Event> {
    void update(E event);
}
