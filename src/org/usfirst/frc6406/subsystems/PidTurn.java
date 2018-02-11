package org.usfirst.frc6406.subsystems;

import org.usfirst.frc6406.RobotMap;

import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PidTurn extends PIDSubsystem {

	
	public PidTurn() {
		super(45,0,0);
		setAbsoluteTolerance(0.05);
		getPIDController().setContinuous(false);
	}
	@Override
	protected double returnPIDInput() {
		// TODO Auto-generated method stub
		return RobotMap.ahrs.getYaw();
	}

	@Override
	protected void usePIDOutput(double output) {
		// TODO Auto-generated method stub
		SmartDashboard.putNumber("Pid OUt:",  output);
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}

}
