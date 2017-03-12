package business;

import dao.DAO;
import dao.DAOFactory;
import java.util.List;

/*
 * Класс для реализации операций с БД
 */
public class Manager
{
    private DAO dao;
    //В конструкторе устанавливаем связь с фабрикой БД
    public Manager() {
        dao = DAOFactory.getDAO();
    }

    // Добавление пользователя - возвращает ID добавленного пользователя
    public void add(Object object) {dao.add(object);}

    // Редактирование пользователя
    public void update(Object object) {
        dao.update(object);
    }

    // Удаление пользователя по его ID
    public void delete(int id) {
        dao.delete(id);
    }

    // Получение одного пользователя
    public Object get(int id) {
        return dao.get(id);
    }

    // Получение списка пользователей по полю name
    public List<Object> find(String name) {
        return dao.find(name);
    }

    // Получение списка пользователей для одной страницы
    public List<Object> getList(int rows) {
        return dao.getList(rows);
    }
}
