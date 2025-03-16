package ro.iss2025.medicineorderingsystem.utils.observer;

import ro.iss2025.medicineorderingsystem.utils.events.Event;

public interface Observable<E extends Event> {
    void addObserver(Observer<E> observer);
    void removeObserver(Observer<E> observer);
    void notifyObservers(E t);
}