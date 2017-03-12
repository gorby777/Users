package dao;

import gui.Frame;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

/*
 * Класс для выполнения операций с БД
 */
public class DAOEntity<Bean> implements DAO
{
    private final Class<Bean> typeParameterClass;   //параметры передаваемого класса
    private static int offset=0; //оффсет для пейджинга
    private static int frameRows = Frame.rows;   //Количество отображаемых записей на одной странице

    DAOEntity(Class<Bean> typeParameterClass)
    {
        this.typeParameterClass = typeParameterClass;
    }

    //Удаление выбранной записи
    @Override
    public void delete(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Object del = session.get(typeParameterClass, id);
        session.delete(del);
        session.getTransaction().commit();
        if (session.isOpen()) {
            session.close();
        }
    }

    //Поиск записей по полю name
    @Override
    public List<Object> find(String name) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        String sql = String.format("from %s where name='%s'",typeParameterClass.getCanonicalName(),name);
        Query SQLQuery = session.createQuery(sql);
        ArrayList<Object> result = (ArrayList<Object>) SQLQuery.list();
        session.getTransaction().commit();
        if (session.isOpen()) {
            session.close();
        }
        return result;
    }

    //Получение списка записей для одной страницы пейджинга
    @Override
    public ArrayList<Object> getList(int rows) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        int off = boundsCheck(session,rows);
        String hql = String.format("from %s",typeParameterClass.getCanonicalName());
        Query SQLQuery = session.createQuery(hql).setFirstResult(off).setMaxResults(frameRows);
        ArrayList<Object> result = (ArrayList<Object>) SQLQuery.list();
        session.getTransaction().commit();
        if (session.isOpen()) {
            session.close();
        }
        return result;
    }

    //Получение уникальной записи по полю id (при выборе записи в форме)
    @Override
    public Object get(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Object result = session.get(typeParameterClass, id);
        session.getTransaction().commit();
        if (session.isOpen()) {
            session.close();
        }
        return result;
    }

    //Обновление записи после редактирования
    @Override
    public void update(Object object) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(object);
        session.getTransaction().commit();
        if (session.isOpen()) {
            session.close();
        }
    }

    //Добавление записи
    @Override
    public void add(Object object) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(object);
        session.getTransaction().commit();
        if (session.isOpen()) {
            session.close();
        }
    }

    //Полная очистка таблицы с записями
    @Deprecated
    public void clear()
    {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        String hql = String.format("delete from %s",typeParameterClass.getCanonicalName());
        Query query = session.createQuery(hql);
        query.executeUpdate();
        session.getTransaction().commit();
        if (session.isOpen()) {
            session.close();
        }
    }

    //Проверка граничных условий в целях пейджинга
    //В качестве аргумента - шаг пейджинга в зависимости от операции
    private int boundsCheck(Session session,int rows){
        //Делаем запрос, получаем список записей
        String sql = String.format("select id from %s", typeParameterClass.getCanonicalName());
        Query query = session.createQuery(sql);
        int size = query.list().size();
        //Устанавливаем нужный оффсет для каждой страницы
        offset += rows;
        if(offset>=size) {offset -= rows;}
        if(offset<frameRows) {offset = 0;}
        return offset;
    }
}
