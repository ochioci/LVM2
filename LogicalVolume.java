import java.util.ArrayList;
import java.util.UUID;

public class LogicalVolume extends Volume{
    private VolumeGroup assignedVG;
    public LogicalVolume (String name, int size, VolumeGroup vg) {
        super(name, size);
        this.assignedVG = vg;
    }
}
