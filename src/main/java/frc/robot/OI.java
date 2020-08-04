package frc.robot;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.commands.indexer.SpinIndexerManual;
import frc.robot.subsystems.Intake;
import harkerrobolib.wrappers.XboxGamepad;

/**
 * Takes in input from the controller
 * 
 * @author Aimee Wang
 * @since June 16, 2020
 */
public class OI {
    private static OI instance;

    private XboxGamepad driverGamepad;
    private XboxGamepad operatorGamepad;

    public static final double JOYSTICK_DEADBAND = 0.1;
    public static final double TRIGGER_DEADBAND = 0.1;

    private OI() {

        driverGamepad = new XboxGamepad(RobotMap.DRIVER_PORT);
        operatorGamepad = new XboxGamepad(RobotMap.OPERATOR_PORT);

        initBindings();
    }

    private void initBindings() {
        operatorGamepad.getButtonA().whilePressed(new InstantCommand(() -> {
            Intake.getInstance().setOutput(1, Intake.IntakeDirection.IN, true);
            }, Intake.getInstance()));
        operatorGamepad.getButtonB().whilePressed(new SpinIndexerManual(1, false));
        operatorGamepad.getButtonX().whilePressed(new SpinIndexerManual(1, true));
    }

    public XboxGamepad getDriverGamepad() {
        return driverGamepad;
    }

    public XboxGamepad getOperatorGamepad() {
        return operatorGamepad;
    }

    public static OI getInstance() {
        if(instance == null)
            instance = new OI();

        return instance;
    }
}