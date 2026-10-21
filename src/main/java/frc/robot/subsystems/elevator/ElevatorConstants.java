package frc.robot.subsystems.elevator;

import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.Volts;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Voltage;

import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

public class ElevatorConstants {

    public static final String kRobotName = "Walter";

    public static final Angle kStartingAngle = Degrees.of(0);

    public static final Voltage kMaxVoltage = Volts.of(12);
    public static final Current kMaxCurrent = Amps.of(40);
    public static final Current kSupplyCurrentLimit = Amps.of(40);
    public static final boolean kSupplyLimitEnabled = true;
    public static final boolean kStatorLimitEnabled = true;

    public static final int kLeftElevatorMotorID = 10;
    public static final int kRightElevatorMotorID = 9;

    public static final double kGearRatio = 142.9;
    public static final InvertedValue kInversionLeft = InvertedValue.CounterClockwise_Positive;
    public static final InvertedValue kInversionRight = InvertedValue.Clockwise_Positive;
    public static final NeutralModeValue kNeutralMode =NeutralModeValue.Brake;
    
}
