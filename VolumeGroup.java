import java.util.ArrayList;
import java.util.UUID;

public class VolumeGroup extends Volume{
    private ArrayList<PhysicalVolume> volumes = new ArrayList<PhysicalVolume>();
    private ArrayList<LogicalVolume> LVMS = new ArrayList<LogicalVolume>();
    public VolumeGroup (String name, PhysicalVolume pv, Server s) {
        super(name, pv.getSize(), s);
        this.volumes.add(pv);
    }
    public int getSize() {
        int total = 0;
        for (int i = 0; i < volumes.size(); i++) {
            total += volumes.get(i).getSize();
        }
        return total;
    }
    public void extend (PhysicalVolume pv) {
        this.volumes.add(pv);
    }
    public ArrayList<PhysicalVolume> getPVs () {return volumes;}
    public void addLVM (LogicalVolume LV) {
        this.LVMS.add(LV);
    }
    public int getRemainingSpace() {
        int total = 0;
        for (int i = 0; i < volumes.size(); i++) {
            total+=volumes.get(i).getSize();
        }
        for (int i = 0; i < LVMS.size(); i++) {
            total -= LVMS.get(i).getSize();
        }
        return total;
    }


}
