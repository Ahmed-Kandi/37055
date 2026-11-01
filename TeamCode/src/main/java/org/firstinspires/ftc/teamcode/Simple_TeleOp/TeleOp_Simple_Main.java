package org.firstinspires.ftc.teamcode.Simple_TeleOp;

import static android.os.SystemClock.sleep;

import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="Simple TeleOp", group="TeleOp - Scrimmage 1")
public class TeleOp_Simple_Main extends OpMode {
    DcMotorEx frontLeft, frontRight, backLeft, backRight;
    DcMotorEx intake, transfer;
    DcMotorEx shooter_pollen, shooter_nectar;
    Servo arm_1;
    Servo arm_2;

    double distance;
    double targetVelocity;
    boolean shoot = false;
    boolean openHold = false;


    @Override
    public void init() {
        setHardwareMappings();
        setMotorsDirections();
        setMotorsBehaviors();
        setPidfCoefficients();
        robotCentricDrive();
    }

    @Override
    public void loop() {

        if (gamepad1.a) { // Pollen Shooter
            double error = targetVelocity - shooter_pollen.getVelocity();

            shoot = Math.abs(error) < 100;
            if (shoot){
                setShooterPower(1);
            }
            else {
                setShooterPower(0.5);
            }

        } else {
            setShooterPower(0);
            shoot = false;
        }

        openHold = shoot;

        if (openHold) {
            arm_1.setPosition(0.2);
            sleep(100);
            transfer.setPower(1);
            intake.setPower(-1);

        } else {
            arm_1.setPosition(0.82);
        }

        if (gamepad1.right_trigger > 0.3) {
            intake.setPower(-1);
            transfer.setPower(1);
        } else if (gamepad1.left_trigger > 0.3) {
            intake.setPower(1);
            transfer.setPower(-1);
        } else {
            if (!openHold) {
                transfer.setPower(0);
                intake.setPower(0);
            }
        }

    }

    private void robotCentricDrive() {
        /// drive code
        double y = gamepad1.left_stick_y; // Forward
        double x = -gamepad1.left_stick_x; // Strafe
        double rx = gamepad1.right_stick_x; // Rotate

        double frontLeftPower = y + x + rx;
        double backLeftPower = y - x + rx;
        double frontRightPower = y - x - rx;
        double backRightPower = y + x - rx;

        // Optional: Normalize powers if exceeding 1.0
        double max =
                Math.max(
                        Math.max(Math.abs(frontLeftPower), Math.abs(frontRightPower)),
                        Math.max(Math.abs(backLeftPower), Math.abs(backRightPower)));
        if (max > 1.0) {
            frontLeftPower /= max;
            backLeftPower /= max;
            frontRightPower /= max;
            backRightPower /= max;
        }

        frontLeft.setPower(frontLeftPower);
        backLeft.setPower(backLeftPower);
        frontRight.setPower(frontRightPower);
        backRight.setPower(backRightPower);
    }

    private void rotate(double rx) {
        double frontLeftPower = rx;
        double backLeftPower = rx;
        double frontRightPower = 0 - rx;
        double backRightPower = 0 - rx;

        double max =
                Math.max(
                        Math.max(Math.abs(frontLeftPower), Math.abs(frontRightPower)),
                        Math.max(Math.abs(backLeftPower), Math.abs(backRightPower)));

        if (max > 1.0) {
            frontLeftPower /= max;
            backLeftPower /= max;
            frontRightPower /= max;
            backRightPower /= max;
        }

        frontLeft.setPower(frontLeftPower);
        backLeft.setPower(backLeftPower);
        frontRight.setPower(frontRightPower);
        backRight.setPower(backRightPower);
    }
    private void setShooterPower(double power) {
        shooter_pollen.setPower(power);
        shooter_nectar.setPower(power);
    }

    private void setPidfCoefficients() {
        PIDFCoefficients shooterPIDF = new PIDFCoefficients(75, 0.3, 0, 18);
        shooter_pollen.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, shooterPIDF);
        shooter_nectar.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, shooterPIDF);
    }

    private void setHardwareMappings() {
        frontLeft = hardwareMap.get(DcMotorEx.class, "FrontLeft");
        frontRight = hardwareMap.get(DcMotorEx.class, "FrontRight");
        backLeft = hardwareMap.get(DcMotorEx.class, "BackLeft");
        backRight = hardwareMap.get(DcMotorEx.class, "BackRight");

        intake = hardwareMap.get(DcMotorEx.class, "Intake");
        shooter_pollen = hardwareMap.get(DcMotorEx.class, "Shooter1");
        shooter_nectar = hardwareMap.get(DcMotorEx.class, "Shooter2");
        transfer = hardwareMap.get(DcMotorEx.class, "Transfer");

        arm_1 = hardwareMap.get(Servo.class, "hold");
        arm_2 = hardwareMap.get(Servo.class, "kicker");

    }

    private void setMotorsBehaviors() {
        // drive motors
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        // other motors
        transfer.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        // turret.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        shooter_pollen.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        shooter_pollen.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        shooter_nectar.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        shooter_nectar.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // drive motor modes
        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // other motor modes
        intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        transfer.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    private void setMotorsDirections() {
        frontLeft.setDirection(DcMotor.Direction.FORWARD);
        backLeft.setDirection(DcMotor.Direction.FORWARD);
        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.REVERSE);
        intake.setDirection(DcMotor.Direction.REVERSE);
        shooter_pollen.setDirection(DcMotor.Direction.FORWARD);
        shooter_nectar.setDirection(DcMotor.Direction.REVERSE);
    }
}

