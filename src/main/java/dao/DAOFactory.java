package dao;

import entity.User;

/*
 * Фабрика для создания экземпляра класса DAO
 */

public class DAOFactory
{
    public static DAO getDAO() {
        return new DAOEntity<>(User.class);
    }
}
