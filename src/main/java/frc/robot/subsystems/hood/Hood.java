package frc.robot.subsystems.hood;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;

public class Hood {

    public Hood() {
        configureMotors();
    }

    TalonFX hoodMotor = new TalonFX(HoodConstants.kHoodMotorID);

    final CurrentLimitsConfigs currentLimitsConfigs = 
            new CurrentLimitsConfigs()
                .withSupplyCurrentLimit(HoodConstants.kSupplyCurrentLimit)
                .withSupplyCurrentLimitEnable(HoodConstants.kSupplyLimitEnabled)
                .withStatorCurrentLimit(HoodConstants.kSupplyCurrentLimit)
                .withStatorCurrentLimitEnable(HoodConstants.kSupplyLimitEnabled);

    final MotorOutputConfigs motorOutputConfigs = 
            new MotorOutputConfigs()
                .withInverted(HoodConstants.kInversion)
                .withNeutralMode(HoodConstants.kNeutralMode);
              
    final FeedbackConfigs feedbackConfigs = 
            new FeedbackConfigs()
                .withSensorToMechanismRatio(HoodConstants.kGearRatio);

    final TalonFXConfiguration motorConfigs = 
            new TalonFXConfiguration()
            .withCurrentLimits(currentLimitsConfigs)
            .withMotorOutput(motorOutputConfigs)
            .withFeedback(feedbackConfigs);  

    private void configureMotors() {
        hoodMotor.getConfigurator().apply(motorConfigs);
    }
}
