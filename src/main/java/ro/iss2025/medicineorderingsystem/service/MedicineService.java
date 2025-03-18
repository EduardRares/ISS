package ro.iss2025.medicineorderingsystem.service;

import ro.iss2025.medicineorderingsystem.domain.Medicine;
import ro.iss2025.medicineorderingsystem.repository.MedicineRepoInterface;
import ro.iss2025.medicineorderingsystem.utils.events.Event;
import ro.iss2025.medicineorderingsystem.utils.events.MedicineEvent;
import ro.iss2025.medicineorderingsystem.utils.events.MedicineEventType;
import ro.iss2025.medicineorderingsystem.utils.observer.Observable;
import ro.iss2025.medicineorderingsystem.utils.observer.Observer;

import java.util.ArrayList;
import java.util.List;

public class MedicineService implements Observable<Event> {
    MedicineRepoInterface medicineRepo;
    private List<Observer<Event>> observersMedicineEvent = new ArrayList<Observer<Event>>();

    public MedicineService(MedicineRepoInterface medicineRepo) {
        this.medicineRepo = medicineRepo;
    }

    public List<Medicine> getAllMedicines() {
        return medicineRepo.findAll();
    }

    public void add(Medicine medicine) {
        medicineRepo.add(medicine);
        notifyObservers(new MedicineEvent(MedicineEventType.ADD, medicine));
    }

    public void update(Medicine medicine) {
        medicineRepo.update(medicine);
        notifyObservers(new MedicineEvent(MedicineEventType.UPDATE, medicine));
    }

    public void delete(Medicine medicine) {
        medicineRepo.delete(medicine);
        notifyObservers(new MedicineEvent(MedicineEventType.DELETE, medicine));
    }

    @Override
    public void addObserver(Observer<Event> observer) {
        observersMedicineEvent.add(observer);
    }

    @Override
    public void removeObserver(Observer<Event> observer) {
        observersMedicineEvent.remove(observer);
    }

    @Override
    public void notifyObservers(Event t) {
        observersMedicineEvent.stream().forEach(x -> x.update(t));
    }
}
