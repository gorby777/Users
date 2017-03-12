package gui;

import entity.User;

import javax.swing.table.AbstractTableModel;
import java.util.List;
/*
 * Класс для табличного представления записей в БД
 */

public class Model extends AbstractTableModel
{
    // Список заголовков для колонок в таблице
    private static final String[] headers = {"id", "Имя", "Возраст", "Админ", "Дата"};

    // Здесь мы храним список объектов, которые будем отображать в таблице
    private final List<Object> objects;

    Model(List<Object> objects) {
        this.objects = objects;
    }

    @Override
    // Получить количество строк в таблице - у нас это размер коллекции
    public int getRowCount() {
        return objects.size();
    }

    @Override
    // Получить количество столбцов - их у нас столько же, сколько полей
    // у класса User - всего пять
    public int getColumnCount() {
        return headers.length;
    }

    @Override
    // Вернуть заголовок колонки - мы его берем из массива headers
    public String getColumnName(int columnName) {
        return headers[columnName];
    }

    //@Override
    // Вернуть класс объекта в каждом столбце
    public Class getColumnClass(int c) {
        return getValueAt(0,c).getClass();
    }

    @Override
    // Получить объект для отображения в конкретной ячейке таблицы
    // В данном случае мы отдаем строковое представление поля
    public Object getValueAt(int row, int col) {
        User object = (User) objects.get(row);
        // В зависимости от номера колонки возвращаем то или иное поле контакта
        switch (col) {
            case 0:
                return object.getId();
            case 1:
                return object.getName();
            case 2:
                return object.getAge();
            case 3:
                return object.getAdmin();
            default:
                return object.getDate();
        }
    }
}
