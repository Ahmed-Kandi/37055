package org.firstinspires.ftc.teamcode.Main.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Main.subsystems.ShooterSubsystem;

/**
 * Example TeleOp showing how to wire up the dual shooter to gamepad buttons:
 *   - Right trigger: nectar shooter (variable power based on how far pressed)
 *   - Left trigger:  pollen shooter (variable power based on how far pressed)
 *   - Y button: fire both shooters at default power
 */
@TeleOp(name = "BioBuzz Shooter TeleOp")
public class ShooterTeleOp extends LinearOpMode {

    @Override
    public void runOpMode() {
        ShooterSubsystem shooter = new ShooterSubsystem(hardwareMap);

        telemetry.addLine("Ready - press START");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {

            // Variable-speed trigger control for each shooter independently
            double nectarPower = gamepad1.right_trigger;
            double pollenPower = gamepad1.left_trigger;

            if (nectarPower > 0.05) {
                shooter.setNectarPower(nectarPower);
            } else {
                shooter.stopNectar();
            }

            if (pollenPower > 0.05) {
                shooter.setPollenPower(pollenPower);
            } else {
                shooter.stopPollen();
            }

            // Fixed default-power fire-both button
            if (gamepad1.y) {
                shooter.shootBoth();
            }

            telemetry.addData("Nectar Power", shooter.getNectarPower());
            telemetry.addData("Pollen Power", shooter.getPollenPower());
            telemetry.addData("Nectar Velocity (ticks/s)", shooter.getNectarVelocity());
            telemetry.addData("Pollen Velocity (ticks/s)", shooter.getPollenVelocity());
            telemetry.update();
        }
    }
}