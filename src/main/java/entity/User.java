package entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/*
 *   Класс для хранения данных пользователя
 */
@Entity //аннотация, регистрирующая класс как сущность БД
@Table(name="user") //связываем с конкретной таблицей по названию
public class User implements Serializable
{
    @Id //указывает на то, что следующее поле является ID и будет использоваться для поиска по умолчанию
    @Column(name = "id")
    private int id;

    @Column(name = "name", nullable = false, insertable = true, updatable = true, length = 25)
    private String name; //название поля класса

    @Column(name = "age", nullable = false, insertable = true, updatable = true, length = 3)
    private int age;

    @Column(name = "admin", nullable = false, insertable = true, updatable = true, length = 1)
    private boolean admin;

    @Column(name = "date", nullable = false, insertable = true, updatable = true)
    @DateTimeFormat(pattern="dd.MM.yyyy")
    @Temporal(value= TemporalType.DATE)
    private Date date;

    public User() {}

    public User(int id, String name, int age, boolean admin, Date date) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.admin = admin;
        this.date = date;
    }

    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean getAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


}