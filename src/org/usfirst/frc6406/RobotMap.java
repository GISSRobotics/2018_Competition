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

// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.DoubleSolenoid;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.VictorSP;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	
	
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public static DoubleSolenoid clawsolenoids;
    public static SpeedController wristmotor;
    public static AnalogInput wristpot;
    public static SpeedController driveRightBack;
    public static SpeedController driveLeftBack;
    public static SpeedController driveRightFront;
    public static SpeedController driveLeftFront;
    public static RobotDrive driveRobotDrive;
    public static Encoder driveQuadratureEncoder;
    public static Encoder driveQuadratureEncoder2;
    public static PowerDistributionPanel sensorsPDP;
    public static AnalogInput sensorsUltrasonic;

    public static SpeedController climbermotor;
    public static WPI_TalonSRX lifttruckMotor;
    public static WPI_TalonSRX lifttelescopeMotor;
    
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public static AHRS ahrs;
    
    public static String pathString;
    public static Map<String,String> autoDirections = new HashMap<String,String>();
    
    @SuppressWarnings("deprecation")
	public static void init() {
    	 try {
             /* Communicate w/navX-MXP via the MXP SPI Bus.                                     */
             /* Alternatively:  I2C.Port.kMXP, SerialPort.Port.kMXP or SerialPort.Port.kUSB     */
             /* See http://navx-mxp.kauailabs.com/guidance/selecting-an-interface/ for details. */
             ahrs = new AHRS(SPI.Port.kMXP); 
         } catch (RuntimeException ex ) {
             DriverStation.reportError("Error instantiating navX-MXP:  " + ex.getMessage(), true);
   }
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS

        clawsolenoids = new DoubleSolenoid(0, 0, 1);
        LiveWindow.addActuator("claw", "solenoids", clawsolenoids);
        
        wristmotor = new VictorSP(5);
        LiveWindow.addActuator("wrist", "motor", (VictorSP) wristmotor);
        wristmotor.setInverted(false);
        wristpot = new AnalogInput(0);
        LiveWindow.addSensor("wrist", "pot", wristpot);
        
        driveRightBack = new Spark(3);
        LiveWindow.addActuator("Drive", "RightBack", (Spark) driveRightBack);
        driveRightBack.setInverted(false);
        driveLeftBack = new Spark(1);
        LiveWindow.addActuator("Drive", "LeftBack", (Spark) driveLeftBack);
        driveLeftBack.setInverted(false);
        driveRightFront = new Spark(2);
        LiveWindow.addActuator("Drive", "RightFront", (Spark) driveRightFront);
        driveRightFront.setInverted(false);
        driveLeftFront = new Spark(0);
        LiveWindow.addActuator("Drive", "LeftFront", (Spark) driveLeftFront);
        driveLeftFront.setInverted(false);
        driveRobotDrive = new RobotDrive(driveLeftFront, driveLeftBack,
              driveRightFront, driveRightBack);
        
       driveRobotDrive.setSafetyEnabled(false);
  
        driveQuadratureEncoder = new Encoder(0, 1, false, EncodingType.k4X);
        LiveWindow.addSensor("Drive", "Quadrature Encoder", driveQuadratureEncoder);
        driveQuadratureEncoder.setDistancePerPulse(1.0);
        driveQuadratureEncoder.setPIDSourceType(PIDSourceType.kRate);
        driveQuadratureEncoder2 = new Encoder(2, 3, false, EncodingType.k4X);
        LiveWindow.addSensor("Drive", "Quadrature Encoder 2", driveQuadratureEncoder2);
        driveQuadratureEncoder2.setDistancePerPulse(1.0);
        driveQuadratureEncoder2.setPIDSourceType(PIDSourceType.kRate);
        sensorsPDP = new PowerDistributionPanel(0);
        LiveWindow.addSensor("Sensors", "PDP", sensorsPDP);
        
        sensorsUltrasonic = new AnalogInput(1);
        LiveWindow.addSensor("Sensors", "Ultrasonic", sensorsUltrasonic);
        


         sensorsPDP = new PowerDistributionPanel(0);
         LiveWindow.addSensor("Sensors", "PDP", sensorsPDP);
         
         sensorsUltrasonic = new AnalogInput(1);
         LiveWindow.addSensor("Sensors", "Ultrasonic", sensorsUltrasonic);
         
         climbermotor = new VictorSP(5);
         LiveWindow.addActuator("Climber", "motor", (VictorSP) climbermotor);
         climbermotor.setInverted(false);
         lifttruckMotor = new WPI_TalonSRX(3);
         
         
         lifttelescopeMotor = new WPI_TalonSRX(1);
         
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
    }
    
    public void initAutoDirections() {
    	autoDirections.put("LSCL", "D8.22:T90:D0:P");//This is our example case (Drive 1 meter:Begin raise 7 feet:Drive 1 meter:Turn 90 degrees:Place(Dump))
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
    }
}

