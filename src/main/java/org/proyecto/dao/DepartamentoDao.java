package org.proyecto.dao;

import org.proyecto.dto.Departamento;

import java.util.ArrayList;

public abstract class DepartamentoDao {
    public abstract int insert(Departamento obj) throws Exception;
    public abstract void update(Departamento obj) throws Exception;
    public abstract void delete(int id) throws Exception;
    public abstract Departamento get(int id) throws Exception;
    public abstract ArrayList<Departamento> getList() throws Exception;

}
