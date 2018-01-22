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

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Timer;

import org.usfirst.frc6406.Robot;
import org.usfirst.frc6406.RobotMap;

import com.kauailabs.navx.frc.AHRS;

/**
 *
 */
public class AutonomousCommand extends Command {
	int pos;
	AHRS ahrs;
	float yaw;

	// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS

	// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS

	// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
	public AutonomousCommand() {


		// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
		// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING

		// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING

        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		
		

		pos = 0;

		
		try {
			/* Communicate w/navX-MXP via the MXP SPI Bus. */
			/* Alternatively: I2C.Port.kMXP, SerialPort.Port.kMXP or SerialPort.Port.kUSB */
			/*
			 * See http://navx-mxp.kauailabs.com/guidance/selecting-an-interface/ for
			 * details.
			 */
			ahrs = new AHRS(SPI.Port.kMXP);
			ahrs.reset();
			Timer.delay(8);

		} catch (RuntimeException ex) {
			DriverStation.reportError("Error instantiating navX-MXP:  " + ex.getMessage(), true);
		}
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		
		yaw = (float) ahrs.getYaw();
		if (!ahrs.isCalibrating()) {
			SmartDashboard.putNumber("compass:", ahrs.getCompassHeading());
			SmartDashboard.putBoolean("IMU_Connected", ahrs.isConnected());
			SmartDashboard.putBoolean("IMU_IsCalibrating", ahrs.isCalibrating());
		//	SmartDashboard.putNumber("Yaw:", yaw);
			SmartDashboard.putNumber("IMU_Pitch", ahrs.getPitch());
			SmartDashboard.putNumber("IMU_Roll", ahrs.getRoll());
			SmartDashboard.putNumber("Displacement_X", ahrs.getDisplacementX());
			SmartDashboard.putNumber("Displacement_Y", ahrs.getDisplacementY());
			SmartDashboard.putNumber("pos:", pos);
		}
		if (pos==30) {
			SmartDashboard.putString("Log:", "1op");
			
		}
		pos += 1;

		// RobotMap.driveMotorDeck.tankDrive(0.5, 0.55);
		return;
		
		
		
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {

		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}
}
