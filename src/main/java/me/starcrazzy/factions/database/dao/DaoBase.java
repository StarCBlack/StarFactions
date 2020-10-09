package me.starcrazzy.factions.database.dao;

import lombok.Getter;
import lombok.Setter;
import me.starcrazzy.factions.database.MySQL;
import me.starcrazzy.factions.database.manager.MySQLManager;
import me.starcrazzy.factions.database.table.Table;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
abstract public class DaoBase<E> {

    protected MySQL mySQL;
    protected Table table;

    public DaoBase(String tableName){
        table = new Table((tableName));
        mySQL = MySQLManager.getMySQL("general");
        Table.setDefaultConnection(mySQL.getConnection());

    }

     public List<E> selectAll(){
    ArrayList<E> listaDeDados = new ArrayList<>();
         try {
             PreparedStatement state =mySQL.prepareStatement("SELECT * FROM "
                     + table.getName());
             ResultSet query =state.executeQuery();
             while (query.next()){
                 E dadoNovo = newInstance(query);
                 if (dadoNovo != null) {
                     updateCache(dadoNovo, query);
                     listaDeDados.add(dadoNovo);
                 }

             }
         } catch (SQLException ex) {
             ex.printStackTrace();
         }

         return listaDeDados;



     }


    abstract public void insert( E e);

    abstract public void createTable();
    abstract public void update( E e);


    abstract public E  newInstance(ResultSet query);
    abstract public void  updateCache( E e,ResultSet query);

    public E find(int id){

        try {
            PreparedStatement state =mySQL.prepareStatement("SELECT * FROM "
                    + table.getName() +
                    " where ID = ?");
            state.setInt(1, id);
             ResultSet query =state.executeQuery();
            if (query.next()){
                E dadoNovo = newInstance(query);
              return dadoNovo;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;

    }

    public void delete(int id){

        try {
            PreparedStatement state =mySQL.prepareStatement("DELETE FROM " + table.getName() +
                    " where ID = ?");
            state.setInt(1, id);
            state.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }



}
