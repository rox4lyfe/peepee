package frc.robot.auto;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.drivetrain.SwerveManual;

/**
 * Contains autonomous commands to run
 */
public class Autons {

    public static CommandBase getAutonCommand() {
        return autonCommand;
    }

    private static final SequentialCommandGroup autonCommand = new SequentialCommandGroup(new SwerveManual());





    
    
}