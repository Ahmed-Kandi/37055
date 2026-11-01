package org.firstinspires.ftc.teamcode.Main.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.drivebase.MecanumDrive;
import com.seattlesolvers.solverslib.hardware.motors.Motor;

import org.firstinspires.ftc.teamcode.HardwareNames;

public class Drivetrain extends SubsystemBase {

    public final MecanumDrive mecDrive;

    public Drivetrain(HardwareMap hardwareMap) {

        Motor frontLeft = new Motor(hardwareMap, HardwareNames.FRONT_LEFT);
        Motor frontRight = new Motor(hardwareMap, HardwareNames.FRONT_RIGHT);
        Motor backLeft = new Motor(hardwareMap, HardwareNames.BACK_LEFT);
        Motor backRight = new Motor(hardwareMap, HardwareNames.BACK_RIGHT);

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