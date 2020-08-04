package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Intake;
import harkerrobolib.commands.IndefiniteCommand;
import harkerrobolib.subsystems.HSIntake.IntakeDirection;

public class SpinIntakeAndIndexer extends IndefiniteCommand {
    
    private static final double SPINE_PERCENT_OUTPUT = 1;
    private static final double AGITATOR_PERCENT_OUTPUT = 1;
    private static final double INTAKE_PERCENT_OUTPUT = 1;

    private static final double INTAKE_JAM_PERCENT_OUTPUT = 0.5;

    private static final int AGITATOR_CYCLE_DURATION = 5000;
    private static final int AGITATOR_CYCLE_COOLDOWN = 1000;

    private static final int STALL_CURRENT = 10;
    private static final int STALL_MIN_VELOCITY = 100;

    private IntakeDirection direction;

    public SpinIntakeAndIndexer(IntakeDirection direction) {
        addRequirements(Indexer.getInstance());
        addRequirements(Intake.getInstance());
        this.direction = direction;
    }

    public void execute() {
        if(Intake.getInstance().isStalling(STALL_CURRENT, STALL_MIN_VELOCITY)) {
            Intake.getInstance().setOutput(INTAKE_JAM_PERCENT_OUTPUT, IntakeDirection.OUT, true);
        }
        else {
            Intake.getInstance().setOutput(INTAKE_PERCENT_OUTPUT, direction, true);
            Indexer.getInstance().spinSpine(SPINE_PERCENT_OUTPUT);
            if(direction == IntakeDirection.IN)
                Indexer.getInstance().agitate(AGITATOR_PERCENT_OUTPUT, AGITATOR_CYCLE_DURATION, AGITATOR_CYCLE_COOLDOWN);
        }
    }   

    public void end(boolean interrupted) {
        Indexer.getInstance().spinSpine(0);
        Indexer.getInstance().spinAgitator(0);
        Intake.getInstance().getMasterMotor().set(ControlMode.Disabled, 0);
    }
}