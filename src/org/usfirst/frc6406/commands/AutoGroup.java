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

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc6406.RobotMap;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Ultrasonic;

/**
 *
 */
public class AutoGroup extends CommandGroup {
	
	private static final double METRES_TO_PULSES = 689.44 * 0.6857; // Encoders seem to be wrong by 28% (70% speed)
    public AutoGroup() {
		ParseGameData();
	}
    
    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    }

	
	public void ParseGameData(){//Shrink this down after testing
		String position = RobotMap.positionChooser.getSelected();;//When unassigned, need contingency
		String priority = RobotMap.priorityChooser.getSelected();//When unassigned, hierarchy is Switch>Scale>CrossLine>TransferStation
		String gameData = DriverStation.getInstance().getGameSpecificMessage();
		String char0 = (position == "right") ? "R":(position == "left") ? "L":(position == "center") ? "C":"0"; 
		String char1 = (priority == "switch") ? "SW":(priority == "scale") ? "SC":"0";
		int selectedElement = (char1 == "SW") ? 0:1;//THIS ONLY SELECTS SWITCH AND SCALE
		char char2 = gameData.charAt(selectedElement);
	
		String pathString = (char0+=char1+=char2);
		String autoPath = RobotMap.autoDirections.get(pathString);
		StartAutoPath(autoPath);
	}
	
	public void StartAutoPath(String autoPath) {
		if (autoPath == null) {
			return;
		}
		String[] splitDirections = autoPath.split(":");
		for(int i = 0; i<splitDirections.length; i++) {
			String firstChar = splitDirections[i].substring(0,1);
		
			
			if (firstChar.equals("D")) {
				double value = Double.parseDouble(splitDirections[i].substring(1));
				addSequential(new DriveForward((float)value*METRES_TO_PULSES));
			}
			else if (firstChar.equals("T")) {
				double value = (Double.parseDouble(splitDirections[i].substring(1))*-1);
					
				addSequential(new Turn(value));
		
			}
			if (firstChar.equals("d")) {
				double value = Double.parseDouble(splitDirections[i].substring(1));
				addSequential(new PidDrive((float)value*METRES_TO_PULSES));
			}
			else if (firstChar.equals("t")) {
				double value = Double.parseDouble(splitDirections[i].substring(1))*-1;
				addSequential(new PidTurn(value));
		
			}
			else if (firstChar.equals("r")) {
				//Call Raise(value) here. 0.014 = inch
				double value = Double.parseDouble(splitDirections[i].substring(1));
				addSequential(new liftmove(value));
			}
			else if (firstChar.equals("p")) {
				//Call Drop() here //IF THERE IS AN ERROR, TRY AND ADD A '0' AT THE END OF THE P STRING
				double value = Double.parseDouble(splitDirections[i].substring(1));
				addSequential(new Drop(value));
			}else {
				SmartDashboard.putString("Error","Unexpected character ["+firstChar+"] in auto path");
			}
			
		}
	}
	
}

