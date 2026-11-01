package org.firstinspires.ftc.teamcode.Simple_TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.HardwareNames;


/**
 * Simple backup TeleOp. Each mechanism motor is on its own button and only runs while held.
 *
 * Controls (gamepad1):
 *   Left stick    - drive forward/back and strafe (mecanum)
 *   Right stick x - turn
 *   A - intake (also pushes nectar up to the nectar shooter)
 *   B - transfer (pushes pollen up to the pollen shooter)
 *   X - nectar shooter
 *   Y - pollen shooter
 *   D-pad down - reverse intake and transfer (unjam)
 */
@TeleOp(name = "Simple TeleOp (Backup)")
public class TeleOp_Simple_Main extends OpMode {
    // Mechanism powers (0.0 - 1.0) - tune on the robot
    static final double INTAKE_POWER = 1.0;
    static final double TRANSFER_POWER = 1.0;
    static final double NECTAR_SHOOTER_POWER = 1.0;
    static final double POLLEN_SHOOTER_POWER = 1.0;

    DcMotorEx frontLeft, frontRight, backLeft, backRight;
    DcMotorEx intake, transfer;
    DcMotorEx shooter_nectar, shooter_pollen;


    @Override
    public void init() {
        setHardwareMappings();
        setMotorsDirections();
        setMotorsBehaviors();
    }

    @Override
    public void loop() {
        robotCentricDrive();

        if (gamepad1.dpad_down) {
            intake.setPower(-INTAKE_POWER);
            transfer.setPower(-TRANSFER_POWER);
        } else {
            intake.setPower(gamepad1.a ? INTAKE_POWER : 0);
            transfer.setPower(gamepad1.b ? TRANSFER_POWER : 0);
        }
        shooter_nectar.setPower(gamepad1.x ? NECTAR_SHOOTER_POWER : 0);
        shooter_pollen.setPower(gamepad1.y ? POLLEN_SHOOTER_POWER : 0);

        telemetry.addData("Intake (A)", intake.getPower());
        telemetry.addData("Transfer (B)", transfer.getPower());
        telemetry.addData("Nectar shooter (X)", shooter_nectar.getPower());
        telemetry.addData("Pollen shooter (Y)", shooter_pollen.getPower());
        telemetry.update();
    }

    private void robotCentricDrive() {
        double y = -gamepad1.left_stick_y; // Forward (stick y is negative when pushed up)
        double x = gamepad1.left_stick_x * 1.1; // Strafe, scaled to counter imperfect strafing
        double rx = gamepad1.right_stick_x; // Rotate

        double frontLeftPower = y + x + rx;
        double backLeftPower = y - x + rx;
        double frontRightPower = y - x - rx;
        double backRightPower = y + x - rx;

        // Keep all powers in [-1, 1] while preserving their ratios
        double max =
                Math.max(1.0, Math.max(
                        Math.max(Math.abs(frontLeftPower), Math.abs(frontRightPower)),
                        Math.max(Math.abs(backLeftPower), Math.abs(backRightPower))));

        frontLeft.setPower(frontLeftPower / max);
        backLeft.setPower(backLeftPower / max);
        frontRight.setPower(frontRightPower / max);
        backRight.setPower(backRightPower / max);
    }

    private void setHardwareMappings() {
        // Control Hub
        frontLeft = hardwareMap.get(DcMotorEx.class, HardwareNames.FRONT_LEFT);
        frontRight = hardwareMap.get(DcMotorEx.class, HardwareNames.FRONT_RIGHT);
        backLeft = hardwareMap.get(DcMotorEx.class, HardwareNames.BACK_LEFT);
        backRight = hardwareMap.get(DcMotorEx.class, HardwareNames.BACK_RIGHT);

        // Expansion Hub
        intake = hardwareMap.get(DcMotorEx.class, HardwareNames.INTAKE);
        transfer = hardwareMap.get(DcMotorEx.class, HardwareNames.TRANSFER);
        shooter_nectar = hardwareMap.get(DcMotorEx.class, HardwareNames.NECTAR_SHOOTER);
        shooter_pollen = hardwareMap.get(DcMotorEx.class, HardwareNames.POLLEN_SHOOTER);
    }

    private void setMotorsBehaviors() {
        // drive motors
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        // other motors - shooters coast so flywheels spin down on their own
        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        transfer.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        shooter_nectar.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        shooter_pollen.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        // Plain power control everywhere - works even if an encoder cable is loose
        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        transfer.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        shooter_nectar.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        shooter_pollen.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    private void setMotorsDirections() {
        // Standard goBILDA mecanum setup: left side reversed.
        // If the robot drives backward when you push forward, swap all four.
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotor.Direction.FORWARD);
        backRight.setDirection(DcMotor.Direction.FORWARD);
        // Flip any of these if that mechanism spins the wrong way
        intake.setDirection(DcMotor.Direction.FORWARD);
        transfer.setDirection(DcMotor.Direction.FORWARD);
        shooter_nectar.setDirection(DcMotor.Direction.REVERSE);
        shooter_pollen.setDirection(DcMotor.Direction.FORWARD);
    }
}
