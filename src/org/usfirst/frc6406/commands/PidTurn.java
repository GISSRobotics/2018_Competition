package org.usfirst.frc6406.commands;

import org.usfirst.frc6406.Robot;
import org.usfirst.frc6406.RobotMap;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PidTurn extends Command implements PIDOutput {

	double absoluteAngle;
	double minAngle;
	double maxAngle;
	static final double kP = 0.03;
	static final double kI = 0.00;
	static final double kD = 0.00;
	static final double kF = 0.00;
	static final double kToleranceDegrees = 0.5;
	
	 public PIDController turnController;
	static public double rotateToAngleRate;

	public PidTurn(double deg) {
		SmartDashboard.putNumber("Target:", deg);
		turnController = new PIDController(kP, kI, kD, kF, RobotMap.ahrs, this);
		turnController.setInputRange(-180.0f, 180.0f);
		turnController.setOutputRange(-0.5, 0.5);
		turnController.setAbsoluteTolerance(kToleranceDegrees);
		turnController.setContinuous(true);
		turnController.setSetpoint(deg);
		rotateToAngleRate = 0.0;
		setTimeout(3);
		

	}

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
		turnController.enable();
	}
	
	 // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
    	Robot.drive.turnAngle(rotateToAngleRate);
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
    	SmartDashboard.putNumber("Yaw2:",  RobotMap.ahrs.getYaw());
    	return turnController.onTarget() || isTimedOut();
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
    	turnController.disable();
    	Robot.drive.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
    	end();
    }

    @Override
    /* This function is invoked periodically by the PID Controller, */
    /* based upon navX-MXP yaw angle input and PID Coefficients.    */
    public void pidWrite(double output) {
        rotateToAngleRate = output;
    }

}
