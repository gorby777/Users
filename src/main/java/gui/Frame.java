package gui;

import business.Manager;
import entity.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/*
 * Класс для создания формы при визуализации работы с БД
 */
public class Frame extends JFrame implements ActionListener {
    private static final String ADD = "ADD";
    private static final String EDIT = "EDIT";
    private static final String FIND = "FIND";
    private static final String DELETE = "DELETE";
    private static final String PREV = "PREV";
    private static final String NEXT = "NEXT";

    private final Manager manager = new Manager();
    private final JTable table = new JTable();
    //Количество строк на странице формы
    //чтобы не было необходимости скроллить
    public static final int rows = 19;

    // В конструкторе мы создаем нужные элементы
    public Frame() {
        // Выставляем у таблицы свойство, которое позволяет выделить
        // только одну строку в таблице
        table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        // Используем layout GridBagLayout
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        // Каждый элемент является последним в строке
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        // Элемент раздвигается на весь размер ячейки
        gbc.fill = GridBagConstraints.BOTH;
        // Но имеет границы - слева, сверху и справа по 5. Снизу - 0
        gbc.insets = new Insets(5, 5, 0, 5);

        // Создаем панели для кнопок: левая и нижняя
        JPanel btnPanelLeft = new JPanel();
        JPanel btnPanelBottom = new JPanel();
        // усанавливаем у него layout
        btnPanelLeft.setLayout(gridbag);
        // Создаем кнопки и указываем их заголовки и ActionCommand
        btnPanelLeft.add(createButton(gridbag, gbc, "Добавить", ADD));
        btnPanelLeft.add(createButton(gridbag, gbc, "Найти", FIND));
        btnPanelLeft.add(createButton(gridbag, gbc, "Изменить", EDIT));
        btnPanelLeft.add(createButton(gridbag, gbc, "Удалить", DELETE));
        btnPanelBottom.add(createButton(gridbag, gbc, "< Назад", PREV));
        btnPanelBottom.add(createButton(gridbag, gbc, "Вперёд >", NEXT));

        // Создаем панель для левой колонки с кнопками
        JPanel left = new JPanel();
        // Выставляем layout BorderLayout
        left.setLayout(new BorderLayout());
        // Кладем панель с кнопками в верхнюю часть
        left.add(btnPanelLeft, BorderLayout.NORTH);
        // Кладем панель для левой колонки на форму слева - WEST
        add(left, BorderLayout.WEST);
        // Создаем панель для нижней колонки с кнопками
        JPanel bottom = new JPanel();
        // Выставляем layout BorderLayout
        bottom.setLayout(new BorderLayout());
        // Кладем панель с кнопками в нижнюю часть
        bottom.add(btnPanelBottom, BorderLayout.SOUTH);
        // Кладем панель для правой колонки на форму справа - EAST
        add(bottom, BorderLayout.SOUTH);

        // Кладем панель со скролингом, внутри которой находится наша таблица
        // Теперь таблица может скроллироваться
        add(new JScrollPane(table), BorderLayout.CENTER);

        // выставляем координаты формы
        setBounds(500, 200, 900, 400);

        // При закрытии формы заканчиваем работу приложения
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        table.setAutoCreateColumnsFromModel(true);

        // Загружаем пользователей
        load(0);
    }

    // Метод создает кнопку с заданными харктеристиками - заголовок и действие
    private JButton createButton(GridBagLayout gridBag, GridBagConstraints gbc, String title, String action) {
        // Создаем кнопку с заданным загловком
        JButton button = new JButton(title);
        // Действие будет проверяться в обработчике и мы будем знать, какую
        // именно кнопку нажали
        button.setActionCommand(action);
        // Обработчиком события от кнопки являемся сама форма
        button.addActionListener(this);
        // Выставляем свойства для размещения для кнопки
        gridBag.setConstraints(button, gbc);
        return button;
    }

    @Override
    // Обработка нажатий кнопок
    public void actionPerformed(ActionEvent ae) {
        // Получаем команду - ActionCommand
        String action = ae.getActionCommand();
        // В зависимости от команды выполняем действия
        switch (action) {
            // Добавление записи
            case ADD:
                add();
                break;
            // Исправление записи
            case EDIT:
                edit();
                break;
            // Поиск пользователя
            case FIND:
                find();
                break;
            // Удаление пользователя
            case DELETE:
                delete();
                break;
            // На предыдущую страницу
            case PREV:
                load(-rows);
                // На следующую страницу
                break;
            case NEXT:
                load(rows);
                break;
        }
    }

    // Загрузить список пользователей
    private void load(int offset) {
        // Обращаемся к классу для загрузки списка пользователей
        List<Object> users = manager.getList(offset);
        // Создаем модель, которой передаем полученный список
        Model userModel = new Model(users);
        // Передаем нашу модель таблице - и она может ее отображать
        table.setModel(userModel);
    }

    // Добавление пользователя
    private void add() {
        // Создаем диалог для ввода данных
        EditDialog editDialog = new EditDialog();
        // Обрабатываем закрытие диалога
        save(editDialog);
    }

    // Редактирование записи
    private void edit() {
        // Получаем выделенную строку
        int selectedRow = table.getSelectedRow();
        // если строка выделена - можно ее редактировать
        if (selectedRow != -1) {
            // Получаем ID пользователя
            int id = Integer.parseInt(table.getModel().getValueAt(selectedRow, 0).toString());
            // получаем данные пользователя по его ID
            // Создаем диалог для ввода данных и передаем туда всего пользователя
            EditDialog editDialog = new EditDialog((User) manager.get(id));
            // Обрабатываем закрытие диалога
            save(editDialog);
        } else {
            // Если строка не выделена - сообщаем об этом
            JOptionPane.showMessageDialog(this, "Вы должны выделить строку для редактирования");
        }
    }

    // Поиск пользователя
    private void find() {
        // Создаем диалог для ввода критерия поиска
        FindDialog findDialog = new FindDialog();
        // Обращаемся к классу для загрузки найденного списка пользователей
        List<Object> objects = manager.find(findDialog.get());
        if (objects.size() > 0) {
            // Создаем модель, которой передаем полученный список
            Model userModel = new Model(objects);
            // Передаем нашу модель таблице - и она может ее отображать
            table.setModel(userModel);
        } else JOptionPane.showMessageDialog(this, "Пользователи с таким именем не найдены");
    }

    // Удаление пользователя
    private void delete() {
        // Получаем выделенную строку
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            // Получаем ID пользователя
            int id = Integer.parseInt(table.getModel().getValueAt(selectedRow, 0).toString());
            // Удаляем контакт
            manager.delete(id);
            // обновляем список пользователей
            load(0);
        } else {
            JOptionPane.showMessageDialog(this, "Вы должны выделить строку для удаления");
        }
    }

    // Общий метод для добавления и изменения данных пользователя
    private void save(EditDialog editDialog) {
        // Если мы нажали кнопку SAVE
        if (editDialog.isSave()) {
            // Получаем пользователя из диалогового окна
            User object = editDialog.get();
            if (object.getId() != 0) {
                // Если ID у пользователя есть, то мы его обновляем
                manager.update(object);
            } else {
                // Если у пользователя нет ID - значит он новый и мы его добавляем
                manager.add(object);
            }
            load(0);
        }
    }
}
