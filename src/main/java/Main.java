/**
 * Created by Евгений on 17.02.2017.
 */

import dao.DBCreation;
import gui.Frame;

public class Main
{
    public static void main(String[] args) {
        DBCreation creator = new DBCreation();
        creator.createDB();

        Frame cf = new Frame();
        cf.setVisible(true);
    }
}