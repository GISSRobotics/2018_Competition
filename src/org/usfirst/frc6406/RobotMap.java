// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

package org.usfirst.frc6406;

import java.util.HashMap;
import java.util.Map;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Solenoid;
// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.DoubleSolenoid;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
//import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.VictorSP;

// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {

	// public static Solenoid clawCLaw_solenoid;
	// public static SpeedController wristmotor;
	// public static AnalogInput wristpot;
	// public static PowerDistributionPanel sensorsPDP;
	// public static AnalogInput sensorsUltrasonic;
	// public static SpeedController climbermotor;
	// public static WPI_TalonSRX lifttruckMotor;
	// public static WPI_TalonSRX lifttelescopeMotor;

	public static SpeedController driveRightBack;
	public static SpeedController driveLeftBack;
	public static SpeedController driveRightFront;
	public static SpeedController driveLeftFront;
	public static RobotDrive driveRobotDrive;

	// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
	public static AHRS ahrs;
	public static Encoder driveQuadratureEncoder;
	public static Encoder driveQuadratureEncoder2;

	public static Map<String, String> autoDirections = new HashMap<String, String>();

	public static SendableChooser<String> priorityChooser = new SendableChooser<>();
	public static SendableChooser<String> positionChooser = new SendableChooser<>();

	private double kDDrive;
    private double kPDrive;
    private double kDTurn;
    private double kPTurn;

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
			ahrs.reset();
		} catch (RuntimeException ex) {
			DriverStation.reportError("Error instantiating navX-MXP:  " + ex.getMessage(), true);
		}
		// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS

		// clawCLaw_solenoid = new Solenoid(1, 0);
		// LiveWindow.addActuator("claw", "CLaw_solenoid", clawCLaw_solenoid);
		//
		// wristmotor = new VictorSP(10);
		// LiveWindow.addActuator("wrist", "motor", (VictorSP) wristmotor);
		// wristmotor.setInverted(false);
		// wristpot = new AnalogInput(0);
		// LiveWindow.addSensor("wrist", "pot", wristpot);

		// sensorsPDP = new PowerDistributionPanel(0);
		// LiveWindow.addSensor("Sensors", "PDP", sensorsPDP);
		//
		// sensorsUltrasonic = new AnalogInput(1);
		// LiveWindow.addSensor("Sensors", "Ultrasonic", sensorsUltrasonic);
		//
		// climbermotor = new VictorSP(5);
		// LiveWindow.addActuator("Climber", "motor", (VictorSP) climbermotor);
		// climbermotor.setInverted(false);
		// lifttruckMotor = new WPI_TalonSRX(3);
		//
		// lifttelescopeMotor = new WPI_TalonSRX(1);

		driveRightBack = new Spark(3);
		driveRightBack.setInverted(true);
		driveLeftBack = new Spark(1);
		driveLeftBack.setInverted(true);
		driveRightFront = new Spark(2);
		driveRightFront.setInverted(true);
		driveLeftFront = new Spark(0);
		driveLeftFront.setInverted(true);
		driveRobotDrive = new RobotDrive(driveLeftFront, driveLeftBack, driveRightFront, driveRightBack);

		driveRobotDrive.setSafetyEnabled(false);
		driveRobotDrive.setExpiration(0.1);
		driveRobotDrive.setSensitivity(0.5);
		driveRobotDrive.setMaxOutput(1.0);

		// sensorsUltrasonic = new AnalogInput(1);
		// LiveWindow.addSensor("Sensors", "Ultrasonic", sensorsUltrasonic);

		//lifttelescopeMotor = new WPI_TalonSRX(1);

		// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
		driveQuadratureEncoder = new Encoder(0, 1, false, EncodingType.k4X);

		driveQuadratureEncoder.setDistancePerPulse(1.0);
		driveQuadratureEncoder.setPIDSourceType(PIDSourceType.kRate);
		driveQuadratureEncoder2 = new Encoder(2, 3, false, EncodingType.k4X);

		driveQuadratureEncoder2.setDistancePerPulse(1.0);
		driveQuadratureEncoder2.setPIDSourceType(PIDSourceType.kRate);
		driveQuadratureEncoder2.setSamplesToAverage(50);

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
		autoDirections.put("LSCL", "d8.22:t90:d0:P");// This is our example case (Drive 1 meter:Begin raise 7 feet:Drive
														// 1 meter:Turn 90 degrees:Place(Dump))
		autoDirections.put("LSCR", "d5.2:t-90:d5.5:t90:d2.2:r5.0:t90:r1:t90:d2.2:t-90:d5.5:t90:d5.2:t180"); // has been inverted
		autoDirections.put("LSWL", "d4.3:t90:d0:P");
		autoDirections.put("LSWR", "d6:t90:d5.3:t90:d0:P");
		autoDirections.put("CSCL", "d.3:t-50:d4:t50:d4.7:t90:d0:P");
		autoDirections.put("CSCR", "d.3:t50:D4:t-50:d4.7:t-90:d0:P");
		autoDirections.put("CSWL", "d.3:t-22:d3.4:t22:d0:P");
		autoDirections.put("CSWR", "d.3:t24:d3.5:t-24:d0:P");
		autoDirections.put("RSCL", "d2.5:t90:d1.5:t90:d2.5:t90:d1.5:t90");
		autoDirections.put("RSCR", "d8.22:t-90:d0:P");
		autoDirections.put("RSWL", "d6:t-90:d5.3:t-90:d0:P");
		autoDirections.put("RSWR", "d4.3:t-90:d0:P");
		//autoDirections.put("LSWZ", "D6:T45:D0.5:T45:D0.5:T45:D2:T45:D6");// THIS IS TESTING PATH Should be default
																			// settings

		autoDirections.put("LSWZ", "d6:t180");
		autoDirections.put("LSWY", "d3:d3:t180");
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
