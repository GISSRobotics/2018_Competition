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
    public Joystick xboxstick;
    public Joystick customstick;
    public JoystickButton fairydriving;

    public OI() {
    	initSticks();
    	
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
        wristup.whenPressed(new WristMove(0.0));
        wristdown = new JoystickButton(stick, 4);
        wristdown.whenPressed(new WristMove(0.4));

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
    }

	private void initSticks() {
		for (int order = 0; order <= 2; order++) {
        	Joystick tempStick = new Joystick(order);
        	System.out.print("USB [" + order + "] has [" + tempStick.getAxisCount() + "] axes");
        	switch(tempStick.getAxisCount()) {
        	case 2:
        		customstick = tempStick;
        		System.out.print("USB [" + order +"] is assigned to the custom stick");
        		break;
        	case 4:
        		stick = tempStick;
        		System.out.print("USB [" + order +"] is assigned to the flight stick");
        		break;
        	default:
        		xboxstick = tempStick;
        		System.out.print("USB [" + order +"] is assigned to the xbox stick");
        		break;
        	}
    	}
	}
    
    public void UpdateXboxAxes() {
    	
		System.out.print("XBOX Axis 0:"+xboxstick.getRawAxis(0));
		System.out.print("XBOX Axis 1:"+xboxstick.getRawAxis(1));
		System.out.print("XBOX Axis 2:"+xboxstick.getRawAxis(2));
		System.out.print("XBOX Axis 3:"+xboxstick.getRawAxis(3));
		System.out.print("XBOX Axis 4:"+xboxstick.getRawAxis(4));
		System.out.print("XBOX Axis 5:"+xboxstick.getRawAxis(5));
		
		System.out.print("XBOX POV:" + xboxstick.getPOV());
		
		
		//if (stick.getRawAxis(0) != 0) {
		
   		//}
		
		//if (stick.getRawAxis(1) != 0) {
   		
		//}
		
		//if (stick.getRawAxis(2) != 0) {
   		
		//}
		
		//if (stick.getRawAxis(3) != 0) {
   		
		//}
		
		//if (stick.getRawAxis(4) != 0) {
   		
		//}
		
		//if (stick.getRawAxis(5) != 0) {
   		
		//}
    }
}
