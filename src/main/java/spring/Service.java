package spring;

import com.sun.xml.internal.bind.v2.model.core.ID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Created by Vlad on 27.10.2016.
 */

@Component
public class Service implements IService {


    @Autowired
    @Qualifier("dao")
    private IDao dao;

    public IDao getDao() {
        return dao;
    }

    public void setDao(IDao dao) {
        this.dao = dao;
    }

    public Service(IDao dao) {
        this.dao = dao;
    }

    public Service() {
    }

    @Override
    public String doService(){
        return dao.method();
    }

}
