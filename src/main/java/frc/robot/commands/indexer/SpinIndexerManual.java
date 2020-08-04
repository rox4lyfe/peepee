package frc.robot.commands.indexer;

import frc.robot.subsystems.Indexer;
import harkerrobolib.commands.IndefiniteCommand;

public class SpinIndexerManual extends IndefiniteCommand {
    private double percentOutput;
    private boolean reverse;

    public SpinIndexerManual(double percentOutput, boolean reverse) {
        addRequirements(Indexer.getInstance());
        this.percentOutput = percentOutput;
        this.reverse = reverse;
    }

    public void execute() {
        if(!reverse)
            Indexer.getInstance().spinSpine(percentOutput);
        else
            Indexer.getInstance().spinSpine(-percentOutput);
    }

    @Override
    public void end(boolean interrupted) {
        Indexer.getInstance().spinSpine(0);
        Indexer.getInstance().spinAgitator(0);
    }
}