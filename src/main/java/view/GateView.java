package view;

import br.pro.hashi.ensino.desagil.aps.model.Gate;
import br.pro.hashi.ensino.desagil.aps.model.Light;
import br.pro.hashi.ensino.desagil.aps.model.Switch;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

public class GateView extends FixedPanel implements ActionListener, MouseListener {
    private final Gate gate;

    private final JCheckBox inputA;
    private final JCheckBox inputB;
    private final Switch sinalA;
    private final Switch sinalB;
    private final Light light;
    private final Image image;
    private boolean ligado1;
    private boolean ligado2;

    public GateView(Gate gate) {

        super(400, 220);

        this.gate = gate;

        inputA = new JCheckBox("A");
        inputB = new JCheckBox("B");
        sinalA = new Switch();
        sinalB = new Switch();
        ligado1 = false;
        ligado2 = false;

        light = new Light(255, 0, 0);

        String name = gate.toString() + ".png";
        URL url = getClass().getClassLoader().getResource(name);
        image = getToolkit().getImage(url);

        addMouseListener(this);

        update();
    }

    private void update() {

        if (inputA.isSelected()) {
            sinalA.turnOn();
        } else {
            sinalA.turnOff();
        }

        if (inputB.isSelected()) {
            sinalB.turnOn();
        } else {
            sinalB.turnOff();
        }

        if (gate.getInputSize() == 1) {
            gate.connect(0, sinalA);
        } else {
            gate.connect(0, sinalA);
            gate.connect(1, sinalB);
        }

        light.connect(0, gate);
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        update();
    }

    @Override
    public void mouseClicked(MouseEvent event) {

        // Descobre em qual posição o clique ocorreu.
        int x = event.getX();
        int y = event.getY();

        // Se o clique foi dentro do quadrado colorido do output
        if (Math.pow(x - 255, 2) + Math.pow(y - 114, 2) <= 625) {

            // ...então abrimos a janela seletora de cor...
            Color color = JColorChooser.showDialog(this, null, light.getColor());
            light.setColor(color);
            // ...e chamamos repaint para atualizar a tela.
            repaint();
        }

        // clicar input 1
        if (gate.getInputSize() == 1) {
            if (Math.pow(x - 85, 2) + Math.pow(y - 115, 2) <= 625) {
                ligado1 = sinalA.read();
            }
        }

        else {
            if (Math.pow(x - 85, 2) + Math.pow(y - 92, 2) <= 625) {
                ligado1 = sinalA.read();
            }
            if (Math.pow(x - 85, 2) + Math.pow(y - 138, 2) <= 625) {
                ligado2 = sinalA.read();
            }
        }

    }

    @Override
    public void mousePressed(MouseEvent event) {
        // Não precisamos de uma reação específica à ação de pressionar
        // um botão do mouse, mas o contrato com MouseListener obriga
        // esse método a existir, então simplesmente deixamos vazio.
    }

    @Override
    public void mouseReleased(MouseEvent event) {
        // Não precisamos de uma reação específica à ação de soltar
        // um botão do mouse, mas o contrato com MouseListener obriga
        // esse método a existir, então simplesmente deixamos vazio.
    }

    @Override
    public void mouseEntered(MouseEvent event) {
        // Não precisamos de uma reação específica à ação do mouse
        // entrar no painel, mas o contrato com MouseListener obriga
        // esse método a existir, então simplesmente deixamos vazio.
    }

    @Override
    public void mouseExited(MouseEvent event) {
        // Não precisamos de uma reação específica à ação do mouse
        // sair do painel, mas o contrato com MouseListener obriga
        // esse método a existir, então simplesmente deixamos vazio.
    }

    @Override
    public void paintComponent(Graphics g) {

        // Não podemos esquecer desta linha, pois não somos os
        // únicos responsáveis por desenhar o painel, como era
        // o caso nos Desafios. Agora é preciso desenhar também
        // componentes internas, e isso é feito pela superclasse.
        super.paintComponent(g);

        // Desenha a imagem do gate, passando sua posição e seu tamanho.
        g.drawImage(image, 91, 70, 165, 110, this);
//        g.drawOval(253, 118, 17, 17);
        // Desenha um quadrado cheio.
        g.setColor(light.getColor());
        g.fillOval(255, 114, 25, 25);

        g.setColor(Color.BLACK);
        if (gate.getInputSize() == 1) {
            g.drawRect(85, 115, 20, 20);
            if (ligado1 == true){
                g.fillRect(85, 115, 20, 20);
            }
        } else {
            g.drawRect(85, 92, 20, 20);
            g.drawRect(85, 138, 20, 20);
            if (ligado1 == true){
                g.fillRect(85, 92, 20, 20);
            }
            if (ligado2 == true){
                g.fillRect(85, 138, 20, 20);
            }

        }





        // Linha necessária para evitar atrasos
        // de renderização em sistemas Linux.
        getToolkit().sync();
    }
}
