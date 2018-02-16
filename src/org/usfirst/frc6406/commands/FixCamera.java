package org.usfirst.frc6406.commands;

import org.usfirst.frc6406.Robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class FixCamera extends Command {

    public FixCamera() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        UsbCamera thing = (Robot.cameras.camerabill);
        Robot.cameras.camerabill = Robot.cameras.camerajill;
        Robot.cameras.camerajill = thing;
        new switchtofrontcamera().start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
