package org.usfirst.frc6406.commands;

import org.usfirst.frc6406.Robot;
import org.usfirst.frc6406.RobotMap;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class PidDrive extends Command implements PIDOutput {

	double distance;
	private PIDController curveController;
	private PIDController driveController;
	private PidDriveOutput driveOutput;
	private int onTargetCounter;
	public static double rotateToAngleRate = 0.0;
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
    	double d =  SmartDashboard.getNumber("D Drive", 0.00);
    	double pT = SmartDashboard.getNumber("P DriveTurn", 0.00);
    	double dT =  SmartDashboard.getNumber("D DriveTurn", 0.00);
    	curveController = new PIDController(pT, kI, dT, kF, RobotMap.ahrs, this);
		curveController.setInputRange(-180.0, 180.0);
		curveController.setOutputRange(-0.5, 0.5);
		curveController.setAbsoluteTolerance(0.5);
		curveController.setContinuous(true);
		curveController.setSetpoint(0);
		rotateToAngleRate = 0.0;
		//setTimeout(7);
		
		RobotMap.driveQuadratureEncoder2.setPIDSourceType(PIDSourceType.kDisplacement);
		driveOutput = new PidDriveOutput();
		driveController = new PIDController(p, kI, d, kF, RobotMap.driveQuadratureEncoder2, driveOutput);
		driveController.setInputRange(0, 1.1*dist);
		driveController.setOutputRange(-0.8, 0.8);
		driveController.setAbsoluteTolerance(10);
		driveController.setContinuous(false);
		driveController.setSetpoint(dist);
		System.out.println(dist);
		
    	requires(Robot.drive);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	SmartDashboard.putString("Error", "d:"+distance);
    	rotateToAngleRate = 0.0;
    	RobotMap.driveQuadratureEncoder2.reset();
    	driveOutput.reset();
    	//RobotMap.ahrs.reset();
    	RobotMap.ahrs.zeroYaw();
    	/*
		while (Math.abs(RobotMap.ahrs.getYaw()) > 0) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		*/
		curveController.enable();
		driveController.enable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//	if (!driveController.onTarget()) {
    	Robot.drive.turnAngle(-driveOutput.driveRate, rotateToAngleRate);
    	//} else {

    		//Robot.drive.turnAngle(0.0, rotateToAngleRate);
    	//}
      //System.out.println(-driveOutput.driveRate);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	SmartDashboard.putNumber("Speed",  RobotMap.driveQuadratureEncoder2.getRate()/(689.44 * 0.8)*3.6);
    	SmartDashboard.putNumber("Distance Error",  driveController.getError());
    	SmartDashboard.putNumber("Distance Error Graph",  driveController.getError());
    	SmartDashboard.putNumber("PID Get", RobotMap.driveQuadratureEncoder2.pidGet());
    	SmartDashboard.putNumber("Yaw2:",  RobotMap.ahrs.getYaw());
    	SmartDashboard.putNumber("Yaw Error",  curveController.getError());
    	SmartDashboard.putNumber("Yaw Error Graph",  curveController.getError());
		SmartDashboard.putBoolean("on_target", curveController.onTarget());
		SmartDashboard.putNumber("Encoder Distance", RobotMap.driveQuadratureEncoder2.getDistance());
		SmartDashboard.putNumber("NavX Distance", RobotMap.ahrs.getDisplacementY());
		if (driveController.onTarget()) {
    		onTargetCounter++;
    	}else{
    		 onTargetCounter = 0;
    	}
		SmartDashboard.putNumber("targetCounter", onTargetCounter);
    	return onTargetCounter == 10;
//    	if (Math.abs(RobotMap.driveQuadratureEncoder2.getDistance()) < distance) {
//    		return false;
//    	}
//    	if (Math.abs(RobotMap.driveQuadratureEncoder2.getDistance()) >= distance && curveController.onTarget()){ // && Math.abs(RobotMap.ahrs.getYaw())<1.0)
//    			
//    		return true;
//    	} else {
//    		
//    		return isTimedOut();
//    	}
    		
    }

    // Called once after isFinished returns true
    protected void end() {
    	driveController.disable();
    	curveController.disable();
    	Robot.drive.stop();
    	System.out.println("I stopped");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	System.out.println("I got inturrupted.");
    	end();
    }
    
    @Override
    /* This function is invoked periodically by the PID Controller, */
    /* based upon navX-MXP yaw angle input and PID Coefficients.    */
    public void pidWrite(double output) {
        rotateToAngleRate = output;
    }
}
