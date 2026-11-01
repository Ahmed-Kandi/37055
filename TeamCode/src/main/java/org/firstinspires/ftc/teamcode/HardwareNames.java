package org.firstinspires.ftc.teamcode;

/**
 * Robot configuration names. These must match the Driver Station robot config exactly.
 *
 * Control Hub:   0 front_left, 1 front_right, 2 back_left, 3 back_right
 * Expansion Hub: 0 intake, 1 transfer, 2 nectar, 3 pollen
 *
 * Ball paths:
 *   intake   - front intake; also drives the transfer that pushes NECTAR up to the nectar shooter
 *   transfer - pushes POLLEN up to the pollen shooter
 */
public final class HardwareNames {
    // Control Hub
    public static final String FRONT_LEFT = "front_left";
    public static final String FRONT_RIGHT = "front_right";
    public static final String BACK_LEFT = "back_left";
    public static final String BACK_RIGHT = "back_right";

    // Expansion Hub
    public static final String INTAKE = "intake";
    public static final String TRANSFER = "transfer";
    public static final String NECTAR_SHOOTER = "nectar";
    public static final String POLLEN_SHOOTER = "pollen";

    private HardwareNames() {}
}
