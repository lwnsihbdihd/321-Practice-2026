package frc.robot.subsystems.hood;

import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.DegreesPerSecond;
import static edu.wpi.first.units.Units.DegreesPerSecondPerSecond;
import static edu.wpi.first.units.Units.Volts;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.epilogue.Logged;
import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Hood {

    private double kG = 0;
    private double kS = 0;
    private double kV = 0;
    
    private ArmFeedforward hoodFeedforward = new ArmFeedforward(kS, kG, kV);

    public Hood() {
        configureMotors();
    }

    TalonFX hoodMotor = new TalonFX(HoodConstants.kHoodMotorID);

    final CurrentLimitsConfigs hoodCurrentLimitsConfigs = 
            new CurrentLimitsConfigs()
                .withSupplyCurrentLimit(HoodConstants.kSupplyCurrentLimit)
                .withSupplyCurrentLimitEnable(HoodConstants.kSupplyLimitEnabled)
                .withStatorCurrentLimit(HoodConstants.kSupplyCurrentLimit)
                .withStatorCurrentLimitEnable(HoodConstants.kSupplyLimitEnabled);

    final MotorOutputConfigs hoodMotorOutputConfigs = 
            new MotorOutputConfigs()
                .withInverted(HoodConstants.kInversion)
                .withNeutralMode(HoodConstants.kNeutralMode);
              
    final FeedbackConfigs hoodFeedbackConfigs = 
            new FeedbackConfigs()
                .withSensorToMechanismRatio(HoodConstants.kGearRatio);

    final TalonFXConfiguration motorConfigs = 
            new TalonFXConfiguration()
            .withCurrentLimits(hoodCurrentLimitsConfigs)
            .withMotorOutput(hoodMotorOutputConfigs)
            .withFeedback(hoodFeedbackConfigs);  

    private void configureMotors() {
        hoodMotor.getConfigurator().apply(motorConfigs);
    }

    private double kP = 0;
    private double kI = 0;
    private double kD = 0;

    private TrapezoidProfile.Constraints hoodConstraints = 
                new TrapezoidProfile.Constraints(
                    HoodConstants.kMaxVelocity.in(DegreesPerSecond), 
                    HoodConstants.kMaxAcceleration.in(DegreesPerSecondPerSecond));

    private ProfiledPIDController hoodController = 
                new ProfiledPIDController(
                    kP, 
                    kI, 
                    kD, 
                    hoodConstraints);

    @Logged (name = "hoodPitch")
    public Angle getAngle() {
        Angle angle = hoodMotor.getPosition().getValue();
        return angle;
    }

    @Logged (name = "atTargetAngle")
    public boolean atTargetAngle() {
        boolean atTargetAngle = Math.abs(getAngle().in(Degrees) - targetAngle.in(Degrees)) < HoodConstants.kAngleTolerance.in(Degrees);

        return atTargetAngle;
    }

    @Logged (name = "hoodVoltage")
    public Voltage getVoltage() {
        return this.hoodMotor.getMotorVoltage().getValue();
    }

    @Logged (name = "hoodCurrent")
    public Current getCurrent() {
        return this.hoodMotor.getStatorCurrent().getValue();
    }

    // @Override
    public void periodic() {
        SmartDashboard.putNumber("Hood Angle", hoodMotor.getPosition().getValue().in(Degrees));
    }

    // public Angle currentAngle = Degrees.of(30);
    public Angle targetAngle = Degrees.of(45);
    // private Voltage desiredVoltage = Volts.of(hoodController.calculate(currentAngle.in(Degrees), targetAngle.in(Degrees)));

    // public void goToAngle(Angle desiredAngle) {
    //     Angle currentAngle = getAngle();
    //     Voltage desiredVoltage = Volts.of(hoodController.calculate(currentAngle.in(Degrees), desiredAngle.in(Degrees)));
        
    //     hoodMotor.setVoltage(desiredVoltage.in(Volts));
    // }

    public void goToAngle(Angle targetAngle) {
        Voltage calculatedPIDVoltage = Volts.of(hoodController.calculate(getAngle().in(Degrees), targetAngle.in(Degrees)));
        Voltage calculatedFFVoltage = Volts.of(hoodFeedforward.calculate(targetAngle.in(Degrees), 0));
        Voltage desiredVoltage = Volts.of(calculatedFFVoltage.in(Volts) + calculatedPIDVoltage.in(Volts));
        hoodMotor.setVoltage(desiredVoltage.in(Volts));
    }

}
