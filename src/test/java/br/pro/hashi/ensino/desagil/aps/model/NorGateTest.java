package br.pro.hashi.ensino.desagil.aps.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NorGateTest {
    @Test
    public void whenReceivingFalseAndFalseShouldReturnTrue() {
        NorGate gate = new NorGate();
        gate.connect(0, new EmitterFalse());
        gate.connect(1, new EmitterFalse());
        Assertions.assertTrue(gate.read());
    }

    @Test
    public void whenReceivingFalseAndTrueShouldReturnFalse() {
        NorGate gate = new NorGate();
        gate.connect(0, new EmitterFalse());
        gate.connect(1, new EmitterTrue());
        Assertions.assertFalse(gate.read());
    }

    @Test
    public void whenReceivingTrueAndFalseShouldReturnFalse() {
        NorGate gate = new NorGate();
        gate.connect(0, new EmitterTrue());
        gate.connect(1, new EmitterFalse());
        Assertions.assertFalse(gate.read());
    }

    @Test
    public void whenReceivingTrueAndTrueShouldReturnFalse() {
        NorGate gate = new NorGate();
        gate.connect(0, new EmitterTrue());
        gate.connect(1, new EmitterTrue());
        Assertions.assertFalse(gate.read());
    }
}
