package frc.robot.subsystems;
import frc.robot.RobotMap;
import harkerrobolib.wrappers.HSTalon;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatorCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import harkerrobolib.subsystems.HSIndexer;
import harkerrobolib.util.Gains;

/**
 * The indexer transfers and stores power cells from the intake to the shooter.
 * 
 * spine -> forward / back
 * agitator -> forward/backward oscillation when forwards, otherwise backwards
 * 
 * @author Arjun Dixit
 * @author Chirag Kaushik
 * @author Aimee Wang
 * @since 
 */
public class Indexer extends HSIndexer<HSTalon> {
    private static Indexer instance;
    
    private DoubleSolenoid solenoid;
    
    private HSTalon spine;
    private VictorSPX agitator;

    private static boolean SPINE_INVERT;
    private static boolean AGITATOR_INVERT;

    private static final boolean SPINE_SENSOR_PHASE = false;
    
    private static final double RAMP_RATE = 0.1;
    private static final double VOLTAGE_COMPENSATION = 10;

    private static final double CURRENT_CONTINUOUS_LIMIT = 0;
    private static final double CURRENT_PEAK_LIMIT = 0;
    private static final double CURRENT_PEAK_DURATION = 0;

    private static final double OUTPUT_MULTIPLIER = 1;

    public static final int AGITATOR_CYCLE_DURATION = 0;
    public static final int AGITATOR_ON_DURATION = 0;

    private DigitalInput indexerSensor;
    private DigitalInput shooterSensor;

    public static final int INDEXER_SENSOR_ID = 0;
    public static final int SHOOTER_SENSOR_ID = 0;

    private static final int VELOCITY_PID_SLOT = 0;

    private static final double INDEXER_VELOCITY_KF = 0.06; //theoretical: 0.034;
    private static final double INDEXER_VELOCITY_KP = 0.7;
    private static final double INDEXER_VELOCITY_KI = 0.0;
    private static final double INDEXER_VELOCITY_KD = 10;

    public enum IndexerState {
        OPEN(DoubleSolenoid.Value.kReverse), CLOSED(DoubleSolenoid.Value.kForward);
        public DoubleSolenoid.Value value;
        private IndexerState(DoubleSolenoid.Value value) {
            this.value = value;
        }
    }

    private Indexer() {
        super(new HSTalon(RobotMap.CAN_IDS.SPINE_ID), new VictorSPX(RobotMap.CAN_IDS.AGITATOR_ID), new DoubleSolenoid(RobotMap.CAN_IDS.INDEXER_FORWARD_SOLENOID, RobotMap.CAN_IDS.INDEXER_BACKWARD_SOLENOID));
        indexerSensor = new DigitalInput(INDEXER_SENSOR_ID);
        shooterSensor = new DigitalInput(SHOOTER_SENSOR_ID);
        setupMotors();
    }
    
    private void setupMotors() {

        Gains constants = new Gains().kP(INDEXER_VELOCITY_KP).kI(INDEXER_VELOCITY_KI).kD(INDEXER_VELOCITY_KD).kF(INDEXER_VELOCITY_KF);

        setUpMotorControllers(VELOCITY_PID_SLOT, constants, 
                            new StatorCurrentLimitConfiguration(true, CURRENT_CONTINUOUS_LIMIT, CURRENT_PEAK_LIMIT, CURRENT_PEAK_DURATION), 
                            VOLTAGE_COMPENSATION, AGITATOR_INVERT, SPINE_INVERT, SPINE_SENSOR_PHASE);
    }

    public void spinSpine(double percentOutput) {
        if(percentOutput == 0)
            spine.set(ControlMode.Disabled, 0);
        else
            spine.set(ControlMode.PercentOutput, percentOutput * OUTPUT_MULTIPLIER);
    }

    public void spinAgitator(double percentOutput) {
        if(percentOutput == 0)
            agitator.set(ControlMode.Disabled, 0);
        else
            agitator.set(ControlMode.PercentOutput, percentOutput * OUTPUT_MULTIPLIER);
    }

    public void agitate(double output, int cycleDuration, int cooldown)
    {
        long time = System.currentTimeMillis();

        if(time % cycleDuration < cooldown)
            spinAgitator(0); 
        else
            spinAgitator(output);
    }

    public void toggleSolenoid() {
        super.toggleSolenoid(solenoid);
    }

    public void setSolenoidState(IndexerState state) {
        solenoid.set(state.value);
    }

    public DigitalInput getShooterSensor() {
        return shooterSensor;
    }

    public DigitalInput getIndexerSensor() {
        return indexerSensor;
    }

    public static Indexer getInstance() {
        if(instance == null)
            instance = new Indexer();
        return instance;
    }
}