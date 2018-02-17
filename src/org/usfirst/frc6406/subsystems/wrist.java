// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

package org.usfirst.frc6406.subsystems;

import org.usfirst.frc6406.Robot;
import org.usfirst.frc6406.RobotMap;
import org.usfirst.frc6406.commands.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.VictorSP;

/**
 *
 */
public class wrist extends PIDSubsystem {
    private final VictorSPX motor = RobotMap.wristmotor;
    private final AnalogInput pot = RobotMap.wristpot;

    // Initialize your subsystem here
    public wrist() {
        super("wrist", 3, 0.0, 0.0);
        setAbsoluteTolerance(0.04);
        getPIDController().setContinuous(false);

        // Use these to get going:
        // setSetpoint() - Sets where the PID controller should move the system
        // to
        // enable() - Enables the PID controller.
    }

    @Override
    public void initDefaultCommand() {

        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("wrist position", Robot.wrist.pot.getVoltage());

        Boolean a = (System.currentTimeMillis() / 125) % 2 == 1 && Robot.wrist.pot.getVoltage() > 1;
        SmartDashboard.putBoolean("wrist be down", a);
    }

    @Override
    protected double returnPIDInput() {
        // Return your input value for the PID loop
        // e.g. a sensor, like a potentiometer:
        // yourPot.getAverageVoltage() / kYourMaxVoltage;

        return pot.getAverageVoltage() / 2.5;
    }

    @Override
    protected void usePIDOutput(double output) {
        // Use output to drive your system, like a motor
        // e.g. yourMotor.set(output);

        motor.set(ControlMode.PercentOutput, output);

    }

    public void move(double m_setpoint) {
        if (m_setpoint > .25) {
            getPIDController().setP(3);
        } else {getPIDController().setP(10);
        }
        setSetpoint(m_setpoint);

        // TODO Auto-generated method stub

    }
}
