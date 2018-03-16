package org.usfirst.frc6406.subsystems;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSink;
import edu.wpi.first.wpilibj.CameraServer;

public class Cameras {

    public static VideoSink server;
    //private static CvSink cvsink1;
    //private static CvSink cvsink2;
    public static UsbCamera camerabill;
    //public static UsbCamera camerajill;

    public Cameras()
    {

        camerabill = CameraServer.getInstance().startAutomaticCapture("jack", 0);
        camerabill.setFPS(15);
        camerabill.setResolution(640, 480);
        //camerajill = CameraServer.getInstance().startAutomaticCapture("jack", 1);
        //camerajill.setFPS(15);
        //camerajill.setResolution(640, 480);
        server = CameraServer.getInstance().getServer();
        //cvsink1 = new CvSink("bill");
        //cvsink1.setSource(camerabill);
        //cvsink1.setEnabled(true);
        //cvsink2 = new CvSink("jill");
        //cvsink2.setSource(camerajill);
        //cvsink2.setEnabled(true);

        server.setSource(camerabill);
    }

}
