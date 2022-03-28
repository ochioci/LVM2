import java.util.ArrayList;
import java.util.UUID;

public class VolumeGroup extends Volume{
    private ArrayList<PhysicalVolume> volumes;
    private ArrayList<LogicalVolume> LVMS = new ArrayList<LogicalVolume>();
    public VolumeGroup (String name, ArrayList<PhysicalVolume> volumes, Server s) {
        super(name, getTotalSize(volumes), s);
        this.volumes = volumes;
    }
    public static int getTotalSize(ArrayList<PhysicalVolume> volumes) {
        int total = 0;
        for (int i = 0; i < volumes.size(); i++) {
            total += volumes.get(i).getSize();
        }
        return total;
    }

    public ArrayList<PhysicalVolume> getPVs () {return volumes;}
    public void addLVM (LogicalVolume LV) {
        this.LVMS.add(LV);
    }


}
