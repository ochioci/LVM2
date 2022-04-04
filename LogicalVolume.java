import java.util.ArrayList;
import java.util.UUID;

public class LogicalVolume extends Volume{
    private VolumeGroup assignedVG;
    public LogicalVolume (String name, int size, VolumeGroup vg, Server s) {
        super(name, size, s);
        this.assignedVG = vg;
        assignedVG.addLVM(this);
    }
    public VolumeGroup getAssignedVG () {
        return assignedVG;
    }
}
