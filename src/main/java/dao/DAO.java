package dao;

import java.util.List;

/*
 * Интерфейс для определения функций хранилища данных о пользователях
 */
public interface DAO
{
    // Добавление контакта - возвращает ID добавленного пользователя
    public void add(Object object);
    // Редактирование данных пользователя
    public void update(Object object);
    // Удаление пользователя по его ID
    public void delete(int id);
    // Получение юзера по id
    public Object get(int id);
    // Получение списка пользователей для одной страницы
    public List<Object> getList(int rows);
    // Получение списка пользователей с фильтром по имени
    public List<Object> find(String name);
}
