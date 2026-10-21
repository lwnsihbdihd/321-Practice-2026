package frc.robot.subsystems.elevator;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;

public class Elevator {
    
    public Elevator() {
        configureElevatorMotors();
    }

    TalonFX leftElevatorMotor = new TalonFX(ElevatorConstants.kLeftElevatorMotorID);
    TalonFX rightElevatorMotor = new TalonFX(ElevatorConstants.kRightElevatorMotorID);

    final CurrentLimitsConfigs elevatorCurrentLimitsConfigs = 
            new CurrentLimitsConfigs()
                .withSupplyCurrentLimit(ElevatorConstants.kSupplyCurrentLimit)
                .withSupplyCurrentLimitEnable(ElevatorConstants.kSupplyLimitEnabled)
                .withStatorCurrentLimit(ElevatorConstants.kSupplyCurrentLimit)
                .withStatorCurrentLimitEnable(ElevatorConstants.kStatorLimitEnabled);

    final FeedbackConfigs elevatorFeedbackConfigs = 
            new FeedbackConfigs()
                .withSensorToMechanismRatio(ElevatorConstants.kGearRatio);

    final MotorOutputConfigs leftElevatorMotorOutputConfigs = 
            new MotorOutputConfigs()
                .withInverted(ElevatorConstants.kInversionLeft)
                .withNeutralMode(ElevatorConstants.kNeutralMode);

    final MotorOutputConfigs rightElevatorMotorOutputConfigs = 
            new MotorOutputConfigs()
                .withInverted(ElevatorConstants.kInversionRight)
                .withNeutralMode(ElevatorConstants.kNeutralMode);

    final TalonFXConfiguration leftElevatorMotorConfigs = 
            new TalonFXConfiguration()
                .withCurrentLimits(elevatorCurrentLimitsConfigs)
                .withMotorOutput(leftElevatorMotorOutputConfigs)
                .withFeedback(elevatorFeedbackConfigs);

    final TalonFXConfiguration rightElevatorMotorConfigs = 
            new TalonFXConfiguration()
                .withCurrentLimits(elevatorCurrentLimitsConfigs)
                .withMotorOutput(rightElevatorMotorOutputConfigs)
                .withFeedback(elevatorFeedbackConfigs);

    private void configureElevatorMotors() {
        leftElevatorMotor.getConfigurator().apply(leftElevatorMotorConfigs);
        rightElevatorMotor.getConfigurator().apply(rightElevatorMotorConfigs);
    }

}
