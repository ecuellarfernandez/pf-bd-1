package org.proyecto.dao;

import org.proyecto.dto.InvestigacionTieneTestigo;

import java.util.ArrayList;

public abstract class InvestigacionTieneTestigoDao {
    public abstract int insert(InvestigacionTieneTestigo obj) throws Exception;
    public abstract void update(InvestigacionTieneTestigo obj) throws Exception;
    public abstract void delete(int id) throws Exception;
    public abstract ArrayList<InvestigacionTieneTestigo> getList() throws Exception;
    public abstract org.proyecto.dto.InvestigacionTieneTestigo get(int id) throws Exception;
}
