package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.DoubleSolenoid;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.StatorCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.TalonFXInvertType;

import harkerrobolib.subsystems.HSFlywheel;
import harkerrobolib.util.Gains;
import harkerrobolib.wrappers.HSFalcon;

public class Shooter extends HSFlywheel<HSFalcon> {

    private static Shooter instance;

    public static final double MAX_VELOCITY = 110;
    private static boolean sensorPhase = false;
    private static boolean masterInvert = false;
    private static boolean followerInvert = false;

    private static double WHEEL_DIAMETER;
    private static int TICKS_PER_REVOLUTION;
    private static double GEAR_RATIO;

    private static final int VELOCITY_PID_SLOT = 0;

    private static final int VOLTAGE_COMP = 10;

    private static final int CURRENT_LIMIT = 0;
    private static final int PEAK_CURRENT = 0;
    private static final int PEAK_TIME = 0;

    private static final double VELOCITY_KF = 0.06; //theoretical: 0.034;
    private static final double VELOCITY_KP = 0.7;
    private static final double VELOCITY_KI = 0.0;
    private static final double VELOCITY_KD = 10;

    private Shooter() {
        super(new HSFalcon(RobotMap.CAN_IDS.SHOOTER_MASTER_ID),
            new HSFalcon(RobotMap.CAN_IDS.SHOOTER_FOLLOWER_ID), 
            new DoubleSolenoid(RobotMap.CAN_IDS.SHOOTER_FORWARD_SOLENOID, RobotMap.CAN_IDS.SHOOTER_BACKWARD_SOLENOID),
            WHEEL_DIAMETER, TICKS_PER_REVOLUTION, GEAR_RATIO);
        setupFlywheel(sensorPhase, masterInvert, followerInvert, VELOCITY_PID_SLOT, 
                      new Gains().kF(VELOCITY_KF).kP(VELOCITY_KP).kI(VELOCITY_KI).kD(VELOCITY_KD),
                      VOLTAGE_COMP, new StatorCurrentLimitConfiguration(true, CURRENT_LIMIT, PEAK_CURRENT, PEAK_TIME));
    }

    public static Shooter getInstance() {
        if (instance == null)
            instance = new Shooter();
        return instance;
    }
}