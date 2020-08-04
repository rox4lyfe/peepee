package frc.robot.commands.intake;
import frc.robot.OI;
import frc.robot.subsystems.Intake;
import harkerrobolib.commands.IndefiniteCommand;
import harkerrobolib.subsystems.HSIntake.IntakeDirection;

/**
 * Controls the intake using a left and right trigger and Velocity PID
 * 
 * @author Aimee Wang
 * @since 7/7/20
 * 
 */
public class IntakeManual extends IndefiniteCommand {
    private static final double MIN_CURRENT_DRAW = 0;
    private static final int JAMMED_VELOCITY = 0;

    private double rightOutput = -0.5;
    private double leftOutput = 0.5;
    private double stallingOutput = 0.3;

    public IntakeManual() {
        addRequirements(Intake.getInstance());
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        if(OI.getInstance().getDriverGamepad().getRightTrigger() > OI.TRIGGER_DEADBAND || OI.getInstance().getOperatorGamepad().getRightTrigger() > OI.TRIGGER_DEADBAND) {          
            Intake.getInstance().setOutput(rightOutput, IntakeDirection.IN, false);
        }
        else if(OI.getInstance().getDriverGamepad().getLeftTrigger() > OI.TRIGGER_DEADBAND ||  OI.getInstance().getOperatorGamepad().getLeftTrigger() > OI.TRIGGER_DEADBAND) {
            Intake.getInstance().setOutput(leftOutput, IntakeDirection.OUT, false);
        }
        else {
           Intake.getInstance().setOutput(0, IntakeDirection.IN, false);
        }

        if(Intake.getInstance().isStalling(MIN_CURRENT_DRAW, JAMMED_VELOCITY)) {
            Intake.getInstance().setOutput(stallingOutput, IntakeDirection.OUT, false);
        }
         
    }

    @Override
    public void end(boolean interrupted) {
        Intake.getInstance().setOutput(0, IntakeDirection.IN, false);
    }
}