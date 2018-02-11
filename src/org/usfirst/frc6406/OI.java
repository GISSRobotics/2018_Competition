// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

package org.usfirst.frc6406;

import org.usfirst.frc6406.commands.*;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // Button button = new JoystickButton(stick, buttonNumber);

    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.

    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:

    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());

    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());

    // Start the command when the button is released and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public JoystickButton telescopeup;
    public JoystickButton telescopeuprelease;
    public JoystickButton telescopedown;
    public JoystickButton telescopedownrelease;
    public JoystickButton opencloseclaw;
    public JoystickButton climb;
    public JoystickButton stopclimb;
    public JoystickButton wristdown;
    public JoystickButton wristup;
    public JoystickButton topscale;
    public JoystickButton mediumscale;
    public JoystickButton bottomscale;
    public JoystickButton switchHeight;
    public JoystickButton pickupheight;
    public JoystickButton sensitivitydecrease;
    public JoystickButton sensitivityincrease;
    public Joystick stick;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    public OI() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS

        stick = new Joystick(0);

        sensitivityincrease = new JoystickButton(stick, 4);
        sensitivityincrease.whenPressed(new buttonIncrease());
        sensitivitydecrease = new JoystickButton(stick, 6);
        sensitivitydecrease.whenPressed(new buttonDecrease());
        //pickupheight = new JoystickButton(stick, 12);
        //pickupheight.whenPressed(new liftmove(0.05));
        switchHeight = new JoystickButton(stick, 10);
        switchHeight.whenPressed(new liftmove(0.2));
        bottomscale = new JoystickButton(stick, 9);
        bottomscale.whenPressed(new liftmove(0.5));
        mediumscale = new JoystickButton(stick, 8);
        mediumscale.whenPressed(new liftmove(0.85));
        topscale = new JoystickButton(stick, 7);
        topscale.whenPressed(new liftmove(1));
        // wristup = new JoystickButton(stick, 31);
        // wristup.whenPressed(new WristMove(1));
        // wristdown = new JoystickButton(stick, 32);
        // wristdown.whenPressed(new WristMove(0));
        stopclimb = new JoystickButton(stick, 11);
        stopclimb.whenReleased(new StopClimb());
        climb = new JoystickButton(stick, 11);
        climb.whileHeld(new Climb());
        opencloseclaw = new JoystickButton(stick, 2);
        opencloseclaw.whenPressed(new Claw_toggle());
        telescopedownrelease = new JoystickButton(stick, 3);
        telescopedownrelease.whenReleased(new telescopestop());
        telescopedown = new JoystickButton(stick, 3);
        telescopedown.whileHeld(new TelescopeDown());
        // telescopeuprelease = new JoystickButton(stick, 5);
        // telescopeuprelease.whenReleased(new telescopestop());
        telescopeup = new JoystickButton(stick, 5);
        telescopeup.whileHeld(new TelescopeUp());

    }

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=FUNCTIONS
    public Joystick getstick() {
        return stick;
    }

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=FUNCTIONS
}
