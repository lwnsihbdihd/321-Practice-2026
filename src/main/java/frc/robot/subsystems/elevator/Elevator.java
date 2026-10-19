package frc.robot.subsystems.elevator;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;

public class Elevator {
    
    public Elevator() {
        configureElevatorMotor();
    }

    TalonFX elevatorMotor = new TalonFX(ElevatorConstants.kElevatorMotorID);

    final CurrentLimitsConfigs elevatorCurrentLimitsConfigs = 
            new CurrentLimitsConfigs()
                .withSupplyCurrentLimit(ElevatorConstants.kSupplyCurrentLimit)
                .withSupplyCurrentLimitEnable(ElevatorConstants.kSupplyLimitEnabled)
                .withStatorCurrentLimit(ElevatorConstants.kSupplyCurrentLimit)
                .withStatorCurrentLimitEnable(ElevatorConstants.kSupplyLimitEnabled);

    final MotorOutputConfigs elevatorMotorOutputConfigs = 
            new MotorOutputConfigs()
                .withInverted(ElevatorConstants.kInversion)
                .withNeutralMode(ElevatorConstants.kNeutralMode);

    final FeedbackConfigs elevatorFeedbackConfigs = 
            new FeedbackConfigs()
                .withSensorToMechanismRatio(ElevatorConstants.kGearRatio);

    final TalonFXConfiguration elevatorMotorConfigs = 
            new TalonFXConfiguration()
                .withCurrentLimits(elevatorCurrentLimitsConfigs)
                .withMotorOutput(elevatorMotorOutputConfigs)
                .withFeedback(elevatorFeedbackConfigs);

    private void configureElevatorMotor() {
        elevatorMotor.getConfigurator().apply(elevatorMotorConfigs);
    }

}
