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
    public JoystickButton high;
    public JoystickButton medium;
    public JoystickButton low;
    public JoystickButton switchHeight;
    public JoystickButton pickupheight;
    public Joystick flightstick = null;
    public Joystick xboxstick = null;
    public Joystick customstick = null;
    public JoystickButton fairydriving;
    
    private static final double TELE_PRESET_HIGH = 391/453;
    private static final double TELE_PRESET_MEDIUM = 82.3/453;
    private static final double TELE_PRESET_LOW = 0.05;

    public OI() {
    	initSticks();
    	
    	if (flightstick != null) {
    		// Logitech 3D Flightstick
    		fairydriving = new JoystickButton(flightstick, 1);
	        fairydriving.whenPressed(new switchtobackcamera());
	        fairydriving.whenReleased(new switchtofrontcamera());
	        
	        low = new JoystickButton(flightstick, 9);
	        low.whenPressed(new liftmove(TELE_PRESET_LOW));
	        medium = new JoystickButton(flightstick, 8);
	        medium.whenPressed(new liftmove(TELE_PRESET_MEDIUM));
	        high = new JoystickButton(flightstick, 7);
	        high.whenPressed(new liftmove(TELE_PRESET_HIGH));
	
	        wristup = new JoystickButton(flightstick, 6);
	        wristup.whenPressed(new WristMove(0.0));
	        wristdown = new JoystickButton(flightstick, 4);
	        wristdown.whenPressed(new WristMove(1.0));
	
	        stopclimb = new JoystickButton(flightstick, 11);
	        climb.whileHeld(new Climb());
	        climb.whenReleased(new StopClimb());
	        opencloseclaw = new JoystickButton(flightstick, 2);
	        opencloseclaw.whenPressed(new Claw_toggle());
	        telescopedown = new JoystickButton(flightstick, 3);
	        telescopedown.whileHeld(new TelescopeDown());
	        telescopedown.whenReleased(new telescopestop());
	        telescopeup = new JoystickButton(flightstick, 5);
	        telescopeup.whileHeld(new TelescopeUp());
    	}
    	
    	if (xboxstick != null) {
	        // Xbox 360 gamepad
	        opencloseclaw = new JoystickButton(xboxstick, 5);
	        opencloseclaw.whenPressed(new Claw_toggle());
	        low = new JoystickButton(xboxstick, 1);
	        low.whenPressed(new liftmove(TELE_PRESET_LOW));
	        medium = new JoystickButton(xboxstick, 2);
	        medium.whenPressed(new liftmove(TELE_PRESET_MEDIUM));
	        high = new JoystickButton(xboxstick, 4);
	        high.whenPressed(new liftmove(TELE_PRESET_HIGH));
    	}
    	
    	if (customstick != null) {
    		// Custom PowerUp console
    		opencloseclaw = new JoystickButton(customstick, 1);
    		opencloseclaw.whenPressed(new claw_open());
    		opencloseclaw.whenReleased(new claw_close());
    		stopclimb = new JoystickButton(customstick, 2);
	        climb.whileHeld(new Climb());
	        climb.whenReleased(new StopClimb());
    	}
    }

	private void initSticks() {
		for (int order = 0; order <= 1; order++) {
        	Joystick tempStick = new Joystick(order);
        	System.out.println("USB [" + order + "] has [" + tempStick.getAxisCount() + "] axes");
        	switch(tempStick.getAxisCount()) {
        	case 2:
        		customstick = new Joystick(order);
        		System.out.println("USB [" + order +"] is assigned to the custom stick");
        		break;
        	case 4:
        		flightstick = new Joystick(order);
        		System.out.println("USB [" + order +"] is assigned to the flight stick");
        		break;
        	case 6:
        		xboxstick = new Joystick(order);
        		System.out.println("USB [" + order +"] is assigned to the xbox stick");
        		break;
        	default:
        		System.out.println("We have no idea what USB [" + order +"] is!");
        		break;
        	}
    	}
	}
    
    public void RunAxes() {
    	
    	// These happen here so that subsystems don't get taken over.
    	
    	// Driving
    	if (flightstick != null) {
    		double acceleration = flightstick.getRawAxis(1);
            double steering = -flightstick.getRawAxis(0);
            double driveSensitivity = (flightstick.getRawAxis(3) / -4.0) + 0.75;
            double steeringSensitivity = (flightstick.getRawAxis(3) / -3.0) + 0.66;
            double reverse = (flightstick.getRawButton(1)) ? -1 : 1;
            Robot.drive.arcadeDrive(acceleration * driveSensitivity * reverse, steering * steeringSensitivity);
    	}
    	
    	// Telescope
    	// Both xbox and custom have controls for this
    	if (xboxstick != null) {
    		double movementSpeed = xboxstick.getRawAxis(4);
    		Robot.lift.Move(movementSpeed);
    	} else if (customstick != null) {
    		double targetPosition = (customstick.getRawAxis(0) / 2.0) + 0.5;
    		Robot.lift.moveTelescope(targetPosition);
    	}
    	
    	// Wrist
    	// Both xbox and custom have controls for this
    	if (xboxstick != null) {
    		int pov = xboxstick.getPOV(0);
    		if (pov > 90 && pov < 270) {
    			(new WristMove(1.0)).start();
    		} else {
    			(new WristMove(0.0)).start();
    		}
    	} else if (customstick != null) {
    		double targetPosition = customstick.getRawAxis(1) + 1.0;
    		(new WristMove(targetPosition)).start();
    	}
    	
		System.out.print("XBOX Axis 0:"+xboxstick.getRawAxis(0));
		System.out.print("XBOX Axis 1:"+xboxstick.getRawAxis(1));
		System.out.print("XBOX Axis 2:"+xboxstick.getRawAxis(2));
		System.out.print("XBOX Axis 3:"+xboxstick.getRawAxis(3));
		System.out.print("XBOX Axis 4:"+xboxstick.getRawAxis(4));
		System.out.print("XBOX Axis 5:"+xboxstick.getRawAxis(5));
		System.out.print("XBOX POV:"   +xboxstick.getPOV());
		
		//if (xboxstick.getRawAxis(0) != 0) {
		
   		//}
		
		//if (xboxstick.getRawAxis(1) != 0) {
   		
		//}
		
		//if (xboxstick.getRawAxis(2) != 0) {
   		
		//}
		
		//if (xboxstick.getRawAxis(3) != 0) {
   		
		//}
		
		//if (xboxstick.getRawAxis(4) != 0) {
   		
		//}
		
		//if (xboxstick.getRawAxis(5) != 0) {
   		
		//}
		
		//if (xboxstick.getPOV(0) != -1){
			//This should be elbow up command
		//}
		
		//if (xboxstick.getPOV(180) != -1){
			//This should be elbow down command
		//}
		
    }
}
