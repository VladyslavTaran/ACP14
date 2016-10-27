package spring;

import org.springframework.stereotype.Component;

/**
 * Created by Vlad on 28.10.2016.
 */

@Component
public class SecondDao {

    private String daoInfo;

    public SecondDao() {
    }

    public String getDaoInfo() {
        return daoInfo;
    }

    public void setDaoInfo(String daoInfo) {
        this.daoInfo = daoInfo;
    }
}
