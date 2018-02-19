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

import edu.wpi.first.wpilibj.command.ConditionalCommand;
import org.usfirst.frc6406.Robot;

/**
 *
 */
public class Claw_toggle extends ConditionalCommand {

    public Claw_toggle() {
        super(new ClawClose(), new ClawOpen());
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

    @Override
    protected boolean condition() {
        return Robot.claw.getState();
    }
}
