package org.usfirst.frc6406.commands;

import edu.wpi.first.wpilibj.PIDOutput;

public class PidDriveOutput implements PIDOutput {

	public static double driveRate;
	
	public PidDriveOutput() {
        reset();
    }
	
	public void reset() {
		driveRate = 0.0;
	}

	@Override
	public void pidWrite(double output) {
		driveRate = output;
	}

}
