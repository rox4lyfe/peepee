package frc.robot.commands;

import harkerrobolib.commands.IndefiniteCommand;
import frc.robot.subsystems.Shooter;

public class FlywheelManual extends IndefiniteCommand {

    private static double VELOCITY_MULTIPLIER = 1;

    public FlywheelManual() {
        addRequirements(Shooter.getInstance());

    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {

        double rightOutput = MathUtil.mapJoystickOutput(OI.getInstance().getDriverGamepad().getRightTrigger(), OI.TRIGGER_DEADBAND);
        double leftOutput = MathUtil.mapJoystickOutput(OI.getInstance().getDriverGamepad().getLeftTrigger(), OI.TRIGGER_DEADBAND);

        rightOutput *= VELOCITY_MULTIPLIER * Shooter.MAX_VELOCITY;
    }

    @Override
    public void end(boolean interrupted) {
        Shooter.getInstance().spinShooterPercentOutput(0);

    }


    
}