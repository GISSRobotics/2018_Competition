package org.usfirst.frc6406.commands;

import edu.wpi.first.wpilibj.command.Command;

public class NoOp extends Command {

	private double timeout;
	
    public NoOp(double seconds) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	
    	timeout = seconds;    	
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	setTimeout(timeout);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
