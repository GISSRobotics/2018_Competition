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

import org.usfirst.frc6406.RobotMap;
import org.usfirst.frc6406.commands.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.SensorCollection;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.BaseMotorController;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

/**
 *
 */
public class Lift extends Subsystem {

    private static final int MAX_HEIGHT_TRUCK = 227000; // this is encoder data scaled to 36 inches on our twelve tooth
                                                        // gear
    private static final int MAX_HEIGHT_TELESCOPE = 226000;
    private static final int INCREMENT = 10000;
    private int targetHeight = 0;
    private boolean truckInit = false;
    private boolean telescopeInit = false;
    private final WPI_TalonSRX truckMotor = RobotMap.lifttruckMotor;
    private final WPI_TalonSRX telescopeMotor = RobotMap.lifttelescopeMotor;

    SensorCollection truckStatus;
    SensorCollection telescopeStatus;

    public int pidid = 0;

    public Lift() {
        truckStatus = truckMotor.getSensorCollection();
        telescopeStatus = telescopeMotor.getSensorCollection();

        truckMotor.configClosedloopRamp(0.1, 100);
        telescopeMotor.configClosedloopRamp(0.1, 100);

    }

    @Override
    public void initDefaultCommand() {

        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    public int currentHeight() {
        int a = telescopeMotor.getSelectedSensorPosition(0);
        int b = truckMotor.getSelectedSensorPosition(0);
        return -(a + b);
    }

    public void moveTelescope(double HeightIn) {
        // Takes height in percentage of total height
        MoveToTarget((int) (HeightIn * (MAX_HEIGHT_TELESCOPE + MAX_HEIGHT_TRUCK)));
    }

    public void Up() {
        MoveToTarget(currentHeight() + 4 * INCREMENT);
    }

    public void Down() {
        MoveToTarget(currentHeight() - INCREMENT);
    }

    public void MoveToTarget(int pos) {
        // Keep pos in range
        pos = Math.max(Math.min(pos, MAX_HEIGHT_TELESCOPE + MAX_HEIGHT_TRUCK), 0);
        // telescope can't go lower than 5%
        // int telescopeTarget = Math.max(Math.min(pos, MAX_HEIGHT_TELESCOPE), (int)
        // (0.0 * MAX_HEIGHT_TELESCOPE));
        // truck can't go lower than 5%
        // int truckTarget = Math.max(pos - MAX_HEIGHT_TELESCOPE, (int) (0.0 *
        // MAX_HEIGHT_TRUCK));

        int telescopeTarget = Math.max(pos - MAX_HEIGHT_TRUCK, (int) (0.0 * MAX_HEIGHT_TELESCOPE));
        // truck can't go lower than 5%
        int truckTarget = Math.max(Math.min(pos, MAX_HEIGHT_TRUCK), (int) (0.0 * MAX_HEIGHT_TRUCK));

        // Encoder counts are actually negative
        telescopeTarget *= -1;
        truckTarget *= -1;

        System.out.println("target:" + telescopeTarget + ", " + truckTarget);
        telescopeMotor.set(ControlMode.Position, telescopeTarget);
        truckMotor.set(ControlMode.Position, truckTarget);
        SmartDashboard.putNumber("telescope-position", pos);
    }

    @Override
    public void periodic() {

        // Put code here to be run every loo
        if (telescopeMotor.getSelectedSensorPosition(0) > 0 && !(telescopeMotor.getMotorOutputPercent() < 0.0)) {
            if (!telescopeStatus.isRevLimitSwitchClosed()) {
                telescopeInit = true;
                telescopeMotor.setSelectedSensorPosition(10000, pidid, 100);
                // System.out.println("Telescope encoder reset to 0.");
            }
            telescopeMotor.set(0.0);

        }
        if (!telescopeInit) {
            // telescopeMotor.set(0.2);
        }

        if (truckMotor.getSelectedSensorPosition(0) > 0 && !(truckMotor.getMotorOutputPercent() < 0.0)) {
            if (!truckStatus.isRevLimitSwitchClosed()) {
                truckInit = true;
                truckMotor.setSelectedSensorPosition(10000, pidid, 100);
                System.out.println("Truck encoder reset to 0.");
            }
            truckMotor.set(0.0);

        }
        if (!truckInit) {
            // truckMotor.set(0.2);
        }
    }

    public void stop() {
         
        telescopeMotor.set(0.0);
        truckMotor.set(0.0);
    }
    

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

}
