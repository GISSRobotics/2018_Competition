package org.usfirst.frc6406;

import org.usfirst.frc6406.commands.*;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    private JoystickButton flightTelescopeUp;
    private JoystickButton flightTelescopeDown;
    private JoystickButton flightClaw;
    private JoystickButton flightClimb;
    private JoystickButton flightWristUp;
    private JoystickButton flightWristDown;
    private JoystickButton flightTelescopeHigh;
    private JoystickButton flightTelescopeMedium;
    private JoystickButton flightTelescopeLow;
    private JoystickButton flightInvertDrive;
    private JoystickButton xboxClaw;
    private JoystickButton xboxTelescopeHigh;
    private JoystickButton xboxTelescopeMedium;
    private JoystickButton xboxTelescopeLow;
    private JoystickButton customClaw;
    private JoystickButton customClimb;
    public Joystick flightstick = null;
    public Joystick xboxstick = null;
    public Joystick customstick = null;
    
    private static final int FLIGHT_TEL_UP = 5;
    private static final int FLIGHT_TEL_DOWN = 3;
    private static final int FLIGHT_TEL_HIGH = 8;
    private static final int FLIGHT_TEL_MED = 10;
    private static final int FLIGHT_TEL_LOW = 12;
    private static final int FLIGHT_WRIST_UP = 6;
    private static final int FLIGHT_WRIST_DOWN = 4;
    private static final int FLIGHT_CLAW = 2;
    private static final int FLIGHT_CLIMB = 11;
    private static final int FLIGHT_INVERT = 1;
    private static final int FLIGHT_AXIS_ACCELERATE = 1;
    private static final int FLIGHT_AXIS_STEER = 0;
    private static final int FLIGHT_AXIS_SENSITIVITY = 3;
    
    private static final int XBOX_TEL_HIGH = 4;
    private static final int XBOX_TEL_MED = 2;
    private static final int XBOX_TEL_LOW = 1;
    private static final int XBOX_CLAW = 5;
    private static final int[] XBOX_POV_WRIST_UP = {315, 0, 45};
    private static final int[] XBOX_POV_WRIST_DOWN = {135, 180, 225};
    private static final int XBOX_AXIS_TEL = 5;
    
    private static final int CUSTOM_CLAW = 1;
    private static final int CUSTOM_CLIMB = 2;
    private static final int CUSTOM_AXIS_TEL = 0;
    private static final int CUSTOM_AXIS_WRIST = 1;
    
    private static final double TELE_PRESET_HIGH = 0.87;
    private static final double TELE_PRESET_MEDIUM = 0.2;
    private static final double TELE_PRESET_LOW = 0.05;
    private static final double WRIST_PRESET_UP = 0.1;
    private static final double WRIST_PRESET_DOWN = 0.74;
    
    public static enum Indication {
        WristDown
    }

    public OI() {
    	checkSticks();
    	SmartDashboard.putData("switch camera", new FixCamera());
    }

	public void checkSticks() {
		Robot.Log("Updating controllers...", 2);
		boolean flight = false;
		boolean xbox = false;
		boolean custom = false;
		for (int order = 0; order <= 1; order++) {
        	Joystick tempStick = new Joystick(order);
        	switch(tempStick.getAxisCount()) {
        	case 8:
        		custom = true;
        		if (customstick == null) {
            		customstick = new Joystick(order);
            		addCustomButtons();
            		Robot.Log("USB [" + order +"] is assigned to the custom stick", 1);
        		}
        		break;
        	case 4:
        		flight = true;
        		if (flightstick == null) {
            		flightstick = new Joystick(order);
            		addFlightstickButtons();
            		Robot.Log("USB [" + order +"] is assigned to the flight stick", 1);
        		}
        		break;
        	case 6:
        		xbox = true;
        		if (xboxstick == null) {
            		xboxstick = new Joystick(order);
            		addXboxButtons();
            		Robot.Log("USB [" + order +"] is assigned to the xbox stick", 1);
        		}
        		break;
        	case 0:
        	    // This controller is unplugged.
        	    break;
        	default:
        		Robot.Log("We have no idea what USB [" + order + "] is!", 1);
        		break;
        	}
    	}
		if (!flight) {
			flightstick = null;
		}
		if (!xbox) {
			xboxstick = null;
		}
		if (!custom) {
			customstick = null;
		}
	}
	
	private void addFlightstickButtons() {
		if (flightstick != null) {
    		// Logitech 3D Flightstick
    		flightInvertDrive = new JoystickButton(flightstick, FLIGHT_INVERT);
    		flightInvertDrive.whenPressed(new switchtobackcamera());
    		flightInvertDrive.whenReleased(new switchtofrontcamera());

            flightTelescopeUp = new JoystickButton(flightstick, FLIGHT_TEL_UP);
            flightTelescopeUp.whileHeld(new TelescopeUp());
            flightTelescopeDown = new JoystickButton(flightstick, FLIGHT_TEL_DOWN);
            flightTelescopeDown.whileHeld(new TelescopeDown());
            flightTelescopeHigh = new JoystickButton(flightstick, FLIGHT_TEL_HIGH);
            flightTelescopeHigh.whenPressed(new LiftMove(TELE_PRESET_HIGH));
	        flightTelescopeMedium = new JoystickButton(flightstick, FLIGHT_TEL_MED);
	        flightTelescopeMedium.whenPressed(new LiftMove(TELE_PRESET_MEDIUM));
            flightTelescopeLow = new JoystickButton(flightstick, FLIGHT_TEL_LOW);
            flightTelescopeLow.whenPressed(new LiftMove(TELE_PRESET_LOW));
	
	        flightWristUp = new JoystickButton(flightstick, FLIGHT_WRIST_UP);
	        flightWristUp.whenPressed(new WristMove(WRIST_PRESET_UP));
	        flightWristDown = new JoystickButton(flightstick, FLIGHT_WRIST_DOWN);
	        flightWristDown.whenPressed(new WristMove(WRIST_PRESET_DOWN));
	        
            flightClaw = new JoystickButton(flightstick, FLIGHT_CLAW);
            flightClaw.whenPressed(new ClawToggle());
            
	        flightClimb = new JoystickButton(flightstick, FLIGHT_CLIMB);
	        flightClimb.whileHeld(new Climb());
	        flightClimb.whenReleased(new StopClimb());
    	}
	}
	
	private void addXboxButtons() { 	
    	if (xboxstick != null) {
	        // Xbox 360 gamepad
    		xboxTelescopeHigh = new JoystickButton(xboxstick, XBOX_TEL_HIGH);
            xboxTelescopeHigh.whenPressed(new LiftMove(TELE_PRESET_HIGH));
	        xboxTelescopeMedium = new JoystickButton(xboxstick, XBOX_TEL_MED);
	        xboxTelescopeMedium.whenPressed(new LiftMove(TELE_PRESET_MEDIUM));
            xboxTelescopeLow = new JoystickButton(xboxstick, XBOX_TEL_LOW);
            xboxTelescopeLow.whenPressed(new LiftMove(TELE_PRESET_LOW));

            xboxClaw = new JoystickButton(xboxstick, XBOX_CLAW);
            xboxClaw.whenPressed(new ClawToggle());
    	}
	}
	
	private void addCustomButtons() {   	
    	if (customstick != null) {
    		// Custom PowerUp console
    		customClaw = new JoystickButton(customstick, CUSTOM_CLAW);
    		customClaw.whenPressed(new ClawOpen());
    		customClaw.whenReleased(new ClawClose());
    		
    		customClimb = new JoystickButton(customstick, CUSTOM_CLIMB);
	        customClimb.whileHeld(new Climb());
	        customClimb.whenReleased(new StopClimb());
    	}
	}
    
    public void RunAxes() {
    	// These happen here so that subsystems don't get taken over.
    	
    	// Driving
    	if (flightstick != null) {
    		double acceleration = flightstick.getRawAxis(FLIGHT_AXIS_ACCELERATE);
            double steering = -flightstick.getRawAxis(FLIGHT_AXIS_STEER);
            double driveSensitivity = (flightstick.getRawAxis(FLIGHT_AXIS_SENSITIVITY) / -4.0) + 0.75;
            double steeringSensitivity = (flightstick.getRawAxis(FLIGHT_AXIS_SENSITIVITY) / -3.0) + 0.66;
            double reverse = (flightstick.getRawButton(FLIGHT_INVERT)) ? -1.0 : 1.0;
            Robot.drive.arcadeDrive(acceleration * driveSensitivity * reverse, steering * steeringSensitivity);
    	}
    	
    	// Telescope
    	// Both xbox and custom have controls for this. 
    	if (xboxstick != null) {
    		double movementSpeed = -xboxstick.getRawAxis(XBOX_AXIS_TEL);
    		if (Math.abs(movementSpeed) > 0.2) {
    			Robot.lift.Move(movementSpeed);
    		}
    	} else if (customstick != null) {
    		double targetPosition = (customstick.getRawAxis(CUSTOM_AXIS_TEL) / 2.0) + 0.5;
    		Robot.lift.moveTelescope(targetPosition);
    	}
    	
    	// Wrist
    	// Both xbox and custom have controls for this
    	if (xboxstick != null) {
    		int pov = xboxstick.getPOV(0);
            if (Contains(XBOX_POV_WRIST_UP, pov)) {
                (new WristMove(WRIST_PRESET_UP)).start();
            }
    		if (Contains(XBOX_POV_WRIST_DOWN, pov)) {
    			(new WristMove(WRIST_PRESET_DOWN)).start();
    		}
    	} else if (customstick != null) {
    		double targetPosition = (customstick.getRawAxis(CUSTOM_AXIS_WRIST) * -1.0) + 1.0;
    		(new WristMove(targetPosition)).start();
    	}
    }
    
    public void Indicate(Indication status, boolean value) {
        if (status == Indication.WristDown) {
        	if (xboxstick != null) {
        		Robot.oi.xboxstick.setRumble(RumbleType.kLeftRumble, value ? 0.8 : 0.0);
        		Robot.oi.xboxstick.setRumble(RumbleType.kRightRumble, value ? 0.8 : 0.0);
        	}
        }
    }
    
    // int[] contains int helper
    private static boolean Contains(int[] haystack, int needle) {
        for (int i : haystack) {
            if (needle == i) {
                return true;
            }
        }
        return false;
    }
}
