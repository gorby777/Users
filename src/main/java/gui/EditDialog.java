package gui;

import entity.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

/*
 * Класс для создания формы диалога при редактировании записи
 */

public class EditDialog<Bean extends User> extends JDialog implements ActionListener {
    // Заголовки кнопок
    private static final String SAVE = "Сохранить";
    private static final String CANCEL = "Отменить";

    // Размер отступа
    private static final int PAD = 10;
    // Ширина метки
    private static final int W_L = 100;
    //Ширина поля для ввода
    private static final int W_T = 300;
    // Ширина кнопки
    private static final int W_B = 120;
    // высота элемента - общая для всех
    private static final int H_B = 25;

    // Поле для ввода Имени
    private final JTextPane txtName = new JTextPane();
    // Поле для ввода возраста
    private final JTextPane txtAge = new JTextPane();
    // Поле для ввода админа
    private final JCheckBox isAdmin = new JCheckBox();
    // Поле для ввода даты
    private final JTextPane txtDate = new JTextPane();
    // Поле для хранения ID пользователя, если мы собираемся редактировать
    // Если это новый контакт - id = null
    private int id = 0;
    // Надо ли записывать изменения после закрытия диалога
    private boolean save = false;
    //Формат даты для отображения в форме
    private SimpleDateFormat sdfJava = new SimpleDateFormat("dd.MM.yyyy");

    EditDialog() {        this(null);    }

    EditDialog(Bean object) {
        // Убираем layout - будем использовать абсолютные координаты
        setLayout(null);
        // Выстраиваем метки и поля для ввода
        buildFields();
        // Если нам передали контакт - заполняем поля формы
        initFields(object);
        // выстраиваем кнопки
        buildButtons();
        // Диалог в модальном режиме - только он активен
        setModal(true);
        // Запрещаем изменение размеров
        setResizable(false);
        // Выставляем размеры формы
        setBounds(700, 300, 450, 200);
        // Делаем форму видимой
        setVisible(true);
    }

    // Размещаем метки и поля ввода на форме
    private void buildFields() {
        // Набор метки и поля для Имени
        JLabel lblName = new JLabel("Имя:");
        // Выравнивание текста с правой стороны
        lblName.setHorizontalAlignment(SwingConstants.RIGHT);
        // Выставляем координаты метки
        lblName.setBounds(new Rectangle(PAD, 0 * H_B + PAD, W_L, H_B));
        // Кладем метку на форму
        add(lblName);
        // Выставляем координаты поля
        txtName.setBounds(new Rectangle(W_L + 2 * PAD, 0 * H_B + PAD, W_T, H_B));
        // Делаем "бордюр" для поля
        txtName.setBorder(BorderFactory.createEtchedBorder());
        // Кладем поле на форму
        add(txtName);

        // Набор метки и поля для возраста
        JLabel lblAge = new JLabel("Возраст:");
        lblAge.setHorizontalAlignment(SwingConstants.RIGHT);
        lblAge.setBounds(new Rectangle(PAD, 1 * H_B + PAD, W_L, H_B));
        add(lblAge);
        txtAge.setBounds(new Rectangle(W_L + 2 * PAD, 1 * H_B + PAD, W_T, H_B));
        txtAge.setBorder(BorderFactory.createEtchedBorder());
        add(txtAge);

        // Набор метки и поля для Админа
        JLabel lblAdmin = new JLabel("Админ:");
        lblAdmin.setHorizontalAlignment(SwingConstants.RIGHT);
        lblAdmin.setBounds(new Rectangle(PAD, 2 * H_B + PAD, W_L, H_B));
        add(lblAdmin);
        isAdmin.setBounds(new Rectangle(W_L + 2 * PAD, 2 * H_B + PAD, W_T, H_B));
        isAdmin.setBorder(BorderFactory.createEtchedBorder());
        add(isAdmin);

        // Набор метки и поля для Даты
        JLabel lblDate = new JLabel("Дата:");
        lblDate.setHorizontalAlignment(SwingConstants.RIGHT);
        lblDate.setBounds(new Rectangle(PAD, 3 * H_B + PAD, W_L, H_B));
        add(lblDate);
        txtDate.setBounds(new Rectangle(W_L + 2 * PAD, 3 * H_B + PAD, W_T, H_B));
        txtDate.setBorder(BorderFactory.createEtchedBorder());
        add(txtDate);
    }

    // Размещаем кнопки на форме
    private void buildButtons() {
        JButton btnSave = new JButton("Сохранить");
        btnSave.setActionCommand(SAVE);
        btnSave.addActionListener(this);
        btnSave.setBounds(new Rectangle(PAD+90, 5 * H_B + PAD, W_B, H_B));
        add(btnSave);

        JButton btnCancel = new JButton("Отменить");
        btnCancel.setActionCommand(CANCEL);
        btnCancel.addActionListener(this);
        btnCancel.setBounds(new Rectangle(W_B + 2 * PAD+90, 5 * H_B + PAD, W_B, H_B));
        add(btnCancel);
    }

    @Override
    // Обработка нажатий кнопок
    public void actionPerformed(ActionEvent ae) {
        String action = ae.getActionCommand();
        // Если нажали кнопку SAVE (сохранить изменения) - запоминаем этой
        save = SAVE.equals(action);
        // Закрываем форму
        setVisible(false);
    }

    // Надо ли сохранять изменения
    boolean isSave() {
        return save;
    }

    // Если нам передали пользователя - заполняем поля формы из БД
    private void initFields(Bean object) {
        if (object != null) {
            id = object.getId();
            txtName.setText(object.getName());
            txtAge.setText(String.valueOf(object.getAge()));
            isAdmin.setSelected(object.getAdmin());
            txtDate.setText(sdfJava.format(object.getDate()));
        }
    }

    // Создаем пользователя из заполненных полей, который можно будет записать
    User get() {
        User object = null;
        try {
            if(!txtDate.getText().matches("\\d{2}\\.\\d{2}\\.\\d{4}")){
                throw new NullPointerException();}
            object =  new User(id, txtName.getText(),
                    Integer.parseInt(txtAge.getText()),
                    isAdmin.isSelected(),
                    sdfJava.parse(txtDate.getText()));
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(this, "Введите дату в формате ДД.ММ.ГГГГ");
        }catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Допущена ошибка при заполнении формы");
        }
        return object;
    }
}
