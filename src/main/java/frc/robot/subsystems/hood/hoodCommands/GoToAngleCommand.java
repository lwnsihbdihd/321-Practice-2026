package frc.robot.subsystems.hood.hoodCommands;

import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.Volts;

import java.util.function.Supplier;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.hood.Hood;

public class GoToAngleCommand extends Command{
    
    Hood hood;

    Supplier<Angle> targetAngle;
    
    public void GoToAngle(Hood hood, Supplier<Angle> targetAngle) {
        this.hood = hood;
        this.targetAngle = targetAngle;
        addRequirements(hood);
    }

    @Override 
    public void initialize() {
        hood.setTargetAngle(targetAngle.get());
    }

    @Override 
    public void execute() {
        hood.goToAngle(targetAngle.get());
    }

    @Override 
    public boolean isFinished() {
    return hood.getAngle().in(Degrees) == targetAngle.get().in(Degrees);

    }

    @Override 
    public void end(boolean interrupted) {
        hood.setVoltage(Volts.of(0));
        // hood.zeroEncoder();
    }
}
