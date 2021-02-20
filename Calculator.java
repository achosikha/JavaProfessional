package OOP.Calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class Calculator extends JFrame {
    private JTextField textField;
    ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
    ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("Submit");
    Object result;

    public Calculator() {
        initMandatoryComponents();

        setTitle("Calculator");
        setBounds(100, 100, 300, 500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        add(initTop(), BorderLayout.NORTH);
        add(initBottom(), BorderLayout.CENTER);

        setResizable(false);
        setVisible(true);
    }

    private void initMandatoryComponents()
    {
        textField = new JTextField();
    }

    private JPanel initTop (){
        JPanel top = new JPanel();
        top.setSize(this.getWidth(), 30);

        top.setLayout(new BorderLayout());

        textField = new JTextField();
        textField.setEditable(false);

        top.add(textField, BorderLayout.CENTER);

        return top;
    }

    private JPanel initBottom () {
        JPanel bottom = new JPanel();
        bottom.setLayout(new GridLayout(5,3));

        ActionListener operatorButtonListener = new OperatorsButtonListener(textField);

        for (int butCount = 1; butCount < 10; butCount++)
        {
            JButton buttonJ = new JButton(String.valueOf(butCount));
            buttonJ.addActionListener(operatorButtonListener);
            bottom.add((buttonJ));
        }

        JButton zero = new JButton("0");
        zero.addActionListener(operatorButtonListener);

        JButton dot = new JButton(".");
        dot.addActionListener(operatorButtonListener);

        // submit operation
        JButton submit = new JButton("=");


        /*submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                        result = scriptEngine.eval(textField.getText());
                        textField.setText(String.valueOf(result));
                } catch (Exception error)
                {
                    System.out.println ("Houston we have a problem!");
                }
            }
        }); */

        submit.addActionListener(new CalculationListener(textField));

        // clear field, can be used Lambda ->
        JButton clear = new JButton("C");
        clear.addActionListener(new ActionListener() { // анонимный класс
            @Override
            public void actionPerformed(ActionEvent e) {
                textField.setText("");
            }
        });

        JButton plus = new JButton("+");
        plus.addActionListener(operatorButtonListener);

        JButton minus = new JButton("-");
        minus.addActionListener(operatorButtonListener);

        JButton multiply = new JButton("*");
        multiply.addActionListener(operatorButtonListener);

        JButton division = new JButton("/");
        division.addActionListener(operatorButtonListener);

        JButton sqrt = new JButton("sqrt");
        sqrt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField.setText(String.valueOf(Math.sqrt(Double.parseDouble(textField.getText()))));
            }
        });

        JButton pow = new JButton("pow");
        pow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField.setText(String.valueOf(Math.pow(Double.parseDouble(textField.getText()), 2)));
            }
        });

        bottom.add(zero);
        bottom.add(dot);
        bottom.add(submit);
        bottom.add(clear);
        bottom.add(plus);
        bottom.add(minus);
        bottom.add(multiply);
        bottom.add(division);
        bottom.add(sqrt);
        bottom.add(pow);

        return bottom;
    }
}