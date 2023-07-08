package org.proyecto.dao;

import org.proyecto.dto.CategoriaCrimen;

import java.util.ArrayList;

public abstract class CategoriaCrimenDao {
    public abstract int insert(CategoriaCrimen obj) throws Exception;
    public abstract void update(CategoriaCrimen obj) throws Exception;
    public abstract void delete(int id) throws Exception;
    public abstract CategoriaCrimen get(int id) throws Exception;
    public abstract ArrayList<CategoriaCrimen> getList() throws Exception;
}
