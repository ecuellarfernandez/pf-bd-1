package org.proyecto.dao;

import org.proyecto.dto.SospechosoTieneCrimen;

import java.util.ArrayList;

public abstract class SospechosoTieneCrimenDao {
    public abstract int insert(SospechosoTieneCrimen obj) throws Exception;
    public abstract void update(SospechosoTieneCrimen obj) throws Exception;
    public abstract void delete(int id) throws Exception;
    public abstract ArrayList<SospechosoTieneCrimen> getList() throws Exception;
    public abstract SospechosoTieneCrimen get(int id) throws Exception;
}
