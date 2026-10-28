package frc.robot.subsystems.pivot;

import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.DegreesPerSecond;
import static edu.wpi.first.units.Units.DegreesPerSecondPerSecond;
import static edu.wpi.first.units.Units.Newton;
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
import frc.robot.subsystems.hood.HoodConstants;

public class Pivot {

    private double kG = 0;
    private double kS = 0;
    private double kV = 0;

    private ArmFeedforward pivotFeedforward = new ArmFeedforward(kS, kG, kV);

    public Pivot() {
        configurePivotMotor();
    }

    TalonFX pivotMotor = new TalonFX(PivotConstants.kPivotMotorID);

    final CurrentLimitsConfigs pivotCurrentLimitsConfigs =
            new CurrentLimitsConfigs() 
                .withSupplyCurrentLimit(PivotConstants.kSupplyCurrentLimit)
                .withSupplyCurrentLimitEnable(PivotConstants.kSupplyLimitEnabled)
                .withStatorCurrentLimit(PivotConstants.kSupplyCurrentLimit)
                .withStatorCurrentLimitEnable(PivotConstants.kSupplyLimitEnabled);

    final MotorOutputConfigs pivotMotorOutputConfigs = 
            new MotorOutputConfigs()
                .withInverted(PivotConstants.kInversion)
                .withNeutralMode(PivotConstants.kNeutralMode);

    final FeedbackConfigs pivotFeedbackConfigs = 
            new FeedbackConfigs()
                .withSensorToMechanismRatio(PivotConstants.kGearRatio);

    final TalonFXConfiguration pivotMotorConfigs = 
            new TalonFXConfiguration()
                .withCurrentLimits(pivotCurrentLimitsConfigs)
                .withMotorOutput(pivotMotorOutputConfigs)
                .withFeedback(pivotFeedbackConfigs);

    private void configurePivotMotor() {
        pivotMotor.getConfigurator().apply(pivotMotorConfigs);
    }
    
    private double kP = 0;
    private double kI = 0;
    private double kD = 0;

    private TrapezoidProfile.Constraints pivotConstraints =
                new TrapezoidProfile.Constraints(
                    PivotConstants.kMaxVelocity.in(DegreesPerSecond),
                    PivotConstants.kMaxAcceleration.in(DegreesPerSecondPerSecond));

    private ProfiledPIDController pivotController =
                new ProfiledPIDController(
                    kP, 
                    kI, 
                    kD, 
                    pivotConstraints);

        @Logged (name = "pivotPitch")
    public Angle getAngle() {
        Angle angle = pivotMotor.getPosition().getValue();
        return angle;
    }

    @Logged (name = "atTargetAngle")
    public boolean atTargetAngle() {
        boolean atTargetAngle = Math.abs(getAngle().in(Degrees) - PivotConstants.kTargetAngle.in(Degrees)) < HoodConstants.kAngleTolerance.in(Degrees);

        return atTargetAngle;
    }

    @Logged (name = "pivotVoltage")
    public Voltage getVoltage() {
        return this.pivotMotor.getMotorVoltage().getValue();
    }

    @Logged (name = "pivotCurrent")
    public Current getCurrent() {
        return this.pivotMotor.getStatorCurrent().getValue();
    }

    // @Override
    public void periodic() {
        SmartDashboard.putNumber("Pivot Angle", pivotMotor.getPosition().getValue().in(Degrees));
    }

    private Voltage desireVoltage = Volts.of(pivotController.calculate(getAngle().in(Degrees), PivotConstants.kTargetAngle.in(Degrees)));

    // public void goToAngle(Angle targetAngle) {
    //     Angle currentAngle = getAngle();
    //     Voltage desiredVoltage = Volts.of(pivotController.calculate(currentAngle.in(Degrees), targetAngle.in(Degrees)));
    // }

    public void goToAngle(Angle targetAngle) {
        Voltage calculatedPIDVoltage = Volts.of(pivotController.calculate(getAngle().in(Degrees), targetAngle.in(Degrees)));
        Voltage calculatedFFVoltage = Volts.of(pivotFeedforward.calculate(targetAngle.in(Degrees), 0));
        Voltage desiredVoltage = Volts.of(calculatedFFVoltage.in(Volts) + calculatedPIDVoltage.in(Volts));
        pivotMotor.setVoltage(desiredVoltage.in(Volts));
    }
    
}
