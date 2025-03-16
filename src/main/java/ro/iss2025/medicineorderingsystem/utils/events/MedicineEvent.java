package ro.iss2025.medicineorderingsystem.utils.events;

import ro.iss2025.medicineorderingsystem.domain.Medicine;

public class MedicineEvent implements Event{
    private MedicineEventType type;
    private Medicine data,oldData;

    public MedicineEvent(MedicineEventType type, Medicine data){
        this.type = type;
        this.data = data;
    }

    public MedicineEvent (MedicineEventType type, Medicine data, Medicine oldData){
        this.type = type;
        this.data = data;
        this.oldData = oldData;
    }

    public MedicineEventType getType() {
        return type;
    }

    public Medicine getData() {
        return data;
    }

    public Medicine getOldData() {
        return oldData;
    }
}
