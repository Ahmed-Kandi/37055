package org.firstinspires.ftc.teamcode.Main.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.drivebase.MecanumDrive;
import com.seattlesolvers.solverslib.hardware.motors.Motor;

public class Drivetrain extends SubsystemBase {

    public final MecanumDrive mecDrive;

    public Drivetrain(HardwareMap hardwareMap) {

        Motor frontLeft = new Motor(hardwareMap, "frontLeft");
        Motor frontRight = new Motor(hardwareMap, "frontRight");
        Motor backLeft = new Motor(hardwareMap, "backLeft");
        Motor backRight = new Motor(hardwareMap, "backRight");

        mecDrive = new MecanumDrive(
                frontLeft,
                frontRight,
                backLeft,
                backRight
        );
    }

    public void drive(double forward, double strafe, double turn) {
        mecDrive.driveRobotCentric(strafe, forward, turn);
    }
}