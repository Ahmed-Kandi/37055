package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp(name="Shooter Test")
public class ShooterTest extends OpMode {
    DcMotorEx motor1;

    @Override
    public void init() {
        motor1 = hardwareMap.get(DcMotorEx.class, "Motor 1");
        telemetry.addData("Status: ","Initialized");
        telemetry.update();
    }

    @Override
    public void loop() {
        motor1.setPower(1.0);
        telemetry.addData("Motor power:", motor1.getPower());
        telemetry.addData("Motor Velocity:", motor1.getVelocity());
        telemetry.update();

    }
}
