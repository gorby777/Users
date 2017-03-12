package gui;

import entity.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 * Класс для создания формы диалога при поиске записи по имени
 */
public class FindDialog extends JDialog implements ActionListener{
    public FindDialog() {
        this(null);
    }

    // Заголовки кнопок
    private static final String FIND = "Найти";
    private static final String CANCEL = "Отмена";

    // Размер отступа
    private static final int PAD = 10;
    // Ширина метки
    private static final int W_L = 100;
    //Ширина поля для ввода
    private static final int W_T = 200;
    // Ширина кнопки
    private static final int W_B = 120;
    // высота элемента - общая для всех
    private static final int H_B = 25;

    // Поле для ввода Имени
    private final JTextPane txtName = new JTextPane();

    public FindDialog(User user) {
        // Убираем layout - будем использовать абсолютные координаты
        setLayout(null);

        // Выстраиваем метки и поля для ввода
        buildField();
        // выстраиваем кнопки
        buildButtons();
        // Диалог в модальном режиме - только он активен
        setModal(true);
        // Запрещаем изменение размеров
        setResizable(false);
        // Выставляем размеры формы
        setBounds(700, 300, 300, 150);
        // Делаем форму видимой
        setVisible(true);
    }

    // Размещаем метки и поля ввода на форме
    private void buildField() {
        // Набор метки и поля для Имени
        JLabel lblName = new JLabel("Имя:");
        // Выравнивание текста с правой стороны
        lblName.setHorizontalAlignment(SwingConstants.RIGHT);
        // Выставляем координаты метки
        lblName.setBounds(new Rectangle(PAD-60, 0 * H_B + PAD, W_L, H_B));
        // Кладем метку на форму
        add(lblName);
        // Выставляем координаты поля
        txtName.setBounds(new Rectangle(W_L + 2 * PAD-60, 0 * H_B + PAD, W_T, H_B));
        // Делаем "бордюр" для поля
        txtName.setBorder(BorderFactory.createEtchedBorder());
        // Кладем поле на форму
        add(txtName);

    }

    // Размещаем кнопки на форме
    private void buildButtons() {
        JButton btnFind = new JButton("Найти");
        btnFind.setActionCommand(FIND);
        btnFind.addActionListener(this);
        btnFind.setBounds(new Rectangle(PAD+15, 5 * H_B + PAD-50, W_B, H_B));
        add(btnFind);

        JButton btnCancel = new JButton("Отмена");
        btnCancel.setActionCommand(CANCEL);
        btnCancel.addActionListener(this);
        btnCancel.setBounds(new Rectangle(W_B + 2 * PAD+15, 5 * H_B + PAD-50, W_B, H_B));
        add(btnCancel);
    }

    @Override
    // Обработка нажатий кнопок
    public void actionPerformed(ActionEvent ae) {
        String action = ae.getActionCommand();
        // Если нажали кнопку FIND (найти) - запоминаем этой
        boolean find = FIND.equals(action);
        // Закрываем форму
        setVisible(false);
    }

    // Получаем значение поля name для поиска
    public String get() {
        return txtName.getText();
    }
}
