package org.firstinspires.ftc.teamcode.Main.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.hardware.motors.Motor;

public class Intake extends SubsystemBase {

    private final Motor intakeMotor;
    private final Motor transferMotor;

    public Intake(HardwareMap hardwareMap) {
        intakeMotor = new Motor(hardwareMap, "intake");
        transferMotor = new Motor(hardwareMap, "transfer");
    }

    public void run(double power, String motor) {
        if (motor.equals("intake"))
            intakeMotor.set(power);
        else
            transferMotor.set(power);
    }
}