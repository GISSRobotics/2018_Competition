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
	        wristdown.whenPressed(new WristMove(0.4));
	
	        stopclimb = new JoystickButton(flightstick, 11);
	        stopclimb.whenReleased(new StopClimb());
	        climb = new JoystickButton(flightstick, 11);
	        climb.whileHeld(new Climb());
	        //opencloseclaw = new JoystickButton(flightstick, 2);
	        //opencloseclaw.whenPressed(new Claw_toggle());
	        telescopedownrelease = new JoystickButton(flightstick, 3);
	        telescopedownrelease.whenReleased(new telescopestop());
	        telescopedown = new JoystickButton(flightstick, 3);
	        telescopedown.whileHeld(new TelescopeDown());
	        telescopeup = new JoystickButton(flightstick, 5);
	        telescopeup.whileHeld(new TelescopeUp());
    	}
    	
    	if (xboxstick != null) {
	        //START XBOX CONFIG
	        opencloseclaw = new JoystickButton(xboxstick, 5);
	        opencloseclaw.whenPressed(new Claw_toggle());
	        low = new JoystickButton(xboxstick, 1);
	        low.whenPressed(new liftmove(TELE_PRESET_LOW));
	        medium = new JoystickButton(xboxstick, 2);
	        medium.whenPressed(new liftmove(TELE_PRESET_MEDIUM));
	        high = new JoystickButton(xboxstick, 4);
	        high.whenPressed(new liftmove(TELE_PRESET_HIGH));
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
    
    public void UpdateXboxAxes() {
    	
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
