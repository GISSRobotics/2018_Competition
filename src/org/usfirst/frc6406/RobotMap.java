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
    
    private static String WRIST_PRESET_STRING = "w0.74:";

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
        driveQuadratureEncoder.setPIDSourceType(PIDSourceType.kDisplacement);
		driveQuadratureEncoder.setReverseDirection(true);
        driveQuadratureEncoder2 = new Encoder(2, 3, false, EncodingType.k4X);
        driveQuadratureEncoder2.setPIDSourceType(PIDSourceType.kDisplacement);
		driveQuadratureEncoder2.setReverseDirection(true);
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
        priorityChooser.addObject("Cross Auto-Line", "crossAutoLine");
        priorityChooser.addObject("Nothing", "nothing");
        positionChooser.addDefault("Left", "left");
        positionChooser.addObject("Center", "center");
        positionChooser.addObject("Right", "right");
        SmartDashboard.putData("PrioritySelect2", priorityChooser);
        SmartDashboard.putData("DriverStationPosition2", positionChooser);
        
        SmartDashboard.putString("PathString", "");
    }


	public static void initAutoDirections() {
		autoDirections.put("LSCL", "d6.1:r1.0:t20:D0.95:t-20:w0.74:p0.5:w0.15:D-1.0:r0.0:t-179.0");
		autoDirections.put("LSCR", "d6.1:t90:d5.2:r1.0:t-100.0:D1.1:t10.0:w0.74:p0.5:w0.15"); 
		autoDirections.put("LSWL", "t-5.0:r0.4:D4.0:t95:D0.2:w0.74:p0.5:w0.15");
		autoDirections.put("LSWR", "D4.0:t180:t180");//CROSSES AUTOLINE ONLY
		autoDirections.put("CSCL", "");
		autoDirections.put("CSCR", "");
		autoDirections.put("CSWL", "D0.6:t-50.0:r0.4:D2.3:t50.0:D0.2:w0.74:p0.5:w0.15:D-1.6:r0.0:t90:D1.5:t-90:D0.3");
		autoDirections.put("CSWR", "D0.6:t35.0:r0.4:D2:t-35.0:D0.1:w0.74:p0.5:w0.15:D-1.6:r0.0:t-90:D1.5:t90:D0.3");   
		autoDirections.put("RSCL", "d6.1:t-90:d5.2:r1.0:t100.0:D1.1:t-10.0:w0.74:p0.5:w0.15");
		autoDirections.put("RSCR", "d6.1:r1.0:t-20:D0.95:t20:w0.74:p0.5:w0.15:D-1.0:r0.0:t179.0");
		autoDirections.put("RSWL", "D4.0:t180:t180");//CROSSES AUTOLINE ONLY
		autoDirections.put("RSWR", "t5.0:r0.4:D4.0:t-95:D0.2:w0.74:p0.5:w0.15");

		autoDirections.put("RALR","D4.0");
		autoDirections.put("RALL","D4.0");
		autoDirections.put("CALR","D4.0:T180.0");
		autoDirections.put("CALL","D4.0:T180.0");
		autoDirections.put("LALR","D4.0");
		autoDirections.put("LALL","D4.0");

		autoDirections.put("RNOR","");
		autoDirections.put("RNOL","");
		autoDirections.put("CNOR","");
		autoDirections.put("CNOL","");
		autoDirections.put("LNOR","");
		autoDirections.put("LNOL","");
		
		autoDirections.put("LSWZ", "d2:t180");
		autoDirections.put("LSWY", "d3:d3:t180");
		autoDirections.put("LSWX", "r0.8:r0.3:r0.75:w0.74:r0.8:r0.3:p0.5");
		
	}
	
	public static String getPathString(String pathString) {
	    String newString = /*WRIST_PRESET_STRING +*/ autoDirections.get(pathString);
	    System.out.println(newString);
	    //String customString = SmartDashboard.getString("PathString", "");
	    //System.out.println(customString);
	    //String finalString = customString.length() < 2 ? newString : customString;
	    //System.out.println(finalString);
	    //SmartDashboard.putString("PathString", finalString);
	    //return finalString;
	    return newString;
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
