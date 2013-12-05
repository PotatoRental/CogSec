package org.security.dao;


import org.security.model.Coglet;
import org.security.model.Cogtag;

import java.util.List;

/**
 * User: Milky
 * Date: 11/25/13
 * Time: 7:22 PM
 */
public interface CogletDao {

    public void addCoglet(Coglet coglet);

    public Coglet getCoglet(Coglet coglet);

    public List<Coglet> getAllImages();

    public List<Cogtag> getAllTags();

    public List<Coglet> getDefaultCoglets();

    public List<Coglet> getCogletsCategory(Cogtag cogtag);

    public Integer getNumCogletCategory(Cogtag cogtag);

    public Coglet getCogletWithCategoryPosition(Cogtag cogtag, Integer pos);
}
