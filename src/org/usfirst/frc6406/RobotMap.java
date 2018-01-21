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

// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.PowerDistributionPanel;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.RobotDrive;

import edu.wpi.first.wpilibj.Spark;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.Ultrasonic;


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    public static DoubleSolenoid clawsolenoids;
    public static SpeedController elbowmotor;
    public static AnalogInput sensorsInput;

    public static SpeedController wristmotor;
    public static AnalogInput wristpot;
    public static AnalogInput ultrasonic;
    public static SpeedController driveRightBack;
    public static SpeedController driveLeftBack;
    public static SpeedController driveRightFront;
    public static SpeedController driveLeftFront;
    public static RobotDrive driveRobotDrive;
    public static PowerDistributionPanel sensorsPDP;
    //public static Ultrasonic ultrasonic;


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    @SuppressWarnings("deprecation")
	public static void init() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        //clawopenmotor = new Solenoid(1, 0);
        //LiveWindow.addActuator("claw", "open motor", clawopenmotor);
        
       // = new Solenoid(1, 1);
        //LiveWindow.addActuator("claw", "close motor", clawclosemotor);
        //Warning, the two modules in robot builder are different!
        clawsolenoids = new DoubleSolenoid(1, 0, 1);
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
        
        driveRobotDrive.setSafetyEnabled(true);
        driveRobotDrive.setExpiration(0.1);
        driveRobotDrive.setSensitivity(0.5);
        driveRobotDrive.setMaxOutput(1.0);

        sensorsPDP = new PowerDistributionPanel(1);
        ultrasonic = new AnalogInput(1);
        
        LiveWindow.addSensor("Sensors", "PDP", sensorsPDP);
        LiveWindow.addSensor("Sensors", "Ultrasonic", ultrasonic);
        

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
    }
}
