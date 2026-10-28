package frc.robot.subsystems.hood;

import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.DegreesPerSecond;
import static edu.wpi.first.units.Units.DegreesPerSecondPerSecond;
import static edu.wpi.first.units.Units.Radians;
import static edu.wpi.first.units.Units.Volts;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularAcceleration;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Voltage;

import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;


public class HoodConstants {
    
    public static final String kRobotName = "Walter"; // sets robot name

    public static final Angle kFeedAngle = Degrees.of(15); // sets feed angle
    public static final double kFeedAngleDegrees = kFeedAngle.in(Degrees); // converts degrees to double
    public static final double kFeedAngleRadians = kFeedAngle.in(Radians); // converts radians to double
    public static final Angle kStartingAngle = Degrees.of(0); // sets starting angle
    public static final Angle kAngleTolerance = Degrees.of(1.5);
    public static final Angle kTargetAngle = Degrees.of(50);

    public static final Voltage kMaxVoltage = Volts.of(12); // sets max voltage
    public static final Current kMaxCurrent = Amps.of(40); // sets max Amps
    public static final Current kSupplyCurrentLimit = Amps.of(40); // sets supply current limit
    public static final boolean kSupplyLimitEnabled = true; // sets supply limit to enabled
    public static final boolean kStatorLimitEnabled = true; // sets stator limit to enabled

    public static final int kHoodMotorID = 11; // sets motor ID

    public static final double kGearRatio = 142.9; // sets gear ratio

    public static final InvertedValue kInversion = InvertedValue.Clockwise_Positive; // sets inverted value
    
    public static final NeutralModeValue kNeutralMode = NeutralModeValue.Brake; // sets neutral mode

    public static final AngularVelocity kMaxVelocity = DegreesPerSecond.of(0);
    public static final AngularAcceleration kMaxAcceleration = DegreesPerSecondPerSecond.of(0);;
}
