package frc.robot.subsystems.hood;

import static edu.wpi.first.units.Units.Degrees;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.epilogue.Logged;
import edu.wpi.first.units.measure.Angle;

public class Hood {

    public double targetAngle = HoodConstants.kTargetAngle.in(Degrees);

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

    @Logged (name = "hoodPitch")
    public Angle getAngle() {
        Angle angle = hoodMotor.getPosition().getValue();
        return angle;
    }

    @Logged (name = "atTargetAngle")
    public boolean atTargetAngle() {
        boolean atTargetAngle = Math.abs(getAngle().in(Degrees) - targetAngle) < HoodConstants.kAngleTolerance.in(Degrees);

        return atTargetAngle;
    }
}
