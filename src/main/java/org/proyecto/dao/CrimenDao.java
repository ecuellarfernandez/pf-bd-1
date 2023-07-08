package org.proyecto.dao;

import org.proyecto.dto.Crimen;

import java.util.ArrayList;

public abstract class CrimenDao {
    public abstract int insert(Crimen obj) throws Exception;
    public abstract void update(Crimen obj) throws Exception;
    public abstract void delete(int id) throws Exception;
    public abstract Crimen get(int id) throws Exception;
    public abstract ArrayList<Crimen> getList() throws Exception;
}
