package org.firstinspires.ftc.teamcode.Main.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * ShooterSubsystem
 *
 * Controls the two independent shooter stages on the BioBuzz robot:
 *   - Nectar shooter
 *   - Pollen shooter
 *
 * Both are driven by goBILDA Yellow Jacket motors through DcMotorEx so you
 * get access to encoder velocity (ticks/sec) if you want closed-loop speed
 * control later, but by default this just runs them open-loop with power.
 *
 * Config names below must match whatever you named the motors in the
 * Driver Station robot configuration.
 */
public class ShooterSubsystem {

    // ----- Config names (must match your Driver Station robot config) -----
    public static final String NECTAR_MOTOR_NAME = "nectarMotor";
    public static final String POLLEN_MOTOR_NAME = "pollenMotor";

    // ----- Default shoot powers (0.0 - 1.0), tune to your flywheel/gearing -----
    public static final double NECTAR_SHOOT_POWER = 0.8;
    public static final double POLLEN_SHOOT_POWER = 0.7;

    private final DcMotorEx nectarMotor;
    private final DcMotorEx pollenMotor;

    private double nectarPower = 0.0;
    private double pollenPower = 0.0;

    public ShooterSubsystem(HardwareMap hardwareMap) {
        nectarMotor = hardwareMap.get(DcMotorEx.class, NECTAR_MOTOR_NAME);
        pollenMotor = hardwareMap.get(DcMotorEx.class, POLLEN_MOTOR_NAME);

        // Flip these if a shooter spins backwards on your build
        nectarMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        pollenMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        // Coast when power is zero so flywheels can spin down naturally
        // (use BRAKE instead if you want them to stop fast)
        nectarMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        pollenMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        nectarMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        pollenMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    // ---------------- Nectar shooter controls ----------------

    /** Run the nectar shooter at its default shoot power. */
    public void shootNectar() {
        setNectarPower(NECTAR_SHOOT_POWER);
    }

    /** Run the nectar shooter at a custom power (-1.0 to 1.0). */
    public void setNectarPower(double power) {
        nectarPower = power;
        nectarMotor.setPower(power);
    }

    public void stopNectar() {
        setNectarPower(0.0);
    }

    // ---------------- Pollen shooter controls ----------------

    /** Run the pollen shooter at its default shoot power. */
    public void shootPollen() {
        setPollenPower(POLLEN_SHOOT_POWER);
    }

    /** Run the pollen shooter at a custom power (-1.0 to 1.0). */
    public void setPollenPower(double power) {
        pollenPower = power;
        pollenMotor.setPower(power);
    }

    public void stopPollen() {
        setPollenPower(0.0);
    }

    // ---------------- Combined controls ----------------

    /** Fire both shooters at their default powers. */
    public void shootBoth() {
        shootNectar();
        shootPollen();
    }

    /** Stop both shooters. */
    public void stopAll() {
        stopNectar();
        stopPollen();
    }

    // ---------------- Getters (telemetry / debugging) ----------------

    public double getNectarPower() {
        return nectarPower;
    }

    public double getPollenPower() {
        return pollenPower;
    }

    /** Current nectar motor velocity in encoder ticks/sec (goBILDA encoder). */
    public double getNectarVelocity() {
        return nectarMotor.getVelocity();
    }

    /** Current pollen motor velocity in encoder ticks/sec (goBILDA encoder). */
    public double getPollenVelocity() {
        return pollenMotor.getVelocity();
    }
}