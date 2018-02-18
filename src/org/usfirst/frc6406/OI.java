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
    public Joystick stick;
    public JoystickButton fairydriving;

    public OI() {

        stick = new Joystick(0);
        fairydriving = new JoystickButton(stick, 1);
        fairydriving.whenPressed(new switchtobackcamera());
        fairydriving.whenReleased(new switchtofrontcamera());
        pickupheight = new JoystickButton(stick, 12);
        pickupheight.whenPressed(new liftmove(0.05));
        switchHeight = new JoystickButton(stick, 10);
        switchHeight.whenPressed(new liftmove(0.2));
        bottomscale = new JoystickButton(stick, 9);
        bottomscale.whenPressed(new liftmove(0.5));
        mediumscale = new JoystickButton(stick, 8);
        mediumscale.whenPressed(new liftmove(0.85));
        topscale = new JoystickButton(stick, 7);
        topscale.whenPressed(new liftmove(1));

        wristup = new JoystickButton(stick, 6);
        wristup.whenPressed(new WristMove(0.0));//WRIST_MOVE
        wristdown = new JoystickButton(stick, 4);
        wristdown.whenPressed(new WristMove(0.4));//WRIST_MOVE

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
        telescopeup = new JoystickButton(stick, 5);
        telescopeup.whileHeld(new TelescopeUp());

        SmartDashboard.putData("switch camera", new FixCamera());
        SmartDashboard.putNumber("WristUpPosition", 0.0);//TEMP FOR TESTING WRIST POSITIONS
    }
}
