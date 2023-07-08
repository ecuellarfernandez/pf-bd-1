package org.proyecto.dao;

import org.proyecto.dto.Investigacion;

import java.util.ArrayList;

public abstract class InvestigacionDao {
    public abstract int insert(Investigacion obj) throws Exception;
    public abstract void update(Investigacion obj) throws Exception;
    public abstract void delete(int id) throws Exception;
    public abstract Investigacion get(int id) throws Exception;
    public abstract ArrayList<Investigacion> getList() throws Exception;
}
