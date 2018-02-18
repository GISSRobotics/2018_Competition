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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc6406.Robot;

/**
 *
 */
public class Drop extends Command {

    private double m_setpoint;

    public Drop(double setpoint) {

        m_setpoint = setpoint;

        requires(Robot.wrist);
        requires(Robot.claw);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        Robot.wrist.enable();
        Robot.wrist.move(m_setpoint);//WRIST_MOVE

    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
    	if (Robot.wrist.onTarget()) {
            Robot.claw.setOpen();
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return Robot.wrist.onTarget() && Robot.claw.getState();

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
