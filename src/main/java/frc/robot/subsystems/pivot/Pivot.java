package frc.robot.subsystems.pivot;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;

public class Pivot {

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
    
}
