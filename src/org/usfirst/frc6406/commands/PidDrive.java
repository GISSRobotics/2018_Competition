package org.usfirst.frc6406.commands;

import org.usfirst.frc6406.Robot;
import org.usfirst.frc6406.RobotMap;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class PidDrive extends Command implements PIDOutput {

	double distance;
	private PIDController turnController;
	public static double rotateToAngleRate;
	static final double kP = 0.0; //SmartDashboard.getNumber("P Drive", 0.00);//0.002;
	static final double kI = 0.0;
	static final double kD = 0. ; //SmartDashboard.getNumber("D Drive", 0.00);//0.0032;
	static final double kF = 0.0;
	
	//static final double kToleranceDegrees = 0.25;
	
	
    public PidDrive(double dist) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	distance = dist;
    	double p = SmartDashboard.getNumber("P Drive", 0.00);
    	SmartDashboard.putNumber("pp", p);
    	//System.out.printf("p drive %f\n", p);
    	double d =  SmartDashboard.getNumber("D Drive", 0.00);
    	SmartDashboard.putNumber("dd", d);
    	System.out.printf("d drive %f\n", d);
    	turnController = new PIDController(kP, kI, kD, kF, RobotMap.ahrs, this);
		turnController.setInputRange(-180.0f, 180.0f);
		turnController.setOutputRange(-0.5, 0.5);
		turnController.setPercentTolerance(3.0);
		//turnController.setAbsoluteTolerance(kToleranceDegrees);
		turnController.setContinuous(true);
		turnController.setSetpoint(180);
		rotateToAngleRate = 0.0;
		setTimeout(7);

    	requires(Robot.drive);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	RobotMap.driveQuadratureEncoder2.reset();
    	RobotMap.ahrs.reset();
    	RobotMap.ahrs.zeroYaw();
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
    protected void execute() {
    	if (RobotMap.driveQuadratureEncoder2.getDistance() < distance) {
    		Robot.drive.turnAngle(0.7, rotateToAngleRate);
    	} else {

    		Robot.drive.turnAngle(0.0, rotateToAngleRate);
    	}
    	// System.out.println(rotateToAngleRate);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
		SmartDashboard.putBoolean("on_target", turnController.onTarget());
		SmartDashboard.putNumber("yaw value", RobotMap.ahrs.getYaw());
		SmartDashboard.putNumber("Encoder Distance", RobotMap.driveQuadratureEncoder2.getDistance());
    	if (RobotMap.driveQuadratureEncoder2.getDistance() < distance) {
    		return false;
    	}
    	if (RobotMap.driveQuadratureEncoder2.getDistance() >= distance && turnController.onTarget()){ // && Math.abs(RobotMap.ahrs.getYaw())<1.0)
    			
    		return true;
    	} else {
    		
    		return isTimedOut();
    	}
    		
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drive.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
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
