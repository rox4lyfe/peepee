package frc.robot.subsystems;

import harkerrobolib.subsystems.HSSwerveDrivetrain;
import harkerrobolib.wrappers.HSPigeon;
import edu.wpi.first.wpilibj.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.TalonFXInvertType;

/**
 * Manages the swerve drivetrain.
 * 
 * @author Arjun Dixit
 * @since  June 16, 2020
 */
public class Drivetrain extends HSSwerveDrivetrain {
    private static Drivetrain instance;

    public static final int DRIVE_VELOCITY_SLOT = 0;
    public static final int ANGLE_POSITION_SLOT = 0;
    public static final double MAX_DRIVE_VELOCITY = 4;
    public static final double MAX_ROTATION_VELOCITY = 3*Math.PI;

    private static final int[] offsets = {9084, 5951, 1582, 5891}; // TL TR BL BR
    private static TalonFXInvertType[] driveInverts = {TalonFXInvertType.Clockwise, TalonFXInvertType.Clockwise, TalonFXInvertType.Clockwise, TalonFXInvertType.Clockwise};
    private static boolean[] angleInverts = {true, true, true, false};
    private static boolean[] driveSensorPhases = {true, true, false, false};
    private static boolean[] angleSensorPhases = {true, true, true, false};
    private static final double DT_WIDTH = 0.535; //meters
    private static final double DT_LENGTH = 0.645; //meters
    private static final double WHEEL_DIAMETER = 4; //inches
    private static final double GEAR_RATIO = 6;

    private static final double ANGLE_POSITION_KP = 1.1;
    private static final double ANGLE_POSITION_KI = 0.0;
    private static final double ANGLE_POSITION_KD = 11;

    private static final double DRIVE_VELOCITY_KF = 0.06; //theoretical: 0.034;
    private static final double DRIVE_VELOCITY_KP = 0.7;
    private static final double DRIVE_VELOCITY_KI = 0.0;
    private static final double DRIVE_VELOCITY_KD = 10;

    private static final double DRIVE_RAMP_RATE = 0.1;
    private static final double ANGLE_RAMP_RATE = 0.2;

    public static final double PIGEON_kP = 0.05;
    


    private Drivetrain() {
        super(initSwerveModules(RobotMap.CAN_IDS.DRIVE_IDS, RobotMap.CAN_IDS.ANGLE_IDS, 
            offsets, driveInverts, driveSensorPhases, angleInverts, angleSensorPhases,
            WHEEL_DIAMETER, GEAR_RATIO), new HSPigeon(RobotMap.CAN_IDS.PIGEON_ID), DT_WIDTH, DT_LENGTH);

        setupPositionPID(ANGLE_POSITION_SLOT, ANGLE_POSITION_KP, ANGLE_POSITION_KI, ANGLE_POSITION_KD, ANGLE_RAMP_RATE);
        setupVelocityPID(DRIVE_VELOCITY_SLOT, DRIVE_VELOCITY_KF, DRIVE_VELOCITY_KP, DRIVE_VELOCITY_KI, DRIVE_VELOCITY_KD, DRIVE_RAMP_RATE);
    }

    @Override
    public void setDrivetrainVelocity(SwerveModuleState tl, SwerveModuleState tr, SwerveModuleState bl,
            SwerveModuleState br, boolean isPercentOutput, boolean isMotionProfile) {
        super.setDrivetrainVelocity(tl, tr, bl, br, isPercentOutput, isMotionProfile);

        SmartDashboard.putNumber("tl angle", tl.angle.getDegrees());
        SmartDashboard.putNumber("tl manitude", tl.speedMetersPerSecond);
    }


    public static Drivetrain getInstance() {
        if(instance == null)
            instance = new Drivetrain();
        return instance;
    }

}