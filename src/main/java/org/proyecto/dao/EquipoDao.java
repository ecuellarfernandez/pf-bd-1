package org.proyecto.dao;

import org.proyecto.dto.Equipo;

import java.util.ArrayList;

public abstract class EquipoDao {
    public abstract int insert(Equipo obj) throws Exception;
    public abstract void update(Equipo obj) throws Exception;
    public abstract void delete(int id) throws Exception;
    public abstract Equipo get(int id) throws Exception;
    public abstract ArrayList<Equipo> getList() throws Exception;
}
