package org.usfirst.frc6406;

import java.util.HashMap;
import java.util.Map;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    public static Solenoid clawSolenoid = null;
    public static VictorSPX wristMotor = null;
    public static AnalogInput wristPot;
    public static PowerDistributionPanel PDP = null;
    public static AnalogInput ultrasonic;
    public static VictorSPX winchMotor = null;
    public static WPI_TalonSRX liftTruckMotor = null;
    public static WPI_TalonSRX liftTelescopeMotor = null;
    public static SpeedController driveRightBack;
    public static SpeedController driveLeftBack;
    public static SpeedController driveRightFront;
    public static SpeedController driveLeftFront;
    public static RobotDrive driveRobotDrive;

    public static AHRS ahrs;
    public static Encoder driveQuadratureEncoder;
    public static Encoder driveQuadratureEncoder2;

    public static Map<String, String> autoDirections = new HashMap<String, String>();

    public static SendableChooser<String> priorityChooser = new SendableChooser<>();
    public static SendableChooser<String> positionChooser = new SendableChooser<>();

    @SuppressWarnings("deprecation")
    public static void init() {
        try {
            /* Communicate w/navX-MXP via the MXP SPI Bus. */
            /* Alternatively: I2C.Port.kMXP, SerialPort.Port.kMXP or SerialPort.Port.kUSB */
            /*
             * See http://navx-mxp.kauailabs.com/guidance/selecting-an-interface/ for
             * details.
             */
            ahrs = new AHRS(SPI.Port.kMXP);
        } catch (RuntimeException ex) {
            DriverStation.reportError("Error instantiating navX-MXP:  " + ex.getMessage(), true);
        }

        // Non-CAN devices
        driveRightBack = new Spark(3);
        driveRightBack.setInverted(false);
        driveLeftBack = new Spark(1);
        driveLeftBack.setInverted(false);
        driveRightFront = new Spark(2);
        driveRightFront.setInverted(false);
        driveLeftFront = new Spark(0);
        driveLeftFront.setInverted(false);
        driveRobotDrive = new RobotDrive(driveLeftFront, driveLeftBack, driveRightFront, driveRightBack);
        driveRobotDrive.setSafetyEnabled(false);
        driveRobotDrive.setExpiration(0.1);
        driveRobotDrive.setMaxOutput(1.0);

        driveQuadratureEncoder = new Encoder(0, 1, false, EncodingType.k4X);
        driveQuadratureEncoder.setDistancePerPulse(1.0);
        driveQuadratureEncoder.setPIDSourceType(PIDSourceType.kRate);
        driveQuadratureEncoder2 = new Encoder(2, 3, false, EncodingType.k4X);
        driveQuadratureEncoder2.setDistancePerPulse(1.0);
        driveQuadratureEncoder2.setPIDSourceType(PIDSourceType.kRate);
        ultrasonic = new AnalogInput(1);
        wristPot = new AnalogInput(0);
        
        // CAN devices
        // Null if not detected
        // This might not work...
        try {
        	clawSolenoid = new Solenoid(0, 0);
        	Robot.Log("Gripper solenoid initialized: " + (clawSolenoid != null), 1);
        } catch (Error e) {
        	clawSolenoid = null;
        	Robot.Log("Gripper solenoid not detected.", 1);
        }
        try {
        	winchMotor = new VictorSPX(0);
        } catch (Exception e) {
        	winchMotor = null;
        	Robot.Log("Winch motor not detected.", 1);
        }
        try {
        	wristMotor = new VictorSPX(1);
        } catch (Exception e) {
        	wristMotor = null;
        	Robot.Log("Wrist motor not detected.", 1);
        }
        try {
        	liftTelescopeMotor = new WPI_TalonSRX(0);
        } catch (Exception e) {
        	liftTelescopeMotor = null;
        	Robot.Log("Telescope motor not detected.", 1);
        }
        try {
        	liftTruckMotor = new WPI_TalonSRX(1);
        } catch (Exception e) {
        	liftTruckMotor = null;
        	Robot.Log("Truck motor not detected.", 1);
        }
        try {
        	PDP = new PowerDistributionPanel(0);
        } catch (Exception e) {
        	PDP = null;
        	Robot.Log("PDP not detected.", 1);
        }

        initSelectionButtons();
        initAutoDirections();
    }

    public static void initSelectionButtons() {
        priorityChooser.addDefault("Switch", "switch");
        priorityChooser.addObject("Scale", "scale");
        positionChooser.addDefault("Left", "left");
        positionChooser.addObject("Center", "center");
        positionChooser.addObject("Right", "right");
        SmartDashboard.putData("PrioritySelect", priorityChooser);
        SmartDashboard.putData("DriverStationPosition", positionChooser);
    }

    public static void initAutoDirections() {
        autoDirections.put("LSCL", "D8.22:T90:D0:P");// This is our example case (Drive 1 meter:Begin raise 7 feet:Drive
                                                     // 1 meter:Turn 90 degrees:Place(Dump))
        autoDirections.put("LSCR", "D6:T90:D6:T-90:D2.2:T-90:D0:P");
        autoDirections.put("LSWL", "D4.3:T90:D0:P");
        autoDirections.put("LSWR", "D6:T90:D5.3:T90:D0:P");
        autoDirections.put("CSCL", "D.3:T-50:D4:T50:D4.7:T90:D0:P");
        autoDirections.put("CSCR", "D.3:T50:D4:T-50:D4.7:T-90:D0:P");
        autoDirections.put("CSWL", "D.3:T-22:D3.4:T22:D0:P");
        autoDirections.put("CSWR", "D.3:T24:D3.5:T-24:D0:P");
        autoDirections.put("RSCL", "D6:T-90:D6:T90:D2.2:T90:D0:P");
        autoDirections.put("RSCR", "D8.22:T-90:D0:P");
        autoDirections.put("RSWL", "D6:T-90:D5.3:T-90:D0:P");
        autoDirections.put("RSWR", "D4.3:T-90:D0:P");
        autoDirections.put("LSWZ", "D6:T45:D0.5:T45:D0.5:T45:D2:T45:D6");// THIS IS TESTING PATH Should be default
                                                                         // settings

    }
}
