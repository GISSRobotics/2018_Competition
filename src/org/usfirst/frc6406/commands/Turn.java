// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

package org.usfirst.frc6406.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc6406.Robot;
import org.usfirst.frc6406.RobotMap;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Turn extends Command {

	double angle;
	double absolutangle;

	// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
	public Turn() {

		// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
		// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING

		// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING
		// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
		requires(Robot.drive);

		// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
	}

	public Turn(double value) {
		angle = value;
		requires(Robot.drive);

		absolutangle = Math.abs(angle);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		RobotMap.ahrs.reset();
		while (Math.abs(RobotMap.ahrs.getYaw()) > 0) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		SmartDashboard.putBoolean("conection stage two:", RobotMap.ahrs.isConnected());

		if (angle > 0) {
			Robot.drive.turnright();
		} else if (angle < 0) {
			Robot.drive.turnleft();
		} else {
			end();
		}
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {

	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		SmartDashboard.putNumber("Yaw2:", RobotMap.ahrs.getYaw());
		return Math.abs(RobotMap.ahrs.getYaw()) >= absolutangle;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {

		Robot.drive.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}
