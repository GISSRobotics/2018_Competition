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
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.BaseMotorController;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS

// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS


/**
 *
 */
public class Lift extends Subsystem {


	// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

	// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

	private static final int MAX_HEIGHT = 229320; // this is encoder data scaled to 36 inches on our twelve tooth gear
	public static final double INCREMENT = 0.15;
	// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
	private final WPI_TalonSRX truckMotor = RobotMap.lifttruckMotor;
	private final WPI_TalonSRX telescopeMotor = RobotMap.lifttelescopeMotor;

	// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
	public double UpHeight;
	public double Down = -1;
	public int Height;
	public int WantedHeight;
	public int pidid = 0;

	@Override
	public void initDefaultCommand() {
		// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

		// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	public double telescopePosition() {
		return telescopeMotor.getSelectedSensorPosition(pidid) / MAX_HEIGHT;
	}

	public void moveTelescope(double HeightIn) {

		telescopeMotor.set(ControlMode.Position, HeightIn * MAX_HEIGHT);
	}

	@Override
	public void periodic() {
		// Put code here to be run every loo

	}

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

}
