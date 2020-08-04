package frc.robot.subsystems;

import frc.robot.RobotMap;
import harkerrobolib.subsystems.HSIntake;
import harkerrobolib.wrappers.HSFalcon;


public class Intake extends HSIntake<HSFalcon> {
    private static Intake instance;

    public Intake() {
        super(new HSFalcon(RobotMap.CAN_IDS.INTAKE_LEFT_ID), new HSFalcon(RobotMap.CAN_IDS.INTAKE_RIGHT_ID));
    }

    public static Intake getInstance() {
        if (instance == null)
            instance = new Intake();
        return instance;
    }

}