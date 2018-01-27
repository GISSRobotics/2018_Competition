// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc6406.subsystems;

import org.usfirst.frc6406.Robot;
import org.usfirst.frc6406.RobotMap;
import org.usfirst.frc6406.commands.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.PowerDistributionPanel;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS


/**
 *
 */
public class Sensors extends Subsystem {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    private final PowerDistributionPanel pDP = RobotMap.sensorsPDP;
    private final AnalogInput ultrasonic = RobotMap.sensorsUltrasonic;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    private int ultrasonicTickCycle;
	int[] distanceValues = new int[5];

    @Override
    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    void computeUltrasonicValues() {
    	if (ultrasonicTickCycle <= 0) {resetDistanceValues();}
    	distanceValues[ultrasonicTickCycle] = Robot.sensors.ultrasonic.getValue() -229;
    	if (ultrasonicTickCycle == 5) {updateUltrasonicValues();}
    	ultrasonicTickCycle++;
    }
    void resetDistanceValues() {
    	for (int i=0; i<5;i++) {distanceValues[i] = 0;}
    }
    void updateUltrasonicValues() {
    	ultrasonicTickCycle = 2;
    	double finalValue = 0;
    	for (int i=0; i < 5;i++) {finalValue += distanceValues[i];}
    	finalValue = (finalValue/ultrasonicTickCycle);
    	SmartDashboard.putNumber("Ultrasonic", finalValue);
    	ultrasonicTickCycle = 0;
    	}

    @Override
    public void periodic() {
        // Put code here to be run every loop
    	int distanceComputed = (Robot.sensors.ultrasonic.getValue() - 229);
    	SmartDashboard.putNumber ("Ultrasonic", distanceComputed);
    	//computeUltrasonicValues();
    	//SmartDashboard.putNumber("ultrasonicTickCycle", ultrasonicTickCycle);
    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

}

