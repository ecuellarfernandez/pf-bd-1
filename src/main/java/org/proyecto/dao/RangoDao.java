package org.proyecto.dao;

import org.proyecto.dto.Rango;

import java.util.ArrayList;

public abstract class RangoDao {

        public abstract int insert(Rango obj) throws Exception;

        public abstract void update(Rango obj) throws Exception;

        public abstract void delete(int id) throws Exception;

        public abstract ArrayList<Rango> getList() throws Exception;

        public abstract Rango get(int id) throws Exception;
}
