package frc.robot;

/**
 * Stores important constants and values that pertain to the whole robot.
 * 
 * @author Arjun Dixit
 * @author Aimee Wang
 * @author Chirag Kaushik
 * @since June 16, 2020
 */
public class RobotMap {

    public static class CAN_IDS {

        public static final int SHOOTER_FORWARD_SOLENOID = 0;
        public static final int SHOOTER_BACKWARD_SOLENOID = 0;
        public static final int SHOOTER_MASTER_ID = 0;
        public static final int SHOOTER_FOLLOWER_ID = 0;

        public static final int INTAKE_FORWARD_SOLENOID = 0;
        public static final int INTAKE_BACKWARD_SOLENOID = 0;

        public static final int SPINNER_FORWARD_SOLENOID = 0;
        public static final int SPINNER_BACKWARD_SOLENOID = 0;

        public static final int INDEXER_FORWARD_SOLENOID = 0;
        public static final int INDEXER_BACKWARD_SOLENOID = 0;

        public static final int INTAKE_ID = 0;
        public static final int SPINNER_ID = 0;

        public static final int[] DRIVE_IDS = {0, 2, 4, 6}; // TL, TR, BL, BR
        public static final int[] ANGLE_IDS = {1, 3, 5, 7};

        public static final int PIGEON_ID = 0;

        public static final int INTAKE_LEFT_ID = 0;
        public static final int INTAKE_RIGHT_ID = 0;

        public static final int SPINE_ID = 0;
        public static final int AGITATOR_ID = 0;

 
    }

    public static final int DRIVER_PORT = 0;
    public static final int OPERATOR_PORT = 1;
}