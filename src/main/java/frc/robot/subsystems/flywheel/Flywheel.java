package frc.robot.subsystems.flywheel;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;

import frc.robot.subsystems.hood.HoodConstants;

public class Flywheel {
    
    TalonFX flywheelMotor = new TalonFX(HoodConstants.kHoodMotorID);

    final CurrentLimitsConfigs flywheelCurrentLimitsConfigs = 
            new CurrentLimitsConfigs()
                .withSupplyCurrentLimit(FlywheelConstants.kSupplyCurrentLimit)
                .withSupplyCurrentLimitEnable(FlywheelConstants.kSupplyLimitEnabled)
                .withStatorCurrentLimit(FlywheelConstants.kSupplyCurrentLimit)
                .withStatorCurrentLimitEnable(FlywheelConstants.kSupplyLimitEnabled);

    final MotorOutputConfigs flywheelMotorOutputConfigs = 
            new MotorOutputConfigs()
                .withInverted(FlywheelConstants.kInversion)
                .withNeutralMode(HoodConstants.kNeutralMode);
              
    final FeedbackConfigs flywheelFeedbackConfigs = 
            new FeedbackConfigs()
                .withSensorToMechanismRatio(FlywheelConstants.kGearRatio);

    final TalonFXConfiguration flywheelMotorConfigs = 
            new TalonFXConfiguration()
            .withCurrentLimits(flywheelCurrentLimitsConfigs)
            .withMotorOutput(flywheelMotorOutputConfigs)
            .withFeedback(flywheelFeedbackConfigs);  

    private void configureFlywheelMotor() {
        flywheelMotor.getConfigurator().apply(flywheelMotorConfigs);
    }

}
