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

import org.usfirst.frc6406.RobotMap;
import org.usfirst.frc6406.commands.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.VictorSP;


/**
 *
 */
public class Climber extends Subsystem {



    private final VictorSPX motor = RobotMap.winchMotor;


    @Override
    public void initDefaultCommand() {


        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    @Override
    public void periodic() {
        // Put code here to be run every loop

    }

    public void climb() {
    	if (motor != null) {
    		motor.set(ControlMode.PercentOutput, 0.8);	
    	}
    }

    public void stopClimb() {
    	if (motor != null) {
    		motor.set(ControlMode.PercentOutput, 0.0);
    	}

    }
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

}
