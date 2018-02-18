package org.usfirst.frc6406.commands;

import edu.wpi.first.wpilibj.PIDOutput;

public class PidDriveOutput implements PIDOutput {

	public static double driveRate;
	
	public PidDriveOutput() {
		driveRate = 0.0;
	}
	
	public void reset() {
		
	}
	@Override
	public void pidWrite(double output) {
		// TODO Auto-generated method stub
		driveRate = output;
		
	}

}
