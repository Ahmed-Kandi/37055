package org.firstinspires.ftc.teamcode.Main.opmodes;

import com.seattlesolvers.solverslib.command.CommandOpMode;

import org.firstinspires.ftc.teamcode.Main.subsystems.Drivetrain;

//@TeleOp(name = "Basic TeleOp")
public class TeleOp extends CommandOpMode {
    private Drivetrain drivetrain;

    @Override
    public void initialize() {
        drivetrain = new Drivetrain(hardwareMap);
    }

    @Override
    public void run() {
        drivetrain.drive(
                -gamepad1.left_stick_y,
                gamepad1.left_stick_x,
                gamepad1.right_stick_x
        );
    }
}
