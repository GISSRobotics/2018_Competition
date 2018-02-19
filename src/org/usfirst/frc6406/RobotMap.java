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
            ahrs = new AHRS(SPI.Port.kMXP);
            ahrs.reset();
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
        initPIDSelectors();
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
		autoDirections.put("LSCL", "d8.22:t90:r1:d0:p0.37");// This is our example case (Drive 1 meter:Begin raise 7 feet:Drive
														// 1 meter:Turn 90 degrees:Place(Dump))
		autoDirections.put("LSCR", "d5.2:t-90:d5.5:t90:d2.2:r5.0:t90:r1:t90:d2.2:t-90:d5.5:t90:d5.2:t180"); // has been inverted
		autoDirections.put("LSWL", "d4.3:t90:r0.3:d0:p0.37");
		autoDirections.put("LSWR", "d6:t90:d5.3:t90:r0.3:d0:p0.37");   // r value = percent height, 0.014 = one inch
		autoDirections.put("CSCL", "d.3:t-50:d4:t50:d4.7:t90:r1:d0:p0.37");
		autoDirections.put("CSCR", "d.3:t50:D4:t-50:d4.7:t-90:r1:d0:p0.37");
		autoDirections.put("CSWL", "d.3:t-22:d3.4:t22:r0.3:d0:p0.37");
		autoDirections.put("CSWR", "d.3:t24:d3.5:t-24:r0.3:d0:p0.37");   //!!!!!! p NEEDS to be 0.37 !!!!!!!
		autoDirections.put("RSCL", "d2.5:t90:d1.5:t90:d2.5:t90:r1:d1.5:t90:p0.37");
		autoDirections.put("RSCR", "d8.22:t-90:r1:d0:P0.37");
		autoDirections.put("RSWL", "d6:t-90:d5.3:t-90:r0.3:d0:p0.37");
		autoDirections.put("RSWR", "d4.3:t-90:r0.3:d0:p0.37");

		autoDirections.put("LSWZ", "d6:t180");
		autoDirections.put("LSWY", "d3:d3:t180");
		autoDirections.put("LSWX", "r0.8:r0.3:r0.75:r0.8:r0.3:p0.37");
		
	}
	
	public static void initPIDSelectors() {
		// multi floor
		SmartDashboard.putNumber("P Drive", 0.027);
		SmartDashboard.putNumber("D Drive", 0.13);
		SmartDashboard.putNumber("P DriveTurn", 0.3);
		SmartDashboard.putNumber("D DriveTurn", 0.3);
		// carpet people
//		SmartDashboard.putNumber("p_turn",  0.2);
//		SmartDashboard.putNumber("i_turn", 0.0);
//		SmartDashboard.putNumber("d_turn",  0.2);
		
		// multi floor
		SmartDashboard.putNumber("p_turn",  0.075);
		SmartDashboard.putNumber("i_turn", 0.0);
		SmartDashboard.putNumber("d_turn",  0.155);
	}
}
