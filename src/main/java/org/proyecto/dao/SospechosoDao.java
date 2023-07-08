package org.proyecto.dao;

import org.proyecto.dto.Sospechoso;

import java.util.ArrayList;

public abstract class SospechosoDao {
    public abstract int insert(Sospechoso obj) throws Exception;
    public abstract void update(Sospechoso obj) throws Exception;
    public abstract void delete(int id) throws Exception;
    public abstract Sospechoso get(int id) throws Exception;
    public abstract ArrayList<Sospechoso> getList() throws Exception;
}
