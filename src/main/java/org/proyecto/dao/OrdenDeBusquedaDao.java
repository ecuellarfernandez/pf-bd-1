package org.proyecto.dao;

import org.proyecto.dto.OrdenDeBusqueda;

import java.util.ArrayList;

public abstract class OrdenDeBusquedaDao {
    public abstract int insert(OrdenDeBusqueda obj) throws Exception;
    public abstract void update(OrdenDeBusqueda obj) throws Exception;
    public abstract void delete(int id) throws Exception;
    public abstract ArrayList<OrdenDeBusqueda> getList() throws Exception;
    public abstract OrdenDeBusqueda get(int id) throws Exception;
}
