package org.proyecto.dao;

import org.proyecto.dto.Testigo;

import java.util.ArrayList;

public abstract class TestigoDao {
    public abstract int insert(Testigo obj) throws Exception;
    public abstract void update(Testigo obj) throws Exception;
    public abstract void delete(int id) throws Exception;
    public abstract Testigo get(int id) throws Exception;
    public abstract ArrayList<Testigo> getList() throws Exception;
}
