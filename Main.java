import java.util.Scanner;
import java.util.ArrayList;
public class Main {
    public static Server myServer = new Server("My Server");
    public static void main (String[] args) {
        menu();
    }

    public static boolean isValidSize(String num) {
        try {
            int n = Integer.parseInt(num);
            if (n > 0) {return true;}
            else {return false;}
        }
        catch (Throwable s) {
            return false;
        }
    }

    public static void menu() {
        Scanner sc = new Scanner(System.in);
        System.out.println("please input your command>");
        String input = sc.nextLine();
        ArrayList<String> args = new ArrayList<String>();
        String command = "";
        if (input.indexOf(" ") > -1) {
            command = input.substring(0, input.indexOf(" "));
            input = input.substring(input.indexOf(" ")+1);
            while (input.indexOf(" ") > -1) {
                args.add(input.substring(0, input.indexOf(" ")));
                input = input.substring(input.indexOf(" ")+1);

            }
            args.add(input);
        } else {
           command = input;
        }


//        System.out.println(command);
//        System.out.println(args);

        if (command.equals("install-drive")) {
            boolean proceed = true;
            if (args.size() != 2 || !isValidSize(args.get(1))) {
                proceed = false;
                System.out.println("Invalid Arguments");
            }
            // for this one, the check for a duplicate name is built into the server method
            if (proceed) {
                boolean result = myServer.installDrive(args.get(0), Integer.parseInt(args.get(1)));
                if (result) {System.out.println("Success!");}
                else {System.out.println(myServer.getError());}
            }
        }

        if (command.equals("list-drives")) {
            ArrayList<Drive> drives = myServer.getDrives();
            for (int i = 0; i < drives.size(); i++) {
                Drive d = drives.get(i);
                System.out.println(d.getName() + " [" + d.getSize() + "]");
            }
        }

        if (command.equals("pvcreate")) {
            boolean proceed = true;
            if (args.size() != 2) {
                proceed = false;
                System.out.println("Invalid Arguments");
            }

            ArrayList<PhysicalVolume> PVs = myServer.getPhysicalVolumes();

            if (proceed) {
                for (int i = 0; i < PVs.size(); i++) {
                    if (PVs.get(i).getName().equals(args.get(0))) {
                        System.out.println("That name is already in use!");
                        proceed = false;
                    }
                }
            }

            if (proceed) {
                boolean result = myServer.createPV(args.get(1), args.get(0));
                if (result) {System.out.println("Success!");}
                else {System.out.println(myServer.getError());}
            }
        }

        if (command.equals("pvlist")) {
            ArrayList<PhysicalVolume> PVs = myServer.getPhysicalVolumes();
            for (int i = 0; i < PVs.size(); i++) {
                PhysicalVolume pv = PVs.get(i);
                String vgName = "";
                if (pv.getAssignedVG() != null) {
                     vgName = pv.getAssignedVG().getName();
                }
                System.out.println(pv.getName() + ":[" + pv.getSize() + "] [" + vgName + "] [" + pv.getUUID().toString() + "]");
            }
        }

        if (command.equals("vgcreate")) {
            boolean proceed = true;
            if (args.size() != 2) {
                proceed = false;
                System.out.println("Invalid Arguments");
            }
            ArrayList<VolumeGroup> VGs = myServer.getVolumeGroups();
            if (proceed) {
                for (int i =0 ; i < VGs.size(); i++) {
                    if (args.get(0).equals(VGs.get(i).getName())) {
                        System.out.println("Name already in use!");
                        proceed = false;
                    }
                }
            }
            if (proceed) {
                boolean result = myServer.createVG(args.get(1), args.get(0));
                if (result) {
                    System.out.println("Success!");
                } else {
                    System.out.println(myServer.getError());
                }
            }
        }

        if (command.equals("vgextend")) {
            boolean proceed = true;
            if (args.size() != 2) {
                proceed = false;
                System.out.println("Invalid Arguments");
            }
            if (proceed) {
                boolean result = myServer.extendVG(args.get(1), args.get(0));
                if (result) {
                    System.out.println("Success!");
                } else {
                    System.out.println(myServer.getError());
                }
            }
        }

        if (command.equals("vglist")) {
            ArrayList<VolumeGroup> VGs = myServer.getVolumeGroups();
            for (int i = 0; i < VGs.size(); i++) {
                VolumeGroup vg = VGs.get(i);
                System.out.print(vg.getName() + ": total:[" + vg.getSize() + "] available:[" + vg.getRemainingSpace() +  "] [");
                ArrayList<PhysicalVolume> PVs = vg.getPVs();
                for (int n = 0; n < PVs.size()-1; n++) {
                    System.out.print(PVs.get(n).getName());
                    System.out.print(",");
                }
                System.out.println(PVs.get(PVs.size()-1).getName() + "] [" + vg.getUUID().toString() + "]");

            }
        }

        if (command.equals("lvcreate")) {
            boolean proceed = true;
            if (args.size() != 3 || !isValidSize(args.get(1))){
                proceed = false;
                System.out.println("Invalid Arguments");
            }
            ArrayList<LogicalVolume> LVs = myServer.getLogicalVolumes();

            if (proceed) {
                for (int i = 0; i < LVs.size(); i++) {
                    if (LVs.get(i).getName().equals(args.get(0))) {
                        System.out.println("That name is already in use!");
                        proceed = false;
                    }
                }
            }
            if (proceed) {
                boolean result = myServer.createLV(args.get(2), Integer.parseInt(args.get(1)), args.get(0));
                if (result) {System.out.println("Success!");}
                else {System.out.println(myServer.getError());}
            }
        }

        if (command.equals("lvlist")) {
//            System.out.println("listing lvs!");
            ArrayList<LogicalVolume> LVs = myServer.getLogicalVolumes();
            for (int i = 0; i < LVs.size(); i++) {
                LogicalVolume lv = LVs.get(i);
                System.out.println(lv.getName() + ": [" + lv.getSize() +  "] [" + lv.getAssignedVG().getName() + "] [" + lv.getUUID().toString() + "]");
            }
        }

        if (command.equals("quit")) {
            System.out.println("Goodbye!");
        } else {
            menu();
        }

    }
}
