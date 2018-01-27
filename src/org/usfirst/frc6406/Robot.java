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

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.HashMap;
import java.util.Map;

import org.usfirst.frc6406.commands.*;
import org.usfirst.frc6406.subsystems.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in 
 * the project.
 */
public class Robot extends TimedRobot {

    Command autonomousCommand;
    SendableChooser<Command> chooser = new SendableChooser<>();

    public static OI oi;
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public static claw claw;
    public static wrist wrist;
    public static Drive drive;
    public static Sensors sensors;
    public static Climb climb;
    public static Lift lift;
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    String pathString;
    Map<String,String> autoDirections = new HashMap<String,String>();
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit() {
        RobotMap.init();
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        claw = new claw();
        wrist = new wrist();
        drive = new Drive();
        sensors = new Sensors();
        climb = new Climb();
        lift = new Lift();

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        // OI must be constructed after subsystems. If the OI creates Commands
        //(which it very likely will), subsystems are not guaranteed to be
        // constructed yet. Thus, their requires() statements may grab null
        // pointers. Bad news. Don't move it.
        InitSelectionButtons();
        InitAutoDirections();
        oi = new OI();

        // Add commands to Autonomous Sendable Chooser
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=AUTONOMOUS

        chooser.addDefault("Autonomous Command", new AutonomousCommand());

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=AUTONOMOUS
        SmartDashboard.putData("Auto mode", chooser);
    }
    void InitSelectionButtons() {
		String[] locations = {"left","center","right"};
		String[] priority = {"switch","scale","transferStation"};
		SmartDashboard.setDefaultStringArray("DriverStationPosition", locations);
		SmartDashboard.setDefaultStringArray("PrioritySelect", priority);
	}
    void InitAutoDirections() {
    	autoDirections.put("LSCL", "D1:R7:D1:T90:P");//This is our example case (Drive 1 meter:Begin raise 7 feet:Drive 1 meter:Turn 90 degrees:Place(Dump))
    	autoDirections.put("LSCR", "Do this");
    	autoDirections.put("LSWL", "Do this");
    	autoDirections.put("LSWR", "Do this");
    	autoDirections.put("CSCL", "Do this");
    	autoDirections.put("CSCR", "Do this");
    	autoDirections.put("CSWL", "Do this");
    	autoDirections.put("CSWR", "Do this");
    	autoDirections.put("RSCL", "Do this");
    	autoDirections.put("RSCR", "Do this");
    	autoDirections.put("RSWL", "Do this");
    	autoDirections.put("RSWR", "Do this");
    }

    /**
     * This function is called when the disabled button is hit.
     * You can use it to reset subsystems before shutting down.
     */
    @Override
    public void disabledInit(){

    }

    @Override
    public void disabledPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void autonomousInit() {
        autonomousCommand = chooser.getSelected();
        ParseGameData();
        // schedule the autonomous command (example)
        if (autonomousCommand != null) autonomousCommand.start();
    }
	void ParseGameData(){//Shrink this down after testing
		String position = SmartDashboard.getString("DriverStationPosition", "unassigned");//When unassigned, need contingency
		String priority = SmartDashboard.getString("PrioritySelect", "unassigned");//When unassigned, hierarchy is Switch>Scale>CrossLine>TransferStation
		String gameData = DriverStation.getInstance().getGameSpecificMessage();
		String char0 = (position == "right") ? "R":(position == "left") ? "L":(position == "center") ? "C":"0"; 
		String char1 = (priority == "switch") ? "SW":(priority == "scale") ? "SC":"0";
		int selectedElement = (char1 == "SW") ? 0:1;//THIS ONLY SELECTS SWITCH AND SCALE
		String char2 = gameData.substring(selectedElement,selectedElement++);
		pathString = (char0+=char1+=char2);
		String autoPath = autoDirections.get(pathString);
		StartAutoPath(autoPath);
	}
	void StartAutoPath(String autoPath) {
		String[] splitDirections = autoPath.split(":");
		for(int i = 0; i<=splitDirections.length; i++) {
			String firstChar = splitDirections[i].substring(0,1);
			Double value = Double.parseDouble(splitDirections[i].substring(1));
			if (firstChar == "D") {
				//Call Drive(value) here
			}
			if (firstChar == "T") {
				//Call Turn(value) here
			}
			if (firstChar == "R") {
				//Call Raise(value) here
			}
			if (firstChar == "P") {
				//Call Drop() here //IF THERE IS AN ERROR, TRY AND ADD A '0' AT THE END OF THE P STRING
			}
		}
		//Do ending stuff here
	}
    /**
     * This function is called periodically during autonomous
     */
    @Override
    public void autonomousPeriodic() {
    	//HACK TO AVOID UPDATRE ERRORS
    	RobotMap.driveRobotDrive.tankDrive(0, 0);
        Scheduler.getInstance().run();
    }

    @Override
    public void teleopInit() {
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) autonomousCommand.cancel();
    }

    /**
     * This function is called periodically during operator control
     */
    @Override
    public void teleopPeriodic() {
    	//HACK TO AVOID UPDATRE ERRORS
    	RobotMap.driveRobotDrive.tankDrive(0, 0);

        Scheduler.getInstance().run();
    }
}
